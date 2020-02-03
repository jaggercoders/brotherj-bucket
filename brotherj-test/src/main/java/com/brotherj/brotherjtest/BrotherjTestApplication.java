package com.brotherj.brotherjtest;

import com.brotherj.brotherjclient.EnableFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableFeignClients
@EnableNeo4jRepositories
@EnableTransactionManagement
public class BrotherjTestApplication {



    public static void main(String[] args) {

        SpringApplication.run(BrotherjTestApplication.class, args);
    }

//    @Value("${test.version}")
//    private String version;
//
//    @Bean
//    public VersionApplicationListener versionApplicationListener(){
//
//        return new VersionApplicationListener(version);
//    }
}

