package com.mini.test;

import com.mini.beans.factory.annotation.Autowired;

public class AServiceImpl implements AService {

    private String name;
    private Integer level;
    private String property1;
    private String property2;
    @Autowired
    private BaseService baseService;

    public AServiceImpl() {
    }

    public AServiceImpl(Integer level,String name) {
        this.name = name;
        this.level = level;
        System.out.println(this.name + "," + level);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public String getProperty2() {
        return property2;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    @Override
    public void sayHello() {
        System.out.println("hello thank you thank you very much...");
        System.out.println(property1 + property2);
    }

    public BaseService getBaseService() {
        return baseService;
    }

    public void setBaseService(BaseService baseService) {
        this.baseService = baseService;
    }
}
