package com.teaching.platform.controller.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teaching.common.core.PageRequest;
import com.teaching.common.core.Pagination;
import com.teaching.common.core.ResultReply;
import com.teaching.platform.dto.request.LoginRequest;
import com.teaching.platform.dto.response.CoursePageResponse;
import com.teaching.platform.dto.response.CourseUserResponse;
import com.teaching.platform.dto.response.LoginResponse;
import com.teaching.platform.entity.CourseInfo;
import com.teaching.platform.service.CourseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 课程信息表(CourseInfo)表控制层
 *
 * @author sacher
 * @since 2023-10-06 14:05:03
 */
@RestController
@RequestMapping("/course")
@Api(tags = {"课程信息"})
public class CourseInfoController{
    /**
     * 服务对象
     */
    @Resource
    private CourseInfoService courseInfoService;

    @ApiOperation("获取首页课程列表")
    @PostMapping(value = "/coursePage")
    ResultReply<Pagination<CoursePageResponse>> coursePage(@RequestBody PageRequest<CourseInfo> request){
        return ResultReply.onOk(courseInfoService.coursePage(request));
    }

    @ApiOperation("课程用户数据统计")
    @GetMapping(value = "/courseUser")
    ResultReply<CourseUserResponse> courseUser(@ApiParam(name = "课程ID") @RequestParam(value = "id") Long id){
        return ResultReply.onOk(courseInfoService.courseUser(id));
    }

}

