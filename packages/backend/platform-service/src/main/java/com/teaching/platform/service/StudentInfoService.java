package com.teaching.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.teaching.platform.dto.request.LoginRequest;
import com.teaching.platform.dto.request.UpdatePasswordRequest;
import com.teaching.platform.dto.response.LoginResponse;
import com.teaching.platform.dto.response.StudentResponse;
import com.teaching.platform.entity.StudentInfo;

/**
 * 学生信息表(StudentInfo)表服务接口
 *
 * @author sacher
 * @since 2023-08-09 22:13:32
 */
public interface StudentInfoService extends IService<StudentInfo> {

    LoginResponse studentLogin(LoginRequest request);

    Integer updatePassword(UpdatePasswordRequest request);

    StudentResponse me();
}

