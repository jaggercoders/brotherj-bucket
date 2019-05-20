package com.brotherj.brotherjserver;

import com.brotherj.brotherjutil.scheduling.EnableDistributeScheduling;
import com.brotherj.brotherjutil.controller.EnableAutoController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableScheduling
@EnableDistributeScheduling
@EnableAutoController
public class BrotherjServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrotherjServerApplication.class, args);
    }

}

