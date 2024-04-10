package com.teaching.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teaching.common.core.PageRequest;
import com.teaching.common.core.Pagination;
import com.teaching.common.exception.ServiceException;
import com.teaching.common.helper.BeanHelper;
import com.teaching.common.helper.MathHelper;
import com.teaching.common.helper.RedisHelper;
import com.teaching.common.helper.StringHelper;
import com.teaching.platform.core.LoginUtil;
import com.teaching.platform.dao.CourseChapterDao;
import com.teaching.platform.dao.CourseChapterDetailDao;
import com.teaching.platform.dao.CourseChapterUserDao;
import com.teaching.platform.dao.CourseInfoDao;
import com.teaching.platform.dao.CourseUserDao;
import com.teaching.platform.dto.response.CoursePageResponse;
import com.teaching.platform.dto.response.CourseUserDetailDTO;
import com.teaching.platform.dto.response.CourseUserResponse;
import com.teaching.platform.entity.CourseChapter;
import com.teaching.platform.entity.CourseChapterDetail;
import com.teaching.platform.entity.CourseChapterUser;
import com.teaching.platform.entity.CourseInfo;
import com.teaching.platform.entity.CourseUser;
import com.teaching.platform.kern.CodeMSG;
import com.teaching.platform.kern.IConstant;
import com.teaching.platform.service.CourseInfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 课程信息表(CourseInfo)表服务实现类
 *
 * @author sacher
 * @since 2023-10-06 14:05:03
 */
@Service("courseInfoService")
public class CourseInfoServiceImpl extends ServiceImpl<CourseInfoDao, CourseInfo> implements CourseInfoService {
    @Resource
    private CourseChapterDao courseChapterDao;

    @Resource
    private CourseChapterDetailDao courseChapterDetailDao;

    @Resource
    private CourseChapterUserDao courseChapterUserDao;

    @Resource
    private CourseUserDao courseUserDao;

    @Resource
    private RedisHelper redisHelper;

    @Override
    public Pagination<CoursePageResponse> coursePage(PageRequest<CourseInfo> request) {
        Pagination<CoursePageResponse> pagination = new Pagination<>(request.pager,request.size);
        LambdaQueryWrapper<CourseInfo> eq = Wrappers.<CourseInfo>lambdaQuery().orderByDesc(CourseInfo::getCreatedTime);
        Page<CourseInfo> industryInfoPage = this.baseMapper.selectPage(request.toMybatisPage(), eq);
        pagination.setTotal(industryInfoPage.getTotal());
        List<CoursePageResponse> coursePageResponses = BeanHelper.castTo(industryInfoPage.getRecords(), CoursePageResponse.class);
        for (CoursePageResponse coursePageResponse : coursePageResponses) {
            if(Objects.equals(coursePageResponse.getCourseStatus(), IConstant.CourseEnum.COURSE_STATUS_OPEN.getCode()) && Objects.equals(coursePageResponse.getCourseType(),IConstant.CourseEnum.COURSE_TYPE_CREATED.getCode())){
                //查询章节数
                Long courseChapterCount = courseChapterDao.selectCount(Wrappers.<CourseChapter>lambdaQuery().eq(CourseChapter::getCourseId, coursePageResponse.getId()).eq(CourseChapter::getLevel, 0));
                coursePageResponse.setCourseChapterNumber(MathHelper.nvl(courseChapterCount));
                //查询已学习人数
                Long count = courseUserDao.selectCount(Wrappers.<CourseUser>lambdaQuery().eq(CourseUser::getCourseId, coursePageResponse.getId()));
                coursePageResponse.setLearnedNumber(MathHelper.nvl(count));
            }
        }
        pagination.getList().addAll(coursePageResponses);
        return pagination;
    }

    @Override
    public CourseUserResponse courseUser(Long id) {
        CourseUserResponse courseUserResponse = new CourseUserResponse();
        CourseInfo courseInfo = this.baseMapper.selectById(id);
        Optional.ofNullable(courseInfo).orElseThrow(() -> new ServiceException(CodeMSG.COURSE_NO));

        //获取在线人数
        String count = redisHelper.getNoFormat(IConstant.Redis.REDIS_COURSE_USER_COUNT + id);
        courseUserResponse.nowStudentCount = Long.parseLong(StringHelper.defaultIfBlank(count,"0"));
        Long userId = LoginUtil.getUserId();
        LambdaQueryWrapper<CourseUser> eq = Wrappers.<CourseUser>lambdaQuery()
                                                    .eq(CourseUser::getStudentId, userId)
                                                    .eq(CourseUser::getCourseId,id);
        CourseUser courseUser = courseUserDao.selectOne(eq);
        if(Objects.nonNull(courseUser)){
            courseUserResponse.learningCount = courseUser.getLearningCount();
            courseUserResponse.learningDuration = courseUser.getLearningDuration();
            //查询章节列表
            List<CourseChapter> courseChapters = courseChapterDao.selectList(Wrappers.<CourseChapter>lambdaQuery().eq(CourseChapter::getCourseId, id).eq(CourseChapter::getLevel, 0));
            List<CourseUserDetailDTO> courseUserDetailList = BeanHelper.castTo(courseChapters, CourseUserDetailDTO.class);
            //todo 优化遍历
            for (CourseUserDetailDTO courseUserDetailDTO : courseUserDetailList) {
                LambdaQueryWrapper<CourseChapterDetail> chapterDetailWrapper = Wrappers.<CourseChapterDetail>lambdaQuery()
                                                                      .eq(CourseChapterDetail::getCourseId, id)
                                                                      .eq(CourseChapterDetail::getCourseChapterLevelParentId, courseUserDetailDTO.getId());
                //需要完成章节数
                Long needChapterCount = courseChapterDetailDao.selectCount(chapterDetailWrapper);
                courseUserDetailDTO.setNeedChapterCount(MathHelper.nvl(needChapterCount));
                LambdaQueryWrapper<CourseChapterUser> chapterUserWrapper = Wrappers.<CourseChapterUser>lambdaQuery()
                                                                                   .eq(CourseChapterUser::getStudentId,userId)
                                                                                   .eq(CourseChapterUser::getCourseId, id)
                                                                                   .eq(CourseChapterUser::getCourseChapterLevelParentId,courseUserDetailDTO.getId());

                //已完成章节数
                Long finishedChapterCount = courseChapterUserDao.selectCount(chapterUserWrapper);
                courseUserDetailDTO.setFinishedChapterCount(MathHelper.nvl(finishedChapterCount));
            }
        }
        return courseUserResponse;
    }
}

