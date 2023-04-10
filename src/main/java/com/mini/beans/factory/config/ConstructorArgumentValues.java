package com.mini.beans.factory.config;

import java.util.*;

public class ConstructorArgumentValues {

   /* private final Map<Integer, ArgumentValue> indexedArgumentValues = new HashMap<>(0);
    private final List<ArgumentValue> genericArgumentValues = new LinkedList<>();

    public ArgumentValues() {
    }

    private void addArgumentValue(Integer key, ArgumentValue newValue){
        this.indexedArgumentValues.put(key, newValue);
    }

    public Boolean hasIndexedArgumentValue(Integer index){
        return this.indexedArgumentValues.containsKey(index);
    }

    public ArgumentValue getIndexedArgumentValue(Integer index){
        return this.indexedArgumentValues.get(index);
    }


    private void addGenericArgumentValue(ArgumentValue newValue){
        if (newValue.getName() != null){
            for (Iterator<ArgumentValue> it = this.genericArgumentValues.iterator(); it.hasNext();){
                ArgumentValue currentValue = it.next();
                if (newValue.getName().equals(currentValue.getName())){
                    it.remove();
                }

            }
        }
        this.genericArgumentValues.add(newValue);
    }

    public void addGenericArgumentValue(Object value, String type){
        this.genericArgumentValues.add(new ArgumentValue(value, type));
    }


    public ArgumentValue getGenericArgumentValue(String requiredName){
        for (ArgumentValue valueHold : this.genericArgumentValues){
            if (valueHold.getName() != null && !valueHold.getName().equals(requiredName)){
                continue;
            }
            return valueHold;
        }
        return null;
    }

    public int getArgumentCount(){
        return this.genericArgumentValues.size();
    }

    public boolean isEmpty(){
        return this.genericArgumentValues.isEmpty();
    }

    public void add(ArgumentValue argumentValue){
        this.genericArgumentValues.add(argumentValue);
    }*/

    private final List<ConstructorArgumentValue> constructorArgumentValueList = new ArrayList<>();

    public ConstructorArgumentValues(){}

    public void addArgumentValue(ConstructorArgumentValue constructorArgumentValue){
        this.constructorArgumentValueList.add(constructorArgumentValue);
    }

    public ConstructorArgumentValue getIndexedArgumentValue(int index){
        return this.constructorArgumentValueList.get(index);
    }

    public int getArgumentCount(){
        return this.constructorArgumentValueList.size();
    }

    public boolean isEmpty(){
        return this.constructorArgumentValueList.isEmpty();
    }
}
