package com.brotherj.brotherjtest;

import com.brotherj.brotherjclient.EnableFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

@SpringBootApplication
@EnableFeignClients
public class BrotherjTestApplication {


    public static void main(String[] args) {
        SpringApplication.run(BrotherjTestApplication.class, args);
    }


}

