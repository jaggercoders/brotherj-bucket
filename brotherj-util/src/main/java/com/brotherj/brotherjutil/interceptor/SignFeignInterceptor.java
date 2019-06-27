//package com.brotherj.brotherjutil.interceptor;
//
//import com.sinochem.energy.technology.dataexchange.wms.api.ex.SignIllegalException;
//import com.sinochem.energy.technology.dataexchange.wms.api.safe.RSAUtil;
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.UnsupportedEncodingException;
//
///**
// * description：
// *
// * @author wangjie
// */
//@Slf4j
//public class SignFeignInterceptor implements RequestInterceptor {
//
//
//    private String privateKey;
//
//    private static final String SIGN="sign";
//
//    public SignFeignInterceptor(String privateKey) {
//        this.privateKey=privateKey;
//    }
//
//    @Override
//    public void apply(RequestTemplate template) {
//        try {
//            log.info("signFeignInterceptor, requestBody:" + new String(template.body(), "utf-8"));
//            String sign = RSAUtil.rsaSign(new String(template.body(),"utf-8"),privateKey);
//            log.info("signFeignInterceptor, sign:" + sign);
//            template.header(SIGN,sign);
//        } catch (UnsupportedEncodingException e) {
//            throw new SignIllegalException("add sign failed");
//        }
//
//    }
//}
