package com.brotherj.brotherjserver.GenerateController;

import com.brotherj.brotherjutil.controller.ControllerProxy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description：
 *
 * @author wangjie
 */
@Service
@ControllerProxy("/sds")
public class TestReflectService {


    public String  testController(List<BillDetailVo> vo){

        return "success";
    }


//    @ProxyMapping(path = "/ttt2",method = RequestMethod.GET)
//    public String  testController2(String userName){
//
//        return "success2";
//    }



//    public String  testController3(String userName,Integer userId){
//
//        return "success3";
//    }

}
