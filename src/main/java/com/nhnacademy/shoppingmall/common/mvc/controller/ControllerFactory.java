package com.nhnacademy.shoppingmall.common.mvc.controller;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.exception.ControllerNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ControllerFactory {
    public static final String CONTEXT_CONTROLLER_FACTORY_NAME = "CONTEXT_CONTROLLER_FACTORY";
    private final ConcurrentMap<String, Object> beanMap = new ConcurrentHashMap<>();

    public void initialize(Set<Class<?>> c, ServletContext ctx) {

        if (Objects.isNull(c)) {
            log.info("Controller not found");
            return;
        }

        try {
            for (Class clazz : c) {
                RequestMapping classReQuestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
                RequestMapping.Method method = classReQuestMapping.method();
                String key = method + "-" + classReQuestMapping.value()[0];
                Object value = clazz.getDeclaredConstructor().newInstance();
                beanMap.put(key, value);
                ctx.setAttribute(CONTEXT_CONTROLLER_FACTORY_NAME, value);
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {

        }
    }

    private Object getBean(String key) {
        return beanMap.get(key);
    }

    public Object getController(HttpServletRequest request) {
        String key = request.getMethod() + "-" + request.getServletPath();
        return beanMap.get(key);
    }

    public Object getController(String method, String path) {
        String key = method + "-" + path;
        Object object = beanMap.get(key);
        if (Objects.isNull(object)) {
            throw new ControllerNotFoundException("controller not found exception!");
        }
        return object;
    }

    private String getKey(String method, String path) {
        return method + "-" + path;
    }
}
