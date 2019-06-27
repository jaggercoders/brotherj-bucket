package com.brotherj.brotherjserver.validator;

import com.brotherj.brotherjserver.GenerateController.BillDetailVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * description：
 *
 * @author wangjie
 */
@RestController
public class LoginController {

//    @PostMapping("/test")
//    public BillDetailVo test(@Validated(ResultCodeValidator.class) @RequestBody BillDetailVo test) {
//        return test;
//    }

    @PostMapping("/test2")
    public BillDetailVo test2(@Validated @RequestBody BillDetailVo test) {
        return test;
    }
}