package com.mini.webtest;

import com.mini.web.RequestMapping;

public class HelloWorldBean {

    @RequestMapping("/helloWorld")
    public String doGet(){
        System.out.println("hello world");
        return "hello world!";
    }
}
