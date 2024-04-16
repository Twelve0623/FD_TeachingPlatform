package com.teaching.platform.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/10/6 14:20
 **/
@Data
public class CourseUserDetailDTO {

    /**
     * 章节ID
     */
    private transient Long id;
    /**
     * 章节标题 例：绪论
     */
    @Schema(title = "章节标题 例：绪论")
    private String chapterName;
    /**
     * 章节描述 例：第一章
     */
    @Schema(title = "章节描述 例：第一章/1.1")
    private String chapterDesc;

    @Schema(title = "需要完成章节数")
    private Long needChapterCount;

    @Schema(title = "已完成章节数")
    private Long finishedChapterCount;


}
