package com.brotherj.brotherjclient;

import feign.Contract;
import feign.Feign;
import feign.Logger;
import feign.Retryer;
import feign.codec.Decoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * description：
 *
 * @author brotherJ  Wang
 */
@Configuration
public class FeignClientsConfiguration {

    @Autowired(required = false)
    private Logger logger;

    @Bean
    @ConditionalOnMissingBean(FeignLoggerFactory.class)
    public FeignLoggerFactory feignLoggerFactory() {
        return new DefaultFeignLoggerFactory(logger);
    }

    @Bean
    @ConditionalOnMissingBean
    public Retryer feignRetryer() {
        return Retryer.NEVER_RETRY;
    }

    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    public Feign.Builder feignBuilder(Retryer retryer) {
        return Feign.builder().retryer(retryer);
    }

    @Bean
    @ConditionalOnMissingBean
    public Contract feignContract(){
        return new Contract.Default();
    }


    @Bean
    @ConditionalOnMissingBean
    public Targeter feignTargeter(){
        return new DefaultTargeter();
    }
}
