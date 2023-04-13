package com.mini.beans.factory.support;

import com.mini.beans.factory.BeanFactory;
import com.mini.exception.BeansException;

import java.util.ArrayList;
import java.util.List;

public interface AutowiredCapableBeanFactory extends BeanFactory {
     int AUTOWIRE_NO = 0;
     int AUTOWIRE_BY_NAME = 1;
     int AUTOWIRE_BY_TYPE = 2;
     Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException;
     Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
