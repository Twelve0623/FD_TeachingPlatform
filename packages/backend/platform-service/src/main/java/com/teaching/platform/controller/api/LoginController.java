package com.teaching.platform.controller.api;


import com.teaching.common.core.ResultReply;
import com.teaching.platform.dto.request.LoginRequest;
import com.teaching.platform.dto.response.CaptchaResponse;
import com.teaching.platform.dto.response.LoginResponse;
import com.teaching.platform.service.LoginService;
import com.teaching.platform.service.StudentInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author sacher
 * @since 2023-08-09 22:13:29
 */
@RestController
@RequestMapping("/login")
@Tag(name = "登录信息")
public class LoginController {
    /**
     * 服务对象
     */
    @Resource
    private StudentInfoService studentInfoService;

    @Resource
    private LoginService loginService;

    @Operation(summary = "获取验证码")
    @GetMapping("/getCode")
    ResultReply<CaptchaResponse> getCode(){
        return ResultReply.onOk(loginService.getCode());
    }


    @Operation(summary = "登录")
    @PostMapping(value = "/studentLogin")
    ResultReply<LoginResponse> studentLogin(@Validated @RequestBody LoginRequest request){
        return ResultReply.onOk(studentInfoService.studentLogin(request));
    }

}

