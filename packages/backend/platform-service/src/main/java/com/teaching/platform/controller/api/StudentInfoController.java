package com.teaching.platform.controller.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.ImmutableMap;
import com.teaching.common.core.ResultReply;
import com.teaching.platform.dto.request.DemoRequest;
import com.teaching.platform.dto.request.LoginRequest;
import com.teaching.platform.dto.response.LoginResponse;
import com.teaching.platform.dto.response.TestResponse;
import com.teaching.platform.entity.StudentInfo;
import com.teaching.platform.service.StudentInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 学生信息表(StudentInfo)表控制层
 *
 * @author sacher
 * @since 2023-08-09 22:13:29
 */
@RestController
@RequestMapping("/studentInfo")
@Api(tags = {"学生信息类"})
public class StudentInfoController {
    /**
     * 服务对象
     */
    @Resource
    private StudentInfoService studentInfoService;

    @ApiOperation("登录")
    @PostMapping(value = "/login")
    ResultReply<LoginResponse> login(@Validated @RequestBody LoginRequest request){
        return ResultReply.onOk(studentInfoService.login(request));
    }
}

