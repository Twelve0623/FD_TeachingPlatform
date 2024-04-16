package com.teaching.platform.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.teaching.common.core.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 课程章节用户记录(CourseChapterUser)表实体类
 *
 * @author sacher
 * @since 2023-10-06 18:55:58
 */
@Data
@Accessors(chain = true)
@TableName(value = "course_chapter_user", autoResultMap = true)
public class CourseChapterUser extends BaseEntity {
    /** 主键ID **/
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * 课程ID
     */
    private Long courseId;
    /**
     * 课程章节ID
     */
    private Integer courseChapterId;
    /**
     * 学生ID
     */
    private Long studentId;

    /**
     * 课程章节父ID 最大等级（最大章节）
     */
    private Integer courseChapterLevelParentId;

    public CourseChapterUser(Long courseId, Integer courseChapterId, Long studentId,Integer courseChapterLevelParentId) {
        this.courseId = courseId;
        this.courseChapterId = courseChapterId;
        this.studentId = studentId;
        this.courseChapterLevelParentId = courseChapterLevelParentId;
    }
}

