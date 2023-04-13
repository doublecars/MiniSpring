package com.mini.core.env;

public interface Environment extends PropertyResolver {

    String[] getActiveProfiles();

    String[] getDefaultProfiles();

    boolean acceptProfiles(String... profiles);
}
