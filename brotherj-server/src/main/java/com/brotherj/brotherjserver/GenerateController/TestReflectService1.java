package com.brotherj.brotherjserver.GenerateController;

import org.springframework.stereotype.Service;

/**
 * description：
 *
 * @author wangjie
 */
@Service
public class TestReflectService1 implements TestReflectInterface{


    @Override
    public String  testController(BillDetailVo vo){

        return "success22";
    }
}
