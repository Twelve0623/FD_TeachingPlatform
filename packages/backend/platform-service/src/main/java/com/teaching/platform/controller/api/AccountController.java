package com.teaching.platform.controller.api;

import com.teaching.common.core.ResultReply;
import com.teaching.platform.dto.request.DemoRequest;
import com.teaching.platform.dto.response.TestResponse;
import com.teaching.platform.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@Api(tags = {"测试DEMO"})
public class AccountController {

    @Resource
    private AccountService accountService;

    @ApiOperation("测试DEMO")
    @PostMapping(value = "/demo")
    ResultReply<List<TestResponse>> demo(@RequestBody DemoRequest request){
        return ResultReply.onOk(accountService.demo(request));
    }
}
