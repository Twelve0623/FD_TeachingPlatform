package com.teaching.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.teaching.platform.dto.response.CourseChapterDetailResponse;
import com.teaching.platform.dto.response.CourseChapterResponse;
import com.teaching.platform.entity.CourseChapter;

import java.util.List;

/**
 * 课程章节信息(CourseChapter)表服务接口
 *
 * @author sacher
 * @since 2023-10-06 14:05:03
 */
public interface CourseChapterService extends IService<CourseChapter> {

    List<CourseChapterResponse> courseChapterByLevel(Integer level, Long courseId);

    List<CourseChapterResponse> courseChapterByParentId(Integer parentId, Long courseId);

}

