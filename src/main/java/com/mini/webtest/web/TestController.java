package com.mini.webtest.web;

import com.mini.web.RequestMapping;

public class TestController {

    @RequestMapping("/helloWorld02")
    public String doGet(){
        System.out.println("hello world02");
        return "hello world02!";
    }
}
