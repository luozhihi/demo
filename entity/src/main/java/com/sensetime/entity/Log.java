package com.sensetime.entity;

import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable{
    private Integer id;
    private Integer userId;
    private String action;
    private String params;
    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", userId=" + userId +
                ", action='" + action + '\'' +
                ", params='" + params + '\'' +
                ", time=" + time +
                '}';
    }
}
