package com.brotherj.brotherjtest;

import com.brotherj.brotherjtest.neo4j.dao.TestBillRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BrotherjTestApplicationTests {

    @Autowired
    private TestBillRepository testBillRepository;



    @Test
    public void contextLoads() {
        //        List<BillRelation> all = testBillRepository.findAll();
//        System.out.println(all);
    }



}

