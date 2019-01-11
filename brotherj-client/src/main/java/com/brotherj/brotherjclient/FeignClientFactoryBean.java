package com.brotherj.brotherjclient;

import feign.Contract;
import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.Target;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * description：
 *
 * @author brotherJ  Wang
 */
class FeignClientFactoryBean implements FactoryBean<Object>, InitializingBean,
        ApplicationContextAware {

    private Class<?> type;

    private String name;

    private String url;

    private String path;

    private ApplicationContext applicationContext;

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    protected Feign.Builder feign(FeignContext context) {
        FeignLoggerFactory loggerFactory = get(context, FeignLoggerFactory.class);
        Logger logger = loggerFactory.create(this.type);

        Feign.Builder builder = get(context, Feign.Builder.class)
                // required values
                .logger(logger)
//                .encoder(get(context, Encoder.class))
//                .decoder(get(context, Decoder.class))
                .contract(get(context, Contract.class));
        configureFeign(context, builder);

        return builder;
    }

    /***
     *  加载FeignClientProperties，设置基础属性
     * @param context
     * @param builder
     */
    protected void configureFeign(FeignContext context, Feign.Builder builder) {
        FeignClientProperties properties = applicationContext.getBean(FeignClientProperties.class);
        //.properties是否设置了值
        if (properties != null) {
            configureUsingConfiguration(context, builder);
            configureUsingProperties(properties.getConfig().get(properties.getDefaultConfig()), builder);
            configureUsingProperties(properties.getConfig().get(this.name), builder);
        } else {
            configureUsingConfiguration(context, builder);
        }
    }

    protected void configureUsingConfiguration(FeignContext context, Feign.Builder builder) {
        Logger.Level level = getOptional(context, Logger.Level.class);
        if (level != null) {
            builder.logLevel(level);
        }
        Request.Options options = getOptional(context, Request.Options.class);
        if (options != null) {
            builder.options(options);
        }
    }

    protected void configureUsingProperties(FeignClientProperties.FeignClientConfiguration config, Feign.Builder builder) {
        if (config == null) {
            return;
        }

        if (config.getLoggerLevel() != null) {
            builder.logLevel(config.getLoggerLevel());
        }

        if (config.getConnectTimeout() != null && config.getReadTimeout() != null) {
            builder.options(new Request.Options(config.getConnectTimeout(), config.getReadTimeout()));
        }
    }


    <T> T getTarget() {
        FeignContext context = applicationContext.getBean(FeignContext.class);
        Feign.Builder builder = feign(context);

        if (StringUtils.hasText(this.url) && !this.url.startsWith("http")) {
            this.url = "http://" + this.url;
        }
        String url = this.url + cleanPath();
        Targeter targeter = get(context, Targeter.class);
        return (T) targeter.target(this, builder, context, new Target.HardCodedTarget<>(
                this.type, this.name, url));
    }
    @Override
    public Object getObject() throws Exception {
        return getTarget();
    }

    private String cleanPath() {
        String path = this.path.trim();
        if (StringUtils.hasLength(path)) {
            if (!path.startsWith("/")) {
                path = String.format("/%s", path);
            }
            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }
        }
        return path;
    }


    protected <T> T getOptional(FeignContext context, Class<T> type) {
        return context.getInstance(this.name, type);
    }

    protected <T> T get(FeignContext context, Class<T> type) {
        T instance = context.getInstance(this.name, type);
        if (instance == null) {
            throw new IllegalStateException("No bean found of type " + type + " for "
                    + this.name);
        }
        return instance;
    }

    @Override
    public Class<?> getObjectType() {
        return this.type;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasText(this.name, "Name must be set");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }
}
