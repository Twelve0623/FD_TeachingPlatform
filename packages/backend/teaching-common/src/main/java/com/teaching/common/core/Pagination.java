package com.teaching.common.core;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * 分页条信息
 **/
public final class Pagination<T> {
    /**
     * 当前页
     **/
    @JSONField(ordinal = 0)
    @Schema(title = "当前页")
    private int pager;
    /**
     * 总页数
     **/
    @JSONField(ordinal = 1)
    @Schema(title = "总页数")
    private int pages;
    /**
     * 每页条数
     **/
    @JSONField(ordinal = 2)
    @Schema(title = "每页条数")
    private int size;
    /**
     * 总条数
     **/
    @JSONField(ordinal = 3)
    @Schema(title = "总条数")
    private long total;
    /**
     * 过滤前多少条
     **/
    private transient int offset;
    /**
     * 数据列表
     **/
    @JSONField(ordinal = 4)
    @Schema(title = "数据列表")
    private List<T> list = Lists.newArrayList();

    @SuppressWarnings("unused")
    public Pagination() {
    }

    public Pagination(int pager, int size) {
        if (size < 1) {
            throw new RuntimeException("invalid  size: " + size);
        }
        this.pager = pager;
        this.size = size;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Deprecated
    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPager() {
        return 0 == pager ? 1 : pager;
    }

    public int getSize() {
        return size;
    }

    public int getPages() {
        return Double.valueOf(Math.ceil((double) total / (double) size)).intValue();
    }

    public int getOffset() {
        return size * (getPager() - 1);
    }

    public List<T> getList() {
        return list;
    }

    public long getTotal() {
        return total;
    }

}
