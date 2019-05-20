package com.brotherj.brotherjutil.controller;

import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description：
 *
 * @author wangjie
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface ProxyMapping {


    String path() default "";

    RequestMethod method();
}
