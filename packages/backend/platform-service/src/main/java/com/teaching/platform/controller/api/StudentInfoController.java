package com.teaching.platform.controller.api;


import com.teaching.common.core.ResultReply;
import com.teaching.platform.dto.request.UpdatePasswordRequest;
import com.teaching.platform.dto.response.StudentResponse;
import com.teaching.platform.service.StudentInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 学生信息表(StudentInfo)表控制层
 *
 * @author sacher
 * @since 2023-08-09 22:13:29
 */
@RestController
@RequestMapping("/studentInfo")
@Tag(name = "学生信息类")
public class StudentInfoController {
    /**
     * 服务对象
     */
    @Resource
    private StudentInfoService studentInfoService;

    @Operation(summary = "修改密码")
    @PostMapping(value = "/updatePassword")
    ResultReply<Integer> updatePassword(@RequestBody UpdatePasswordRequest request){
        return ResultReply.onOk(studentInfoService.updatePassword(request));
    }

    @Operation(summary = "个人信息")
    @GetMapping(value = "/me")
    ResultReply<StudentResponse> me(){
        return ResultReply.onOk(studentInfoService.me());
    }

}

