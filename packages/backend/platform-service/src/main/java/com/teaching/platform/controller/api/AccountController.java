package com.teaching.platform.controller.api;

import com.teaching.common.core.ResultReply;
import com.teaching.platform.dto.response.TestResponse;
import com.teaching.platform.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/2/12 23:36
 **/
@RequestMapping("/account")
@RestController
public class AccountController {

    @Resource
    private AccountService accountService;

    @GetMapping(value = "/demo",name = "测试DEMO")
    ResultReply<List<TestResponse>> demo(){
        return ResultReply.onOk(accountService.demo());
    }
}
