package com.teaching.platform.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.teaching.common.core.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 课程信息表(CourseInfo)表实体类
 *
 * @author sacher
 * @since 2023-10-06 14:05:03
 */
@Data
@Accessors(chain = true)
@TableName(value = "course_info", autoResultMap = true)
public class CourseInfo extends BaseEntity {
    /** 主键ID **/
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 课程简介
     */
    private String courseProfile;
    /**
     * 课程图片
     */
    private String courseImages;
    /**
     * 课程类型 0 自建课程 1 外链
     */
    private Integer courseType;
    /**
     * 课程拓展字段（外链需要）
     */
    private String courseExt;

    /**
     * 是否开放 0 开放 1 未开放
     */
    private Integer courseStatus;

}

