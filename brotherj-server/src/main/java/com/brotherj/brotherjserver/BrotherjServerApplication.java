package com.brotherj.brotherjserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/test")
public class BrotherjServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrotherjServerApplication.class, args);
    }

    @RequestMapping(value = "/tt",method = RequestMethod.GET)
    public String test(){
        return "success";
    }
}

