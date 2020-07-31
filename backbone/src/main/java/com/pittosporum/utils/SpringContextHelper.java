package com.pittosporum.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Component
public class SpringContextHelper implements ApplicationContextAware {
    private static ApplicationContext context;

    public SpringContextHelper() {
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public synchronized void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
