package com.teaching.platform.controller.api;


import com.teaching.common.core.ResultReply;
import com.teaching.platform.dto.request.LoginRequest;
import com.teaching.platform.dto.response.CaptchaResponse;
import com.teaching.platform.dto.response.LoginResponse;
import com.teaching.platform.service.LoginService;
import com.teaching.platform.service.StudentInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *
 * @author sacher
 * @since 2023-08-09 22:13:29
 */
@RestController
@RequestMapping("/login")
@Api(tags = {"登录信息"})
public class LoginController {
    /**
     * 服务对象
     */
    @Resource
    private StudentInfoService studentInfoService;

    @Resource
    private LoginService loginService;

    @ApiOperation("获取验证码")
    @GetMapping("/getCode")
    ResultReply<CaptchaResponse> getCode(){
        return ResultReply.onOk(loginService.getCode());
    }


    @ApiOperation("登录")
    @PostMapping(value = "/studentLogin")
    ResultReply<LoginResponse> studentLogin(@Validated @RequestBody LoginRequest request){
        return ResultReply.onOk(studentInfoService.studentLogin(request));
    }

}

