package com.brotherj.brotherjutil.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;

/**
 * description：
 *
 * @author wangjie
 */
class AutoControllerProxyRegistrar implements ImportBeanDefinitionRegistrar,
        ResourceLoaderAware, BeanFactoryAware, EnvironmentAware {


    private ResourceLoader resourceLoader;

    private Environment environment;

    private BeanFactory beanFactory;

    private static final String DEFAULT_NAME="Controller";

    private static final String PACKAGE_SPLIT=".";

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory=beanFactory;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {

        registerAnnotations(metadata);
    }



    public void registerAnnotations(AnnotationMetadata metadata) {
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        scanner.setResourceLoader(this.resourceLoader);

        // 扫描带有自定义注解的类
        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(
                ControllerProxy.class);
        scanner.addIncludeFilter(annotationTypeFilter);
        // 确定扫描的包路径列表
        String basePackage = ClassUtils.getPackageName(metadata.getClassName());
        Set<BeanDefinition> candidateComponents = scanner
                .findCandidateComponents(basePackage);
        for (BeanDefinition candidateComponent : candidateComponents) {
            if (candidateComponent instanceof AnnotatedBeanDefinition) {
                AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                Map<String, Object> attributes = annotationMetadata
                        .getAnnotationAttributes(
                                ControllerProxy.class.getCanonicalName());
                registerBeans(annotationMetadata,attributes);
            }
        }
    }


    /**
     * 创建动态代理，并动态注册到容器中
     *
     */
    private void registerBeans(AnnotationMetadata metadata, Map<String, Object> attributes)  {
        try {
            Class<?> target = Class.forName(metadata.getClassName());
            String className= target.getSimpleName()+DEFAULT_NAME;
            if (StringUtils.isEmpty(attributes.get("value"))){
                attributes.put("value",target.getSimpleName().toLowerCase());
            }
            String path=target.getPackage().getName();
            Class<?> aClass = ControllerBuilder.builder()
                                               .init(target)
                                               .makeClass(path.replaceAll("service", "com/brotherj/brotherjutil/controller") + PACKAGE_SPLIT + className)
                                               .addControllerAnnotation(attributes)
                                               .autowiredFiled(target)
                                               .addMethod()
                                               .toClass();

            AbstractBeanDefinition definition=new RootBeanDefinition(aClass,AbstractBeanDefinition.AUTOWIRE_BY_TYPE,true);
            ((DefaultListableBeanFactory) this.beanFactory).registerBeanDefinition(className,definition);
        } catch (Exception e) {
            throw new RuntimeException("Initialization of controller failed",e);
        }
    }





    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                boolean isCandidate = false;
                if (beanDefinition.getMetadata().isIndependent()) {
                    if (!beanDefinition.getMetadata().isAnnotation()) {
                        isCandidate = true;
                    }
                }
                return isCandidate;
            }
        };
    }


}
