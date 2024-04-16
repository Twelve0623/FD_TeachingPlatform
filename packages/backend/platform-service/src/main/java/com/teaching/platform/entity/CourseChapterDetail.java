package com.teaching.platform.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.teaching.common.core.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 课程章节详情信息(CourseChapterDetail)表实体类
 *
 * @author sacher
 * @since 2023-10-06 14:05:03
 */
@Data
@Accessors(chain = true)
@TableName(value = "course_chapter_detail", autoResultMap = true)
public class CourseChapterDetail extends BaseEntity {
    /** 主键ID **/
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * 课程章节ID
     */
    private Integer courseChapterId;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 课程章节父ID 最大等级（最大章节）
     */
    private Integer courseChapterLevelParentId;

    /**
     * 章节详情信息
     */
    private String detailContent;

    /**
     * 章节类型 0 富文本 1 代码块 2 题库 3 动画
     */
    private Integer detailType;



}

