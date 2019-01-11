package com.brotherj.brotherjtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * description：
 *
 * @author brotherJ  Wang
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestClient testClient;


    @RequestMapping(value = "/tt",method = RequestMethod.GET)
    public String test(String name){
        return testClient.test();
    }
}
