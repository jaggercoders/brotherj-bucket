package com.brotherj.brotherjclient;

import com.brotherj.brotherjclient.base.AbstractRegistrar;

import java.lang.annotation.Annotation;

/**
 * description：
 *
 * @author brotherJ  Wang
 */
class FeignClientsRegistrar  extends AbstractRegistrar {

    @Override
    protected Class<? extends Annotation> getEnableAnnotationClass() {
        return EnableFeignClients.class;
    }

    @Override
    protected Class<? extends Annotation> getAnnotationClass() {
        return FeignClient.class;
    }

    @Override
    protected Class<?> getBeanClass() {
        return FeignClientFactoryBean.class;
    }
}
