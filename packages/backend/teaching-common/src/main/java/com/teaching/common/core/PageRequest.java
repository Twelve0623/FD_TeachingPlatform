package com.teaching.common.core;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/8/17 23:13
 **/
@ApiModel("请求体")
public class PageRequest<T> {
    @ApiModelProperty("当前页 默认1")
    public Integer pager = 1;
    @ApiModelProperty("每页条数 默认20")
    public Integer size = 20;

    public Page<T> toMybatisPage() {
        return new Page<>(pager, size);
    }
}
