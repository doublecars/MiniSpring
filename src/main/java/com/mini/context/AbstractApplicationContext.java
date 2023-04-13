package com.mini.context;

import com.mini.beans.factory.ConfigurableListableBeanFactory;
import com.mini.beans.factory.config.BeanFactoryPostProcessor;
import com.mini.beans.factory.support.BeanPostProcessor;
import com.mini.core.env.Environment;
import com.mini.exception.BeansException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractApplicationContext implements ApplicationContext {

    private Environment environment;

    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    private long startupDate;

    private final AtomicBoolean active = new AtomicBoolean();
    private final AtomicBoolean close = new AtomicBoolean();

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.getBeanFactory().getBean(beanName);
    }

    public List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors() {
        return this.beanFactoryPostProcessors;
    }

    @Override
    public String getApplicationName() {
        return "";
    }

    @Override
    public long getStartupDate() {
        return this.startupDate;
    }

    @Override
    public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    @Override
    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        this.beanFactoryPostProcessors.add(postProcessor);
    }

    @Override
    public void refresh() throws BeansException, IllegalStateException {
        postProcessBeanFactory(this.getBeanFactory());
        registerBeanPostProcessors(this.getBeanFactory());
        initApplicationEventPublisher();
        onRefresh();
        registerListeners();
        finishedRefresh();
    }

    abstract void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory);

    abstract void initApplicationEventPublisher();

    abstract void registerListeners();

    abstract void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory);

    abstract void onRefresh();

    abstract void finishedRefresh();

    @Override
    public void close() {

    }

    @Override
    public boolean isActive() {
        return true;
    }


    @Override
    public Boolean containsBean(String beanName) {
        return this.getBeanFactory().containsBean(beanName);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.getBeanFactory().isSingleton(name);
    }

    @Override
    public boolean isPrototype(String name) {
        return this.getBeanFactory().isPrototype(name);
    }

    @Override
    public Class<?> getType(String name) {
        return this.getBeanFactory().getType(name);
    }

    @Override
    public Environment getEnvironment() {
        return this.environment;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    @Override
    public void registerSingleton(String beanName, Object singletonObj) {
        this.getBeanFactory().registerSingleton(beanName, singletonObj);
    }

    @Override
    public Boolean containsSingleton(String beanName) {
        return this.getBeanFactory().containsSingleton(beanName);
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.getBeanFactory().getSingleton(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return this.getBeanFactory().getSingletonNames();
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return this.getBeanFactory().containsBeanDefinition(beanName);
    }

    @Override
    public int getBeanDefinitionCount() {
        return this.getBeanFactory().getBeanDefinitionCount();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return this.getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return this.getBeanFactory().getBeanNamesForType(type);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return this.getBeanFactory().getBeansOfType(type);
    }


    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.getBeanFactory().addBeanPostProcessor(beanPostProcessor);
    }

    @Override
    public int getBeanPostProcessorCount() {
        return this.getBeanFactory().getBeanPostProcessorCount();
    }

    @Override
    public void registerDependentBean(String beanName, String dependentBeanName) {
        this.getBeanFactory().registerDependentBean(beanName, dependentBeanName);
    }

    @Override
    public String[] getDependentBeans(String beanName) {
        return this.getBeanFactory().getDependentBeans(beanName);
    }

    @Override
    public String[] getDependenciesForBean(String beanName) {
        return this.getBeanFactory().getDependentBeans(beanName);
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public ApplicationEventPublisher getApplicationEventPublisher() {
        return applicationEventPublisher;
    }
}
