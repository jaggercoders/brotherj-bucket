package com.brotherj.brotherjserver.scheduling;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.config.TaskManagementConfigUtils;

/**
 * description：
 *
 * @author wangjie
 */
@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class DistributeSchedulingConfiguration {

    @Bean(name = TaskManagementConfigUtils.SCHEDULED_ANNOTATION_PROCESSOR_BEAN_NAME)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public DistributeScheduledAnnotationBeanPostProcessor scheduledAnnotationProcessor() {
        return new DistributeScheduledAnnotationBeanPostProcessor();
    }


//    @Bean
//    public TaskScheduler taskScheduler(){
//        return new ConcurrentTaskScheduler();
//    }
}
