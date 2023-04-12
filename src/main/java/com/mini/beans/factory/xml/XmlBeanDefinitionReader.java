package com.mini.beans.factory.xml;

import com.mini.beans.*;
import com.mini.beans.factory.BeanFactory;
import com.mini.beans.factory.config.ConstructorArgumentValue;
import com.mini.beans.factory.config.ConstructorArgumentValues;
import com.mini.beans.factory.config.BeanDefinition;
import com.mini.beans.factory.support.AutowireCapableBeanFactory;
import com.mini.beans.factory.support.SimpleBeanFactory;
import com.mini.core.Resource;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class XmlBeanDefinitionReader {

    AutowireCapableBeanFactory beanFactory;

    public XmlBeanDefinitionReader(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanId, beanClassName);

            //handle argument parameters
            List<Element> constructorElements = element.elements("constructor-arg");
            ConstructorArgumentValues ARV = new ConstructorArgumentValues();
            for (Element constructorElement : constructorElements) {
                String aType = constructorElement.attributeValue("type");
                String aName = constructorElement.attributeValue("name");
                String aValue = constructorElement.attributeValue("value");
                ARV.addArgumentValue(new ConstructorArgumentValue(aName, aType, aValue));
            }
            beanDefinition.setConstructArgumentValues(ARV);

            //handle property
            List<Element> properties = element.elements("property");
            PropertyValues PVS = new PropertyValues();
            List<String> refs = new ArrayList<>();
            for (Element property : properties) {
                String pType = property.attributeValue("type");
                String pName = property.attributeValue("name");
                String pValue = property.attributeValue("value");
                String pRef = property.attributeValue("ref");
                String pV = "";
                boolean isRef = false;
                if (pValue != null && !pValue.equals("")) {
                    pV = pValue;
                } else if (pRef != null && !pRef.equals("")) {
                    isRef = true;
                    pV = pRef;
                    refs.add(pRef);
                }
                PVS.addPropertyValue(new PropertyValue(pType, pName, pV, isRef));
            }
            beanDefinition.setPropertyValues(PVS);
            String[] refArray = refs.toArray(new String[0]);
            beanDefinition.setDependsOn(refArray);
            this.beanFactory.registerBeanDefinition(beanId, beanDefinition);
        }
    }

}
