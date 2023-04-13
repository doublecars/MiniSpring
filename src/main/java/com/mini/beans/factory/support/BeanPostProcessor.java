package com.mini.beans.factory.support;

import com.mini.beans.factory.BeanFactory;

public interface BeanPostProcessor {
    Object postProcessorBeforeInitialization(Object bean, String beanName);
    Object postProcessorAfterInitialization(Object bean, String beanName);
    void setBeanFactory(BeanFactory beanFactory);
}
