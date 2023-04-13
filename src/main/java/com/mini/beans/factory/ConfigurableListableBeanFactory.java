package com.mini.beans.factory;

import com.mini.beans.factory.support.AutowiredCapableBeanFactory;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory
        , ConfigurableBeanFactory, AutowiredCapableBeanFactory {
}
