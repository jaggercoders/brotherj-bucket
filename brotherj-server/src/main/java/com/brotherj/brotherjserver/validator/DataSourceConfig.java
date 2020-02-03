//package com.brotherj.brotherjserver.validator;
//
//import com.codahale.metrics.MetricRegistry;
//import com.codahale.metrics.health.HealthCheckRegistry;
//import com.zaxxer.hikari.HikariDataSource;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
///**
// * description： hikariCP 心跳检测
// * date： 2018/10/17 14:44
// *
// * @author wangj
// */
//@Slf4j
//@Configuration
//public class DataSourceConfig {
//
//	/**
//	 * 任总orderbank + WMS数据源， sqlserver
//	 * @return
//	 */
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
//
//
//}
