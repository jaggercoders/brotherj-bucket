package com.brotherj.brotherjutil.scheduling;

import org.springframework.context.annotation.Import;

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
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(DistributeSchedulingConfiguration.class)
@Documented
public @interface EnableDistributeScheduling {
}
