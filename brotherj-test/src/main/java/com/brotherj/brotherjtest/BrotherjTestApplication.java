package com.brotherj.brotherjtest;

import com.brotherj.brotherjclient.EnableFeignClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableFeignClients
public class BrotherjTestApplication {


    public static void main(String[] args) {
        SpringApplication.run(BrotherjTestApplication.class, args);
    }


}

