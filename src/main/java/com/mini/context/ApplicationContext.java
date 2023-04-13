package com.mini.context;

import com.mini.beans.factory.ConfigurableBeanFactory;
import com.mini.beans.factory.ConfigurableListableBeanFactory;
import com.mini.beans.factory.ListableBeanFactory;
import com.mini.beans.factory.config.BeanFactoryPostProcessor;
import com.mini.core.env.EnvironmentCapable;
import com.mini.exception.BeansException;

public interface ApplicationContext
        extends EnvironmentCapable, ListableBeanFactory, ConfigurableBeanFactory, ApplicationEventPublisher {


    String getApplicationName();

    long getStartupDate();

    ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor beanFactoryPostProcessor);

    void refresh()throws BeansException, IllegalStateException;

    void close();

    boolean isActive();
}
