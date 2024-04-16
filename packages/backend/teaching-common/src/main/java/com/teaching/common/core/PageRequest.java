package com.teaching.common.core;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/8/17 23:13
 **/
public class PageRequest<T> {
    @Schema(title = "当前页 默认1")
    public Integer pager = 1;
    @Schema(title = "每页条数 默认20")
    public Integer size = 20;

    public Page<T> toMybatisPage() {
        return new Page<>(pager, size);
    }
}
