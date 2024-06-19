package com.ljdll.nettyServer.common.utils;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    @Getter
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (Objects.isNull(ApplicationContextUtil.applicationContext)) {
            ApplicationContextUtil.applicationContext = applicationContext;
        }
    }

}
