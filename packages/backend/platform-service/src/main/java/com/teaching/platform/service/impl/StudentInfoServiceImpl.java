package com.teaching.platform.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teaching.common.exception.ServiceException;
import com.teaching.common.helper.BeanHelper;
import com.teaching.common.helper.DateHelper;
import com.teaching.common.helper.JwtHelper;
import com.teaching.common.helper.RedisHelper;
import com.teaching.common.helper.StringHelper;
import com.teaching.platform.core.LoginUtil;
import com.teaching.platform.dao.StudentInfoDao;
import com.teaching.platform.dto.request.LoginRequest;
import com.teaching.platform.dto.request.UpdatePasswordRequest;
import com.teaching.platform.dto.response.LoginResponse;
import com.teaching.platform.dto.response.StudentResponse;
import com.teaching.platform.entity.StudentInfo;
import com.teaching.platform.kern.CodeMSG;
import com.teaching.platform.kern.IConstant;
import com.teaching.platform.service.StudentInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 学生信息表(StudentInfo)表服务实现类
 *
 * @author sacher
 * @since 2023-08-09 22:13:33
 */
@Service("studentInfoService")
public class StudentInfoServiceImpl extends ServiceImpl<StudentInfoDao, StudentInfo> implements StudentInfoService {

    @Resource
    private RedisHelper redisHelper;

    @Override
    public LoginResponse studentLogin(LoginRequest request) {
        LoginResponse loginResponse = new LoginResponse();
        //验证码校验 TODO
        String verifyKey = IConstant.Redis.REDIS_LOGIN_CODE + request.uuid;
        //获取验证码
        String code = (String) redisHelper.get(verifyKey);
        redisHelper.del(verifyKey);
        if(StringHelper.isBlank(code)){
            throw new ServiceException(CodeMSG.CODE_NO);
        }
        if(StringHelper.isBlank(request.code) || !request.code.equalsIgnoreCase(code)){
            throw new ServiceException(CodeMSG.CODE_ERROR);
        }
        String cryptogram = LoginUtil.adminPwd(request.password);
        StudentInfo studentInfo = this.baseMapper.selectOne(Wrappers.<StudentInfo>lambdaQuery().eq(StudentInfo::getStudentNo, request.studentNo));
        checkUser(cryptogram, studentInfo);
        studentInfo.setLastLoginTime(DateHelper.now());
        studentInfo.setLoginCount(studentInfo.getLoginCount() + 1);
        this.baseMapper.updateById(studentInfo);
        long expire = DateHelper.time() + 30 * DateHelper.DAY_TIME;
        String token = JwtHelper.signatureJWT(StringHelper.EMPTY, studentInfo.getId().toString(), StringHelper.EMPTY, expire);
        loginResponse.token = token;
        redisHelper.set(IConstant.Redis.REDIS_STUDENT_LOGIN_TOKEN + token, studentInfo.getId(), IConstant.Redis.EXPIRE);
        String oriPass = LoginUtil.adminPwd(studentInfo.getStudentNo().substring(studentInfo.getStudentNo().length() - 6));
        if(oriPass.equals(studentInfo.getPassword())){
            loginResponse.isDefault = Boolean.TRUE;
        }
        return loginResponse;
    }

    @Override
    public Integer updatePassword(UpdatePasswordRequest request) {
        String originalPassword = LoginUtil.adminPwd(request.originalPassword);
        String password = LoginUtil.adminPwd(request.password);
        StudentInfo studentInfo = this.baseMapper.selectById(LoginUtil.getUserId());
        checkUser(originalPassword, studentInfo);
        studentInfo.setPassword(password);
        return this.baseMapper.updateById(studentInfo);
    }

    @Override
    public StudentResponse me() {
        StudentInfo studentInfo = this.baseMapper.selectById(LoginUtil.getUserId());
        return BeanHelper.castTo(studentInfo,StudentResponse.class);
    }

    private void checkUser(String cryptogram, StudentInfo studentInfo) {
        if(Objects.isNull(studentInfo) || !studentInfo.getPassword().equals(cryptogram)){
            throw new ServiceException(CodeMSG.USER_PASS_ERROR);
        }
        if(Objects.equals(studentInfo.getDelFlag(), IConstant.UserDelFlag.DEL.getCode())){
            throw new ServiceException(CodeMSG.USER_DEl);
        }
        if(Objects.equals(studentInfo.getStatus(), IConstant.UserStatus.DISABLE.getCode())){
            throw new ServiceException(CodeMSG.USER_STATUS_ERROR);
        }
    }
}

