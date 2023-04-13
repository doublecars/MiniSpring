package com.mini.beans.factory.support;

import com.mini.exception.BeansException;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAutowiredCapableBeanFactory extends AbstractBeanFactory
        implements AutowiredCapableBeanFactory{

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }


    public int getBeanPostProcessorCount(){
        return this.beanPostProcessors.size();
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    @Override
    public Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            beanPostProcessor.setBeanFactory(this);
            result = beanPostProcessor.postProcessorBeforeInitialization(result, beanName);
            if (result == null){
                return null;
            }
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            result =  beanPostProcessor.postProcessorAfterInitialization(result, beanName);
            if (result == null) return null;
        }
        return result;
    }
}
