package com.mini.test;

public class BaseService {

    private BaseBaseService bbs;
    private String property;

    public BaseService() {
    }

    public BaseService(String property) {
        this.property = property;
    }

    public BaseBaseService getBbs() {
        return bbs;
    }

    public void setBbs(BaseBaseService bbs) {
        this.bbs = bbs;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
