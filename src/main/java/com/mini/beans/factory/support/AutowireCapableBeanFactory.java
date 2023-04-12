package com.mini.beans.factory.support;

import com.mini.exception.BeansException;

import java.util.ArrayList;
import java.util.List;

public class AutowireCapableBeanFactory extends AbstractBeanFactory{

    private final List<AutowiredAnnotationBeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public void addBeanPostProcessor(AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor){
        this.beanPostProcessors.remove(autowiredAnnotationBeanPostProcessor);
        this.beanPostProcessors.add(autowiredAnnotationBeanPostProcessor);
    }


    public int getBeanPostProcessorCount(){
        return this.beanPostProcessors.size();
    }

    public List<AutowiredAnnotationBeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    @Override
    public Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (AutowiredAnnotationBeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            beanPostProcessor.setBeanFactory(this);
            result = beanPostProcessor.postProcessorBeforeInitialization(result, beanName);
            if (result == null){
                return result;
            }
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (AutowiredAnnotationBeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            result =  beanPostProcessor.postProcessorAfterInitialization(result, beanName);
        }
        return result;
    }
}
