package com.mini.test;

import com.mini.beans.factory.ClassPathXmlApplicationContext;
import com.mini.exception.BeansException;

public class Test1 {
    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml");

        AService aservice = (AService)classPathXmlApplicationContext.getBean("aservice");
        aservice.sayHello();
    }
}
