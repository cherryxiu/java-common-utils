package com.cn.sendemail.model;

import org.jeecgframework.poi.excel.annotation.Excel;

import java.util.Date;

public class User {
    @Excel(name = "id", orderNum = "0")
    private String id;

    @Excel(name = "用户名", orderNum = "1")
    private String username;

   @Excel(name = "密码", orderNum = "2")
    private String password;

    @Excel(name = "开始时间", orderNum = "3")
    private Date startDate;


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

}