package com.brotherj.brotherjutil.filters;


import com.brotherj.brotherjutil.util.AESUtil;
import com.brotherj.brotherjutil.util.RSAUtil;
import com.brotherj.brotherjutil.wrapper.BodyCachingHttpServletRequestWrapper;
import com.brotherj.brotherjutil.wrapper.BodyCachingHttpServletResponseWrapper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SafeFilter implements Filter {

    /**
     * 仓海帮的公钥（用于校验请求的签名)
     */
    private static final String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAupeDIVJUELbMozgNYpRqSaxhYCfAZnF7WxMUo2BwQ2LdYTXnl59e78xVLMjl7wfBUvTqFEqER210JrGuSp/aQyvHqAdwIx56oWaIBu9EHpx1RemLfBfEN4252inCj/LyUp09rlAX2Vq79GiRd/Z0r7A06cbqtz0euHpKZkKfZqD0niFFS9a1YGVER1vxNJE7vBH4yv78YE7ZlPWKQsQjMboS/j2itQmC6QGG/w5xqBUiwH9jFuvh4vuMfnDpJb0IVHrMzDOPKuiauLjIBMCU9eKdCmmvX2XI5v4IfGp6h7rYqEU9/ff/EenyZDYTHYCJUjBNuBKZhRBTz03giedooQIDAQAB";

    /**
     * 磐升的私钥（用于对响应参数进行加签）
     */
    // private static final String panshengPrivateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCrMwhy2suYztQTF5ZcdoVvJaMFMrRf0/zJK8TV+IDD5yOcdR7gHTdECBLo/kVcnSIbHlIZz/qRUrO2QZa0d8EPCZls3Pz/VfWzEgsXJe+Zi71JE4U0osiReVJCMWAmnN9vGSydcYWBhtM+uJscLJJ12ySuNjOfL0PIdHWFNrGxuCs78iMHTOaRRBMhgpuJiDV8Bu3qmPwodd18wTgfowgUw45FLQDaZ3DXRWU1sRar9VptP6K59NUDQhRi0Y1yxLXzQVeje4tF+eeyBpwoL/Fb+Tibp/RupqbWeCGgHSGo7VtSWHEvxFAHQXWLhcU+KeFvLxNEg1iI+KiWzvc6r4Y5AgMBAAECggEAOyznpbOlHkuXmIXVrl41XybWQ4y0Nk9zGCIt5nwAHjb94y3En1jZ6oUT7D8/wHR7/n2riUqPZL3+amgfbH/cVpNN2ghE1wliZSQ+eyDcwelAY0sBByGhTE9wNHsWriIZRTChcEZ2DECLMinjS4jIkz5NXn5Tt5lBx78hhAtUvoXGiSQhBPsdIHqcG4qNWmyt20RXIiGQbVlDO06f3FKfK8N9yQ43Hc25JMo7dAbKolaZKbWbOnHz/F0QAr6egeivmaI5dJxSfUh8Vmcgy8xwLu2twZkF1/nCzbCpdipfFQsHLln1dgHj2DdpJKRMuXu/0JugA73sDeYxsrUD6CS7VQKBgQDS1KxJidpOxzsSUvsZtDymBW9IyIuqtpOs/w1wmPwDkIOH+c00QovWqPiHDbMzXVbIZQVjkQJsBxCRji2jeZEmvFK0aRT1BY9zb165qbezxWe23kS0gKuzqBddWkAXUQEc510appkqPQszTKrq8CK7K6EiSKh8p+GLYbISo8SGSwKBgQDP4LdpKzh1sAX9j8pXkWYXgSUsY3cZkA9/5W9lQ9X6jl5FXruSdgNqgfq62WQY7bYX42C3Pjl6DqiYlkwXcUKj8ruoeT9qsOTwG8lg4mx84Wp/4iEoKezFDpVNkeL+QhDJ2xXf7tPn9hcwPhe876YDvEnkMsRQ23LE6hbXCbSjCwKBgHImrFH0zbqxrI4zV7mHGP9bGfHyg4ye5j5YqlI4wtq7ODw7SJvvG//R5R5akJhTBVXjPJO4Q/Nxr8rfjF5ix/CNLsp3yt0PcagyuLsV5Vz0mT8uAU0qDc1sSZpF70WwmkWoD4UKhwuTmajg7fneoy9sZAZICNHVpAzvjJcDDLkZAoGANJCiUFm5gSjtmtyNUe9WXYMunzbXmwMuqCygKn8o0J7Hf8sgqzxkSL5tZGosQZF6hjfc8PkvF4o1X8BBpXviWgq7ITLnslbgzUZ5eaJHxJxLjeLkd3+Ce6HgZykIuTqDfEeGFIYT14gG/Q/2eLQwyqkGFkowEjIrp1d6Rw4RO1kCgYA/so77gk+corYRQ+oFaBZcL3vILKUt01Qa7t4XaXY83ovC3waPTUGLTXSko4vXoVfqL6GEdl8oOW6l7r6ueU/oJVtsa2UsaHOPExRS5hABuWdPMI1vCosTetWBLDmsUJux0Sr3GDtPpTSbfkD/1Mc01KO4snK262MKOxKz7EdVvQ==";
    private static final String panshengPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDpdkAHvBg4zqgcDam8Xq9URaYXbJs9Q4Nr5DQCOeeifDujKeYQIN62QusINlXdCWFzLZvd6fDPLroqAQYgIVYoFZ7X3824PZcE4ijDIoXEcGm15LL5fjfVPTLdNb/Ujn4o3UYtuLt1yE/sBRLydrGkGA4jMs1goxPY0OXll3xSwDm8wP3CUSfGZfyffpgWxAeFup8p8SEJPyUBgZz3skSovtb1/wHa5bGwFK/lUIv6bb2XzpDwY4kXWfymbV5RpVYeQYSyMPkSkzim2WCJNm3l/EudgZJSPk+V0FR5ADFupT3H7ZAXwAzIKlRrob8Vsj8BKC5qMYsPPeXw2H4cDEBjAgMBAAECggEAIuKxpLY9pFd1FWQRRf2cE2qo6/0loVzySW/7OY3vh/8qZjI/l49h2DhdqId+Ax6AulZJYTqw6jSgSuCGSszQdALvDnD565sAcxFcZ/RDqtvlX/HBsLhknW+cpyN9+8QjI/YUo4ZkNLBvCOSlNSf5WXzit+3CdLtJnu9wOwOM50xbAS1skJSgDQ7zy0RH9naX5a88vmaCNWx2pjzQWp4q7jq1fq4UthdqY1l4Zp8ZUGt4J4th4ibFJbiHRy13Rq44oGBUApgl4K3cW/5xpCz5ORxo6wXUVyHwsZL2xQlMnz6vFQ3dwqgHCs8vATFawCf/P/Ml3lD0cBqZk07v7+UzqQKBgQD5BJVEx2cTGfjGhYiHAAaIV8mbT8J8gXXDg6JwyV590hLpSbT+yRmTTLkOds14wxXsgXDPZfhQbwaUY163bHl24fwdaF+hX3XHQx+fPye/3Bg1mYRHnjCqwpaaHobYwdwm7jKLzrsyCPECpMRgi5HZyZMJA4eoRsI8TuHMGa6V7wKBgQDwAgIc1s77Z0HSKH5i/uKONA4SoaiTOg0wYV2nbNEpTUHULNy7XCOtzfBhThwvb15+bcSi46x2NP/pnatHFYur040L0pV+QyjQNQruyOd1LfL8VWz1CjUEVNDkn6RAvdkAKreSKmMZ4c19/2y6iv7sk8Wd0i/JgM2Y553IcVTQzQKBgHef3wFCdV60vFDJ9zM7LOg0m/ITV8+t9V+uDcv2wIDa+SC+uscm3jVnxwKOydg4h4CtEf9f8fDZ1SRVtDuTTapxl0adTEtea1sFANzkXvkJB0xAak5pTOHLRsPU5FyejF7bUMbe0giQSQ9HDdt/UNp5vju3SWTqUQbdshT1RWxbAoGBAM1+2rI4lfYZffjj5Pr8A50AIwB+26hbLwm6hAajHB9B39g8JS5igMYCqwuVZnqS35Rehdt2FqcLUAaFki4F25XeR0jVIFyAQkSq9dZnvA3Prtmwht7zw1sXB9iIe4lXQ6Jp8M08b6esd1anSUAbXJPRHoW3fmUIXIDL54PEzXsNAoGAez3PIBAriXu8vtV7UblwLA43mSwyUMwK47gZM8nHme1XzXihCtROlg21zYWZ3zNrmu77dPWwsNES1/qf1SE4kQpTDzgP9/nm+WWBzXNsNI1L4F6ibIWCz7TYhU418RJlYm5gMndA/7shks+FS7O8kET+kB3jUDnS6rN/8uNcHc4=";



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // 1 封装servletRequest，防止请求body被读取后无法传入到servlet业务逻辑中
        BodyCachingHttpServletRequestWrapper requestWrapper =
            new BodyCachingHttpServletRequestWrapper((HttpServletRequest) servletRequest, AESUtil.AES_KEY);

        // 2 从包装的servletRequest中取出body
        byte[] requestEncryptionBody = requestWrapper.getRequestEncryptionBody();

        // 3 从header中取出签名sign的值
        String sign = requestWrapper.getHeader("sign");

        // 4 调用RSA工具类，使用Requestbody，sign和仓海帮公钥进行验签
        boolean checkResult = RSAUtil.rsaCheck(new String(requestEncryptionBody), sign, publicKey);

        if (!checkResult) {
            throw new RuntimeException("sign check failed!");
        }

        // 5 封装servletResponse
        BodyCachingHttpServletResponseWrapper responseWrapper =
            new BodyCachingHttpServletResponseWrapper((HttpServletResponse) servletResponse);

        // 6 调用业务
        filterChain.doFilter(requestWrapper, responseWrapper);

        // 7 获取业务执行后的responseBody
        byte[] responseBody = responseWrapper.getBody();

        // 8 调用AES加密
        String responseEncryptionBody = AESUtil.encrypt(new String(responseBody, "utf-8"), AESUtil.AES_KEY);

        // 8 调用RSA工具类，使用responseBody和磐升私钥进行加签
        String responseSign = RSAUtil.rsaSign(responseEncryptionBody, panshengPrivateKey);

        // 9 将加签后的签名放到response的header中
        ((HttpServletResponse) servletResponse).addHeader("sign", responseSign);

        // 10 重写response的返回流
        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.getWriter().print(responseEncryptionBody);
    }

}
