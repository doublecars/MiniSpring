package com.mini.beans.factory;

import com.mini.beans.factory.support.SimpleBeanFactory;
import com.mini.beans.factory.xml.XmlBeanDefinitionReader;
import com.mini.context.ApplicationEvent;
import com.mini.context.ApplicationEventPublisher;
import com.mini.core.ClassPathXmlResource;
import com.mini.exception.BeansException;

public class ClassPathXmlApplicationContext
        implements BeanFactory, ApplicationEventPublisher {
   SimpleBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh){
        SimpleBeanFactory simpleBeanFactory = new SimpleBeanFactory();
        ClassPathXmlResource resource = new ClassPathXmlResource(fileName);
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(simpleBeanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory  = simpleBeanFactory;
        if (isRefresh){
            this.beanFactory.refresh();
        }
    }

    public Object getBean(String beanName) throws BeansException {
        return this.beanFactory.getBean(beanName);
    }


    @Override
    public Boolean containsBean(String beanName) {
        return this.beanFactory.containsBean(beanName);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanFactory.isSingleton(name);
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanFactory.isPrototype(name);
    }

    @Override
    public Class<?> getType(String name) {
        return this.beanFactory.getType(name);
    }

    @Override
    public void publishEvent(ApplicationEvent event) {

    }
}
