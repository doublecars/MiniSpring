package com.mini.beans.factory.support;

import com.mini.beans.PropertyValue;
import com.mini.beans.PropertyValues;
import com.mini.beans.factory.ConfigurableBeanFactory;
import com.mini.beans.factory.config.BeanDefinition;
import com.mini.beans.factory.config.ConstructorArgumentValue;
import com.mini.beans.factory.config.ConstructorArgumentValues;
import com.mini.exception.BeansException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry
        implements ConfigurableBeanFactory, BeanDefinitionRegistry {

    protected Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    protected List<String> beanDefinitionNames = new ArrayList<>();
    private Map<String, Object> earlySingletonObjects = new HashMap<>(16);

    public AbstractBeanFactory() {
    }


    public void refresh() {
        for (String beanDefinitionName : beanDefinitionNames) {
            try {
                getBean(beanDefinitionName);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public Object getBean(String beanName) throws BeansException {
        Object singleton = this.getSingleton(beanName);
        //if no this bean, try to get from earlySingletonObjects
        if (singleton == null) {
            singleton = this.earlySingletonObjects.get(beanName);
            //if no this bean in early, then need to create this bean
            if (singleton == null) {
                System.out.println("get bean null -------------- " + beanName);
                BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
                singleton = createBean(beanDefinition);

                //registry bean
                this.registerSingleton(beanName, singleton);

                // bean post processor
                // 1. before
                applyBeanPostProcessorBeforeInitialization(singleton, beanName);
                //2. init-method
                if (beanDefinition.getInitMethodName() != null && !beanDefinition.getInitMethodName().equals("")) {
                    invokeInitMethod(beanDefinition, singleton);
                }
                // after
                applyBeanPostProcessorAfterInitialization(singleton, beanName);
            }
        }

        return singleton;
    }

    private void invokeInitMethod(BeanDefinition beanDefinition, Object singleton) {
        Class<?> clz = singleton.getClass();
        try {
            Method method = clz.getMethod(beanDefinition.getInitMethodName());
            try {
                method.invoke(singleton);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

    }

    private Object createBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        Object obj = doCreateBean(beanDefinition);
        this.earlySingletonObjects.put(beanDefinition.getId(), obj);
        try {
            clz = Class.forName(beanDefinition.getClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        populateBean(beanDefinition, clz, obj);

        return obj;
    }


    private Object doCreateBean(BeanDefinition beanDefinition) {
        Class<?> clz = null;
        Constructor<?> con = null;
        Object obj = null;
        ConstructorArgumentValues constructArgumentValues = beanDefinition.getConstructArgumentValues();
        try {
            clz = Class.forName(beanDefinition.getClassName());

            if (!constructArgumentValues.isEmpty()) {
                Class<?>[] paramTypes = new Class[constructArgumentValues.getArgumentCount()];
                Object[] paramValues = new Object[constructArgumentValues.getArgumentCount()];

                for (int i = 0; i < constructArgumentValues.getArgumentCount(); i++) {
                    ConstructorArgumentValue argumentValue = constructArgumentValues.getIndexedArgumentValue(i);
                    String aType = argumentValue.getType();
                    Object aValue = argumentValue.getValue();
                    if ("Integer".equals(aType) || "java.lang.Integer".equals(aType)) {
                        paramTypes[i] = Integer.class;
                        paramValues[i] = Integer.valueOf((String) aValue);
                    } else if ("int".equals(aType)) {
                        paramTypes[i] = int.class;
                        paramValues[i] = Integer.valueOf((String) aValue);
                    } else {
                        paramTypes[i] = String.class;
                        paramValues[i] = aValue;
                    }
                }
                try {
                    con = clz.getConstructor(paramTypes);
                    try {
                        obj = con.newInstance(paramValues);
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    obj = clz.newInstance();
                } catch (InstantiationException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (
                ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(beanDefinition.getId() + " bean created. " + beanDefinition.getClassName() + " : " + obj.toString());
        return obj;
    }


    private void populateBean(BeanDefinition beanDefinition, Class<?> clz, Object obj) {
        handleProperties(beanDefinition, clz, obj);
    }

    private void handleProperties(BeanDefinition beanDefinition, Class<?> clz, Object obj) {
        // handle properties
        System.out.println("handle properties for bean : " + beanDefinition.getId());
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValueList()) {
            String pType = propertyValue.getType();
            String pName = propertyValue.getName();
            Object pValue = propertyValue.getValue();
            boolean isRef = propertyValue.getIsRef();

            Class<?>[] paramTypes = new Class[1];
            Object[] paramValues = new Object[1];
            if (!isRef) {
                if ("Integer".equals(pType) || "java.lang.Integer".equals(pType)) {
                    paramTypes[0] = Integer.class;
                    paramValues[0] = Integer.valueOf((String) pValue);
                } else if ("int".equals(pType)) {
                    paramTypes[0] = int.class;
                    paramValues[0] = Integer.valueOf((String) pValue);
                } else {
                    paramTypes[0] = String.class;
                    paramValues[0] = pValue;
                }
            } else {
                try {
                    paramTypes[0] = Class.forName(pType);
                    try {
                        paramValues[0] = getBean((String) pValue);
                    } catch (BeansException e) {
                        throw new RuntimeException(e);
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

            Method method = null;
            try {
                method = obj.getClass().getMethod("set" + pName.substring(0, 1).toUpperCase() + pName.substring(1), paramTypes);
                try {
                    method.invoke(obj, paramValues);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

        }
    }

    abstract public Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    abstract public Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) throws BeansException;


    @Override
    public Boolean containsBean(String beanName) {
        return containsSingleton(beanName);
    }

    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitionMap.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitionMap.get(name).isPrototype();
    }

    @Override
    public Class<?> getType(String name) {
        return (this.beanDefinitionMap.get(name).getClass());
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition bd) {
        this.beanDefinitionMap.put(name, bd);
        this.beanDefinitionNames.add(name);
        if (!bd.isLazyInit()) {
            try {
                getBean(name);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }
}
