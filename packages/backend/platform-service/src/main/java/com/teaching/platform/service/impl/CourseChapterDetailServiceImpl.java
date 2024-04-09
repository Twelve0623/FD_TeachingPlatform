package com.teaching.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.LogUtils;
import com.teaching.common.exception.ServiceException;
import com.teaching.common.helper.BeanHelper;
import com.teaching.common.helper.MathHelper;
import com.teaching.platform.core.LoginUtil;
import com.teaching.platform.dao.CourseChapterDetailDao;
import com.teaching.platform.dao.CourseChapterUserDao;
import com.teaching.platform.dto.response.CourseChapterDetailResponse;
import com.teaching.platform.entity.CourseChapterDetail;
import com.teaching.platform.entity.CourseChapterUser;
import com.teaching.platform.kern.CodeMSG;
import com.teaching.platform.service.CourseChapterDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

/**
 * 课程章节详情信息(CourseChapterDetail)表服务实现类
 *
 * @author sacher
 * @since 2023-10-06 14:05:03
 */
@Service("courseChapterDetailService")
public class CourseChapterDetailServiceImpl extends ServiceImpl<CourseChapterDetailDao, CourseChapterDetail> implements CourseChapterDetailService {

    @Resource
    private CourseChapterUserDao courseChapterUserDao;

    @Override
    public CourseChapterDetailResponse courseChapterDetailById(Long id) {

        LambdaQueryWrapper<CourseChapterDetail> eq = Wrappers.<CourseChapterDetail>lambdaQuery().eq(CourseChapterDetail::getCourseChapterId, id);
        CourseChapterDetail courseChapterDetail = this.baseMapper.selectOne(eq);
        Optional.ofNullable(courseChapterDetail).orElseThrow(() -> new ServiceException(CodeMSG.COURSE_CHAPTER_DETAIL_NO));
        //更新章节进度 TODO
        Long userId = LoginUtil.getUserId();
        LambdaQueryWrapper<CourseChapterUser> chapterUserWrapper = Wrappers.<CourseChapterUser>lambdaQuery()
                                                            .eq(CourseChapterUser::getStudentId, userId)
                                                            .eq(CourseChapterUser::getCourseId, courseChapterDetail.getCourseId())
                                                            .eq(CourseChapterUser::getCourseChapterId, id.intValue());
        CourseChapterUser courseChapterUser = courseChapterUserDao.selectOne(chapterUserWrapper);
        CourseChapterUser newCourseChapterUser = Optional.ofNullable(courseChapterUser).orElseGet(() -> new CourseChapterUser(courseChapterDetail.getCourseId(), id.intValue(), userId,courseChapterDetail.getCourseChapterLevelParentId()));
        if(Objects.isNull(newCourseChapterUser.getId())){
            //新增
            courseChapterUserDao.insert(courseChapterUser);
        }

        return BeanHelper.castTo(courseChapterDetail,CourseChapterDetailResponse.class);
    }
}

