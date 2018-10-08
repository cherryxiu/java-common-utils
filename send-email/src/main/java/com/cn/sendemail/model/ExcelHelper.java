package com.cn.sendemail.model;

import java.util.List;

public class ExcelHelper<T> {
    private String sheetName;
    private Class<T> cls;
    private List<T> list;

    public ExcelHelper(String sheetName, Class<T> cls, List<T> list) {
        this.sheetName = sheetName;
        this.cls = cls;
        this.list = list;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Class<T> getCls() {
        return cls;
    }

    public void setCls(Class<T> cls) {
        this.cls = cls;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}