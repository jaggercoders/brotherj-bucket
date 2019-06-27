package com.brotherj.brotherjserver.validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description：
 *
 * @author wangjie
 */
@Configuration
public class ValidatorConfiguration {

    @Bean
    public javax.validation.Validator localValidatorFactoryBean() {
        return new CustomLocalValidatorFactoryBean();
    }
}
