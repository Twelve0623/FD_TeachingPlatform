package com.teaching.platform.controller.api;


import com.teaching.common.core.PageRequest;
import com.teaching.common.core.Pagination;
import com.teaching.common.core.ResultReply;
import com.teaching.platform.dto.request.LoginRequest;
import com.teaching.platform.dto.request.UpdatePasswordRequest;
import com.teaching.platform.dto.response.CoursePageResponse;
import com.teaching.platform.dto.response.LoginResponse;
import com.teaching.platform.dto.response.StudentResponse;
import com.teaching.platform.entity.CourseInfo;
import com.teaching.platform.service.StudentInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    @ApiOperation("修改密码")
    @PostMapping(value = "/updatePassword")
    ResultReply<Integer> updatePassword(@RequestBody UpdatePasswordRequest request){
        return ResultReply.onOk(studentInfoService.updatePassword(request));
    }

    @ApiOperation("个人信息")
    @PostMapping(value = "/me")
    ResultReply<StudentResponse> me(){
        return ResultReply.onOk(studentInfoService.me());
    }

}

