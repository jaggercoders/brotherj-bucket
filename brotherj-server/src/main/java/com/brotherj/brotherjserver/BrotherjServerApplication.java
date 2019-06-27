package com.brotherj.brotherjserver;

import com.brotherj.brotherjserver.GenerateController.BillDetailVo;
import com.brotherj.brotherjutil.controller.EnableAutoController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
//@EnableScheduling
//@EnableDistributeScheduling
@EnableAutoController
@RestController
@RequestMapping("/")
public class BrotherjServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(BrotherjServerApplication.class, args);
    }

//    @PostMapping("/test")
//    public String test(@RequestBody @Validated BillDetailVo vo){
//        return "success";
//    }

}

