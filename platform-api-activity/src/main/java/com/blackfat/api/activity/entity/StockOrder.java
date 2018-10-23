package com.blackfat.api.activity.entity;

import java.util.Date;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/10/23-16:13
 */
public class StockOrder {

    private Integer id;

    private Integer sid;

    private String name;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

