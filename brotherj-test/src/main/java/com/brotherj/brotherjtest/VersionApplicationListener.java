package com.brotherj.brotherjtest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

/**
 * description：
 *
 * @author wangjie
 */
@Component
public class VersionApplicationListener implements GenericApplicationListener {

    @Value("${test.version}")
    private String version;

    public VersionApplicationListener(String version) {
        this.version = version;
    }

    public VersionApplicationListener() {
    }



    @Override
    public boolean supportsEventType(ResolvableType eventType) {
        Class<?> type = eventType.getRawClass();
        if (type == null) {
            return false;
        }
        return ApplicationStartedEvent.class.isAssignableFrom(type);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof  ApplicationStartedEvent){
            System.out.println("当前版本为："+version);
        }
    }
}
