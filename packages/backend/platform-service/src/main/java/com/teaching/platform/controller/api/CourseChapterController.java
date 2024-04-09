package com.teaching.platform.controller.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teaching.common.core.PageRequest;
import com.teaching.common.core.Pagination;
import com.teaching.common.core.ResultReply;
import com.teaching.platform.dto.response.CourseChapterDetailResponse;
import com.teaching.platform.dto.response.CourseChapterResponse;
import com.teaching.platform.dto.response.CoursePageResponse;
import com.teaching.platform.entity.CourseChapter;
import com.teaching.platform.entity.CourseInfo;
import com.teaching.platform.service.CourseChapterDetailService;
import com.teaching.platform.service.CourseChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 课程章节信息(CourseChapter)表控制层
 *
 * @author sacher
 * @since 2023-10-06 14:05:02
 */
@RestController
@RequestMapping("/courseChapter")
@Api(tags = {"课程章节"})
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

    @ApiOperation("根据等级获取章节列表")
    @GetMapping(value = "/courseChapterByLevel")
    ResultReply<List<CourseChapterResponse>> courseChapterByLevel(@ApiParam(name = "等级") @RequestParam(value = "level") Integer level,
                                                                  @ApiParam(name = "课程ID") @RequestParam(value = "courseId") Long courseId){
        return ResultReply.onOk(courseChapterService.courseChapterByLevel(level,courseId));
    }

    @ApiOperation("根据父ID获取章节列表")
    @GetMapping(value = "/courseChapterByParentId")
    ResultReply<List<CourseChapterResponse>> courseChapterByParentId(@ApiParam(name = "父ID") @RequestParam(value = "parentId") Integer parentId,
                                                                     @ApiParam(name = "课程ID") @RequestParam(value = "courseId") Long courseId){
        return ResultReply.onOk(courseChapterService.courseChapterByParentId(parentId,courseId));
    }

    @ApiOperation("根据章节ID获取章节详情内容")
    @GetMapping(value = "/courseChapterDetailById")
    ResultReply<CourseChapterDetailResponse> courseChapterDetailById(@ApiParam(name = "id") @RequestParam(value = "id") Long id){
        return ResultReply.onOk(courseChapterDetailService.courseChapterDetailById(id));
    }
}

