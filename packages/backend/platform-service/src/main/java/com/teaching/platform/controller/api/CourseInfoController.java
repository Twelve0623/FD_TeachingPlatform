package com.teaching.platform.controller.api;


import com.teaching.common.core.PageRequest;
import com.teaching.common.core.Pagination;
import com.teaching.common.core.ResultReply;
import com.teaching.platform.dto.response.CoursePageResponse;
import com.teaching.platform.dto.response.CourseUserResponse;
import com.teaching.platform.entity.CourseInfo;
import com.teaching.platform.service.CourseInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


/**
 * 课程信息表(CourseInfo)表控制层
 *
 * @author sacher
 * @since 2023-10-06 14:05:03
 */
@RestController
@RequestMapping("/course")
@Tag(name = "课程信息")
public class CourseInfoController{
    /**
     * 服务对象
     */
    @Resource
    private CourseInfoService courseInfoService;

    @Operation(summary = "获取首页课程列表")
    @PostMapping(value = "/coursePage")
    ResultReply<Pagination<CoursePageResponse>> coursePage(@RequestBody PageRequest<CourseInfo> request){
        return ResultReply.onOk(courseInfoService.coursePage(request));
    }

    @Operation(summary = "课程用户数据统计")
    @GetMapping(value = "/courseUser")
    ResultReply<CourseUserResponse> courseUser(@Parameter(name = "课程ID") @RequestParam(value = "id") Long id){
        return ResultReply.onOk(courseInfoService.courseUser(id));
    }

}

