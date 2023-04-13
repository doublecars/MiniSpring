package com.mini.beans.factory;

import com.mini.exception.BeansException;

import java.util.Map;

/*
enhance bean factory
 get bean list information
 */
public interface ListableBeanFactory extends BeanFactory{
    boolean containsBeanDefinition(String beanName);
    int getBeanDefinitionCount();
    String[] getBeanDefinitionNames();
    String[] getBeanNamesForType(Class<?> type);
    <T> Map<String,T> getBeansOfType(Class<T> type) throws BeansException;
}
