package com.teaching.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.teaching.platform.dto.response.CourseChapterDetailResponse;
import com.teaching.platform.entity.CourseChapterDetail;

/**
 * 课程章节详情信息(CourseChapterDetail)表服务接口
 *
 * @author sacher
 * @since 2023-10-06 14:05:03
 */
public interface CourseChapterDetailService extends IService<CourseChapterDetail> {

    CourseChapterDetailResponse courseChapterDetailById(Long id);
}

