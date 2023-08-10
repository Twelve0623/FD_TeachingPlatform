package com.teaching.common.core;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity基类
 * @author sacher
 **/
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = -8416686909846848846L;
    protected BaseEntity() {
    }

    /** 主键ID **/
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    public Date createdTime;

    /**
     * 更新时间
     */
    public Date updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
