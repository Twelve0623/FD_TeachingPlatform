package com.teaching.platform.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.teaching.common.core.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 课程用户记录(CourseUser)表实体类
 *
 * @author sacher
 * @since 2023-10-06 18:56:13
 */
@Data
@Accessors(chain = true)
@TableName(value = "course_user", autoResultMap = true)
public class CourseUser extends BaseEntity {
    /** 主键ID **/
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * 课程ID
     */
    private Long courseId;
    /**
     * 学生ID
     */
    private Long studentId;
    /**
     * 学习时长 /s
     */
    private Long learningDuration;
    /**
     * 学习次数
     */
    private Long learningCount;

}

