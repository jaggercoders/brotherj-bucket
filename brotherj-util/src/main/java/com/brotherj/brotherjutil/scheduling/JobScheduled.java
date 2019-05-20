package com.brotherj.brotherjutil.scheduling;

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
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JobScheduled {
    /**
     * cron 表达式
     */
    String cron() default "";

    /**
     * 时区
     */
    String zone() default "";

    /**
     * 是否开启任务，默认开启
     */
    String enable() default "true";

    /**
     * 下次任务执行时间与上次执行任务之间固定间隔毫秒数
     */
    long fixedDelay() default -1;

    String fixedDelayString() default "";

    /**
     * 以固定速率执行任务
     */
    long fixedRate() default -1;

    String fixedRateString() default "";

    /**
     * 初始延迟时间，毫秒
     */
    long initialDelay() default -1;

    String initialDelayString() default "";
}

