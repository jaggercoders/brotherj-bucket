package com.brotherj.brotherjclient;

import feign.Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.actuator.HasFeatures;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * description：
 *
 * @author brotherJ  Wang
 */
@Configuration
@ConditionalOnClass(Feign.class)
@EnableConfigurationProperties({FeignClientProperties.class})
public class FeignAutoConfiguration {


    @Bean
    public FeignContext feignContext(){
        return new FeignContext();
    }



}
