package com.teaching.platform.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.teaching.common.core.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 课程章节信息(CourseChapter)表实体类
 *
 * @author sacher
 * @since 2023-10-06 14:05:03
 */
@Data
@Accessors(chain = true)
@TableName(value = "course_chapter", autoResultMap = true)
public class CourseChapter extends BaseEntity {
    /** 主键ID **/
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程ID
     */
    private Long courseId;
    /**
     * 父节点id
     */
    private Integer parentId;
    /**
     * 等级 0
     */
    private Integer level;
    /**
     * 章节标题 例：绪论
     */
    private String chapterName;
    /**
     * 章节描述 例：第一章
     */
    private String chapterDesc;
    /**
     * 章节页面标记 是否页面内章节 0 否 1 是
     */
    private Integer chapterTag;

    /**
     * 排序
     */
    private Integer sort;


}

