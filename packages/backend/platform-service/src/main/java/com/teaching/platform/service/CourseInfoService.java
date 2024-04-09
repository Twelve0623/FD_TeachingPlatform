package com.teaching.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.teaching.common.core.PageRequest;
import com.teaching.common.core.Pagination;
import com.teaching.platform.dto.response.CoursePageResponse;
import com.teaching.platform.dto.response.CourseUserResponse;
import com.teaching.platform.entity.CourseInfo;

/**
 * 课程信息表(CourseInfo)表服务接口
 *
 * @author sacher
 * @since 2023-10-06 14:05:03
 */
public interface CourseInfoService extends IService<CourseInfo> {

    Pagination<CoursePageResponse> coursePage(PageRequest<CourseInfo> request);

    CourseUserResponse courseUser(Long id);
}

