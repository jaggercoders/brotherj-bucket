package com.brotherj.brotherjserver.scheduling;

import org.springframework.scheduling.annotation.EnableScheduling;

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
public @interface DistributeScheduled {

    /**
     * cron 表达式
     */
    String cron() default "";

    /**
     * 时区
     */
    String zone() default "";

    long fixedDelay() default -1;

    /**
     * Execute the annotated method with a fixed period in milliseconds between the
     * end of the last invocation and the start of the next.
     * @return the delay in milliseconds as a String value, e.g. a placeholder
     * or a {@link java.time.Duration#parse java.time.Duration} compliant value
     * @since 3.2.2
     */
    String fixedDelayString() default "";

    /**
     * Execute the annotated method with a fixed period in milliseconds between
     * invocations.
     * @return the period in milliseconds
     */
    long fixedRate() default -1;

    /**
     * Execute the annotated method with a fixed period in milliseconds between
     * invocations.
     * @return the period in milliseconds as a String value, e.g. a placeholder
     * or a {@link java.time.Duration#parse java.time.Duration} compliant value
     * @since 3.2.2
     */
    String fixedRateString() default "";

    /**
     * Number of milliseconds to delay before the first execution of a
     * {@link #fixedRate()} or {@link #fixedDelay()} task.
     * @return the initial delay in milliseconds
     * @since 3.2
     */
    long initialDelay() default -1;

    /**
     * Number of milliseconds to delay before the first execution of a
     * {@link #fixedRate()} or {@link #fixedDelay()} task.
     * @return the initial delay in milliseconds as a String value, e.g. a placeholder
     * or a {@link java.time.Duration#parse java.time.Duration} compliant value
     * @since 3.2.2
     */
    String initialDelayString() default "";
}
