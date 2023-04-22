package com.mini.web;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    private List<String> packageNames = new ArrayList<>();

    private Map<String, Object> controllerObjs = new HashMap<>();

    private List<String> controllerNames = new ArrayList<>();

    private Map<String, Class<?>> controllerClasses = new HashMap<>();

    private List<String> urlMappingNames = new ArrayList<>();

    private Map<String, Object> mappingObjs = new HashMap<>();

    private Map<String, Method> mappingMethods = new HashMap<>();

    private WebApplicationContext webApplicationContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.webApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        String configLocation = config.getInitParameter("contextConfigLocation");
        URL xmlPath = null;
        try {
            xmlPath = this.getServletContext().getResource(configLocation);
            this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        refresh();
        System.out.println("servlet initial...");
    }


    protected void refresh() {
        initController();
        initMapping();
    }

    private void initMapping() {
        for (String controllerName : this.controllerNames) {
            Class<?> clz = this.controllerClasses.get(controllerName);
            Object obj = this.controllerObjs.get(controllerName);
            Method[] methods = clz.getDeclaredMethods();
            for (Method method : methods) {
                boolean isRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                if (isRequestMapping){
                    String methodName = method.getName();
                    String urlMapping = method.getAnnotation(RequestMapping.class).value();
                    this.urlMappingNames.add(urlMapping);
                    this.mappingObjs.put(urlMapping, obj);
                    this.mappingMethods.put(urlMapping, method);
                }
            }
        }
    }

    private void initController() {
       scanPackage(this.packageNames);
    }

    private void scanPackage(List<String> packageNames) {
        for (String packageName : packageNames) {
            scanPackage(packageName);
        }
    }


    private void scanPackage(String packageName) {
        URI uri = null;
        try {
            uri = this.getClass().getResource("/" + packageName.replaceAll("\\.", "/")).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        File dir = new File(uri);
        for (File file : dir.listFiles()) {
            if (file.isDirectory()){
                scanPackage(packageName + "." + file.getName());
            }else {
                String controllerName = packageName + "." + file.getName().replace(".class", "");
                String controllerClassName = controllerName.replace("//", "\\.");
                try {
                    Class<?> clz = Class.forName(controllerClassName);
                    Object obj = clz.newInstance();
                    this.controllerClasses.put(controllerName, clz);
                    this.controllerObjs.put(controllerName,obj);
                    this.controllerNames.add(controllerName);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sPath = request.getServletPath();
        if (!this.urlMappingNames.contains(sPath)) {
            return;
        }
        Method method = null;
        Object objResult = null;
            method = this.mappingMethods.get(sPath);
            Object obj = this.mappingObjs.get(sPath);
        try {
            objResult = method.invoke(obj);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        response.getWriter().append(objResult.toString());
    }
}
