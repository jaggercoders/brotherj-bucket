//package com.brotherj.brotherjutil.interceptor;
//
//import com.sinochem.energy.technology.dataexchange.wms.api.domain.log.LoggerDTO;
//import com.sinochem.energy.technology.dataexchange.wms.api.ex.SignIllegalException;
//import com.sinochem.energy.technology.dataexchange.wms.api.safe.RSAUtil;
//import feign.FeignException;
//import feign.Response;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.ObjectFactory;
//import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
//import org.springframework.cloud.openfeign.support.SpringDecoder;
//
//import java.io.BufferedReader;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.lang.reflect.Type;
//import java.nio.charset.StandardCharsets;
//import java.util.Collection;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * description：
// *
// * @author wangjie
// */
//@Slf4j
//public class SignDecoder extends SpringDecoder {
//
//
//    private static final String SIGN="sign";
//
//    private String publicKey;
//
//    private boolean signCheck;
//
//    public SignDecoder(ObjectFactory<HttpMessageConverters> messageConverters, String publicKey, boolean signCheck) {
//        super(messageConverters);
//        this.publicKey=publicKey;
//        this.signCheck=signCheck;
//    }
//
//    @Override
//    public Object decode(Response response, Type type) throws IOException, FeignException {
//        Object decode;
//        String bodyJson = new BufferedReader(new InputStreamReader(response.body().asInputStream(), "utf-8"))
//                .lines().parallel().collect(Collectors.joining());
//        LoggerDTO loggerDTO = LoggerDTO.builder()
//                                            .url(response.request().url())
//                                            .httpMethod(response.request().method())
//                                            .requestSign(response.request().headers().get(SIGN).stream().findFirst().orElse(null))
//                                            .requestParams(new String(response.request().body(),"utf-8"))
//                                            .responseResult(bodyJson)
//                                            .build();
//
//        try {
//            if (signCheck){
//                Map<String, Collection<String>> headers = response.headers();
//                String sign=headers.get(SIGN).stream().findFirst().orElseThrow(()->new SignIllegalException("sign is null"));
//                log.info("SignDecoder, responseSign:" + sign);
//                loggerDTO.setResponseSign(sign);
//                Integer length=response.body().length();
//                boolean checkResult = RSAUtil.rsaCheck(bodyJson, sign, publicKey);
//                log.info("SignDecoder, rsaCheck result:" + checkResult);
//                if (!checkResult) {
//                    throw new SignIllegalException("sign check failed!");
//                }
//                InputStream stream = new ByteArrayInputStream(bodyJson.getBytes(StandardCharsets.UTF_8));
//                Response newResponse=Response.builder()
//                                             .status(response.status())
//                                             .reason(response.reason())
//                                             .headers(headers)
//                                             .body(stream, length)
//                                             .request(response.request())
//                                             .build();
//
//                decode = super.decode(newResponse, type);
//            }else {
//                decode = super.decode(response,type);
//            }
//        }finally {
//            log.info(loggerDTO.toString());
//        }
//        return decode;
//    }
//}
