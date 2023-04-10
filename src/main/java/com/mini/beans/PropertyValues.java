package com.mini.beans;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {
    private final List<PropertyValue> propertyValueList;

    public PropertyValues() {
        this.propertyValueList = new ArrayList<>(0);
    }

    public List<PropertyValue> getPropertyValueList(){
        return this.propertyValueList;
    }

    public int size(){
        return this.propertyValueList.size();
    }

    public void addPropertyValue(PropertyValue pv){
        this.propertyValueList.add(pv);
    }

    public void addPropertyValue(String name, Object value){
        addPropertyValue(new PropertyValue(null,name, value,false));
    }

    public void removePropertyValue(PropertyValue pv){
        this.propertyValueList.remove(pv);
    }

    public void removePropertyValue(String propertyName){
        this.propertyValueList.remove(getPropertyValue(propertyName));
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue propertyValue : this.propertyValueList) {
            if (propertyValue.getName().equals(propertyName)){
                return propertyValue;
            }
        }
        return null;
    }

    public PropertyValue[] getPropertyValues(){
        return this.propertyValueList.toArray(new PropertyValue[this.propertyValueList.size()]);
    }

    public Object get(String propertyName){
        PropertyValue pv = getPropertyValue(propertyName);
        return pv != null? pv.getValue() : null;
    }

    public boolean contains(String propertyName){
        return getPropertyValue(propertyName) != null;
    }

    public boolean isEmpty(){
        return this.propertyValueList.isEmpty();
    }

}
