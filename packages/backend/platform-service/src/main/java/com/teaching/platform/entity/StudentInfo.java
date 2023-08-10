package com.teaching.platform.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.teaching.common.core.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 学生信息表(StudentInfo)表实体类
 *
 * @author sacher
 * @since 2023-08-09 22:38:30
 */
@Data
@Accessors(chain = true)
@TableName(value = "student_info", autoResultMap = true)
public class StudentInfo extends BaseEntity {

    /**
     * 姓名
     */
    private String studentName;
    /**
     * 学号
     */
    private String studentNo;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 专业名称
     */
    private String specialityName;
    /**
     * 删除 0：未删除 1 ：已删除
     */
    private Integer delFlag;
    /**
     * 状态 0:正常;1:锁定
     */
    private Integer status;
    /**
     * 登录次数
     */
    private Integer loginCount;
    /**
     * 最后一次登录时间
     */
    private Date lastLoginTime;

}

