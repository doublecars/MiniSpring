package com.mini.web;

import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

public class XmlConfigReader {
    public XmlConfigReader() {
    }

    public Map<String, MappingValue> loadConfig(Resource resource){

        Map<String, MappingValue> result = new HashMap<>();
        while (resource.hasNext()){
            Element element = (Element) resource.next();
            String beanId = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            String beanMethod = element.attributeValue("value");
            result.put(beanId, new MappingValue(beanId,beanClassName, beanMethod));
        }
        return result;
    }
}
