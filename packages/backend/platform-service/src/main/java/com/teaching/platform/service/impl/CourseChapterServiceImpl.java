package com.teaching.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teaching.common.helper.BeanHelper;
import com.teaching.common.helper.MathHelper;
import com.teaching.platform.dao.CourseChapterDao;
import com.teaching.platform.dto.response.CourseChapterDetailResponse;
import com.teaching.platform.dto.response.CourseChapterResponse;
import com.teaching.platform.entity.CourseChapter;
import com.teaching.platform.service.CourseChapterService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程章节信息(CourseChapter)表服务实现类
 *
 * @author sacher
 * @since 2023-10-06 14:05:03
 */
@Service("courseChapterService")
public class CourseChapterServiceImpl extends ServiceImpl<CourseChapterDao, CourseChapter> implements CourseChapterService {

    @Override
    public List<CourseChapterResponse> courseChapterByLevel(Integer level, Long courseId) {
        LambdaQueryWrapper<CourseChapter> eq = Wrappers.<CourseChapter>lambdaQuery()
                                                       .eq(CourseChapter::getCourseId, courseId)
                                                       .eq(CourseChapter::getLevel, level);
        List<CourseChapter> courseChapters = this.baseMapper.selectList(eq);
        return extractedHasChild(courseChapters);
    }

    @Override
    public List<CourseChapterResponse> courseChapterByParentId(Integer parentId, Long courseId) {
        LambdaQueryWrapper<CourseChapter> eq = Wrappers.<CourseChapter>lambdaQuery()
                                                       .eq(CourseChapter::getCourseId, courseId)
                                                       .eq(CourseChapter::getParentId, parentId);
        List<CourseChapter> courseChapters = this.baseMapper.selectList(eq);
        return extractedHasChild(courseChapters);
    }


    private List<CourseChapterResponse> extractedHasChild(List<CourseChapter> courseChapters) {
        List<CourseChapterResponse> courseChapterResponses = BeanHelper.castTo(courseChapters, CourseChapterResponse.class);
        for (CourseChapterResponse courseChapterResponse : courseChapterResponses) {
            Long count = this.baseMapper.selectCount(Wrappers.<CourseChapter>lambdaQuery().eq(CourseChapter::getParentId, courseChapterResponse.getParentId()));
            courseChapterResponse.setHasChild(MathHelper.nvl(count) > 0);
        }
        return courseChapterResponses;
    }
}

