//package com.brotherj.brotherjserver.validator;
//
//import com.brotherj.brotherjserver.GenerateController.BillDetailVo;
//import org.springframework.validation.Errors;
//import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
//
//import javax.validation.Validator;
//
///**
// * description：
// *
// * @author wangjie
// */
//public class ResultCodeValidator extends SpringValidatorAdapter {
//    /**
//     * Create a new SpringValidatorAdapter for the given JSR-303 Validator.
//     *
//     * @param targetValidator the JSR-303 Validator to wrap
//     */
//    public ResultCodeValidator(Validator targetValidator) {
//        super(targetValidator);
//    }
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return BillDetailVo.class.isAssignableFrom(clazz);
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//        BillDetailVo test = (BillDetailVo) target;
//        errors.rejectValue("billNo", "weird");
//        System.out.println(test.getBillNo());
//    }
//}
