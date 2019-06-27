package com.brotherj.brotherjserver.test;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.function.Function;

/**
 * description：
 *
 * @author wangjie
 */
public class ExecApiWrapper {

    public static <T,R> R execApi(Function<T,R> api,T param){
        return api.apply(param);
    }

//    @Bean
//    @ConfigurationProperties("datasource.orderbank")
//    public DataSource orderBankDataSource(){
//        HikariDataSource dataSource = DataSourceBuilder.create().type(HikariDataSource.class).build();
//        dataSource.setMetricRegistry(metricRegistry());
//        dataSource.setHealthCheckRegistry(healthCheckRegistry());
//        return dataSource;
//    }
//
//
//    @Bean
//    public HealthCheckRegistry healthCheckRegistry(){
//        return new HealthCheckRegistry();
//    }
//
//    @Bean
//    public MetricRegistry metricRegistry(){
//        return new MetricRegistry();
//    }

}
