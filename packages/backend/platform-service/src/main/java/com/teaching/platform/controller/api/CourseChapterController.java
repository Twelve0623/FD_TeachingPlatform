package com.teaching.platform.controller.api;


import com.teaching.common.core.ResultReply;
import com.teaching.platform.dto.response.CourseChapterDetailResponse;
import com.teaching.platform.dto.response.CourseChapterResponse;
import com.teaching.platform.service.CourseChapterDetailService;
import com.teaching.platform.service.CourseChapterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程章节信息(CourseChapter)表控制层
 *
 * @author sacher
 * @since 2023-10-06 14:05:02
 */
@RestController
@RequestMapping("/courseChapter")
@Tag(name = "课程章节")
public class CourseChapterController {
    /**
     * 服务对象章节
     */
    @Resource
    private CourseChapterService courseChapterService;

    /**
     * 服务对象章节详情
     */
    @Resource
    private CourseChapterDetailService courseChapterDetailService;

    @Operation(summary = "根据等级获取章节列表")
    @GetMapping(value = "/courseChapterByLevel")
    ResultReply<List<CourseChapterResponse>> courseChapterByLevel(@Parameter(name = "等级") @RequestParam(value = "level") Integer level,
                                                                  @Parameter(name = "课程ID") @RequestParam(value = "courseId") Long courseId){
        return ResultReply.onOk(courseChapterService.courseChapterByLevel(level,courseId));
    }

    @Operation(summary = "根据父ID获取章节列表")
    @GetMapping(value = "/courseChapterByParentId")
    ResultReply<List<CourseChapterResponse>> courseChapterByParentId(@Parameter(name = "父ID") @RequestParam(value = "parentId") Integer parentId,
                                                                     @Parameter(name = "课程ID") @RequestParam(value = "courseId") Long courseId){
        return ResultReply.onOk(courseChapterService.courseChapterByParentId(parentId,courseId));
    }

    @Operation(summary = "根据章节ID获取章节详情内容")
    @GetMapping(value = "/courseChapterDetailById")
    ResultReply<CourseChapterDetailResponse> courseChapterDetailById(@Parameter(name = "id") @RequestParam(value = "id") Long id){
        return ResultReply.onOk(courseChapterDetailService.courseChapterDetailById(id));
    }
}

