package com.mini.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {

    public static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";

    WebApplicationContext context;
    public ContextLoaderListener() {
    }

    public ContextLoaderListener(WebApplicationContext webApplicationContext) {
        this.context = webApplicationContext;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        initWebApplicationContext(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
    private void initWebApplicationContext(ServletContext servletContext){
        String sContextLocation = servletContext.getInitParameter(CONFIG_LOCATION_PARAM);

        WebApplicationContext wac = new AnnotationConfigWebApplicationContext(sContextLocation);

        wac.setServletContext(servletContext);
        this.context = wac;
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.context);
    }
}
