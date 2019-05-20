package com.brotherj.brotherjserver;

import com.brotherj.brotherjserver.GenerateController.EnableAutoController;
import com.brotherj.brotherjserver.scheduling.EnableDistributeScheduling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoController
//@EnableScheduling
@EnableDistributeScheduling
public class BrotherjServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrotherjServerApplication.class, args);
    }

}

