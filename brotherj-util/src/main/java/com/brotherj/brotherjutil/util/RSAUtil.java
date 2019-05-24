package com.brotherj.brotherjutil.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA签名的工具类
 *
 * 使用方法：
 * 1 直接使用RSAUtil.generateKeyPair() 生成一对RSA的公私钥对
 * 2 使用生成的私钥可以对内容进行加签
 */
public class RSAUtil {

    /**
     * 加密算法RSA
     */
    private static final String KEY_PAIR_GENERATOR_ALGORITHM = "RSA";

    /**
     * 加密Key的长度
     */
    private static final Integer KEY_PAIR_SIZE = 2048;

    /**
     * RSA加签算法
     */
    private static final String RSA_SIGN_ALGORITHMS = "SHA1WithRSA";

    /**
     * RSA加签字符串的字符集
     */
    private static final String RSA_SIGN_CHARSET = "utf-8";

    /**
     * RSA加签类型
     */
    private static final String SIGN_TYPE_RSA = "RSA";

    /**
     * 方法 1：生成RSA密钥对(公钥和私钥)
     *
     * @return
     * @throws Exception
     */
    public static final void generateKeyPair() {
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance(KEY_PAIR_GENERATOR_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("KeyPairGenerator.getInstance NoSuchAlgorithmException:", e);
        }

        keyPairGen.initialize(KEY_PAIR_SIZE);

        KeyPair keyPair = keyPairGen.generateKeyPair();

        if (keyPair == null) {
            throw new RuntimeException("keyPairGen.generateKeyPair failed!");
        }

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // if use jdk 1.8 use this
        // System.out.println("publicKey:" + Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        // else use this
        System.out.println("publicKey:" + Base64Utils.encode(publicKey.getEncoded()));

        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // if use jdk 1.8 use this
        // System.out.println("privateKey:" + Base64.getEncoder().encodeToString(privateKey.getEncoded()));
        // else use this
        System.out.println("privateKey:" + Base64Utils.encode(privateKey.getEncoded()));
    }

    /**
     * 私钥加签
     *
     * @param needSignContent 需要加签的字符串
     * @param privateKey 私钥
     * @return
     */
    public static String rsaSign(String needSignContent, String privateKey)  {
        try {

            PrivateKey priKey = getPrivateKeyFromPKCS8(SIGN_TYPE_RSA,
                new ByteArrayInputStream(privateKey.getBytes()));

            Signature signature = Signature.getInstance(RSA_SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update(needSignContent.getBytes(RSA_SIGN_CHARSET));

            byte[] signed = signature.sign();

            return Base64Utils.encode(signed);
        } catch (InvalidKeySpecException ie) {
            throw new RuntimeException("RSA私钥格式不正确，请检查是否正确配置了PKCS8格式的私钥", ie);
        } catch (Exception e) {
            throw new RuntimeException("RSAcontent = " + needSignContent, e);
        }
    }

    private static PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins) throws Exception {

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        String encodedKey = StreamUtil.readText(ins);

        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64Utils.decode(encodedKey)));
    }

    /**
     * 公钥验签
     * @param needCheckContent 需要验证签名的
     * @param sign
     * @param publicKey
     * @return
     * @throws
     */
    public static boolean rsaCheck(String needCheckContent, String sign, String publicKey) {

        try {
            PublicKey pubKey = getPublicKeyFromX509(SIGN_TYPE_RSA,
                    new ByteArrayInputStream(publicKey.getBytes()));

            Signature signature = Signature
                    .getInstance(RSA_SIGN_ALGORITHMS);

            signature.initVerify(pubKey);
            signature.update(needCheckContent.getBytes(RSA_SIGN_CHARSET));

            return signature.verify(Base64Utils.decode(sign));
        } catch (Exception e) {
            throw new RuntimeException("RSAcontent = " + needCheckContent + ",sign=" + sign, e);
        }

    }

    private static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        StringWriter writer = new StringWriter();
        StreamUtil.io(new InputStreamReader(ins), writer);

        String encodedKey = writer.toString();

        return keyFactory.generatePublic(new X509EncodedKeySpec(Base64Utils.decode(encodedKey)));
    }

    public static void main(String[] args) {
        // 1 该方法可以生成RSA的公私钥对
        RSAUtil.generateKeyPair();
        // 2 以下两行为上述方法生成的公钥和私钥，用于后面的演示
        //publicKey:MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAupeDIVJUELbMozgNYpRqSaxhYCfAZnF7WxMUo2BwQ2LdYTXnl59e78xVLMjl7wfBUvTqFEqER210JrGuSp/aQyvHqAdwIx56oWaIBu9EHpx1RemLfBfEN4252inCj/LyUp09rlAX2Vq79GiRd/Z0r7A06cbqtz0euHpKZkKfZqD0niFFS9a1YGVER1vxNJE7vBH4yv78YE7ZlPWKQsQjMboS/j2itQmC6QGG/w5xqBUiwH9jFuvh4vuMfnDpJb0IVHrMzDOPKuiauLjIBMCU9eKdCmmvX2XI5v4IfGp6h7rYqEU9/ff/EenyZDYTHYCJUjBNuBKZhRBTz03giedooQIDAQAB
        //privateKey:MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC6l4MhUlQQtsyjOA1ilGpJrGFgJ8BmcXtbExSjYHBDYt1hNeeXn17vzFUsyOXvB8FS9OoUSoRHbXQmsa5Kn9pDK8eoB3AjHnqhZogG70QenHVF6Yt8F8Q3jbnaKcKP8vJSnT2uUBfZWrv0aJF39nSvsDTpxuq3PR64ekpmQp9moPSeIUVL1rVgZURHW/E0kTu8EfjK/vxgTtmU9YpCxCMxuhL+PaK1CYLpAYb/DnGoFSLAf2MW6+Hi+4x+cOklvQhUeszMM48q6Jq4uMgEwJT14p0Kaa9fZcjm/gh8anqHutioRT399/8R6fJkNhMdgIlSME24EpmFEFPPTeCJ52ihAgMBAAECggEAabaxek10h0upwMde/fzsAvWA7cNXxDKDGuRCixV2RRdcKVkq7sJCPUXc89SWQMh6BQ/xFrHpI7alWc7M2kaal/fT1CTyqVL5UzwN0S/5vgYSgxuABTxJ1Nec9jc2neuc0qV3bqy99kzrs9sSn46oKWmBcPlbvZkJMKdIQ4t3V+LbBQdsrXmsTXMzHPVLkyVGTYGX0i6k8Kj0W9Luo5c94VVSQUbavY3cKCXd38CfMqKSCVI7jJeY5brB94TBglY8rX4h/NZKmPA+ICtGqV1CR8poov4m3MOjIjDoYdh3NxcFhOWu3RcnyU3zbjvCcJ25ICtzDYQthXFw1hbGhdI34QKBgQD6UOn+dCv9HT6FpriWdYjzuV7qz9dZrFRaH0ZIEkTSgvamDgC4dVEKfbUZQ7ggBlG/AzVjY8bN+vUsFAaVhmxBvs4C7RxUw1HDnhvSS5ADYyR6DwLRHHE7bsrrXUnSuhNZTqwOzJU/txtjwIKMxBqmjP+KuZ5GckilFhF2vH473QKBgQC+1CtrfQpkX6vV/9ncObz+unUhkEUjGdBZugCLNuV8AUVTDTcxO96EbfzA3uH/s1GYTiiUxWvx6Ab8RnYfj1JTsX8RCCPLTxDedvN0eJZEkOdrTDdLuqvbDMChW0xxeejh7k8DS8PDeX1068Urqufr6dMtsIkuQiBKjAJsuLxFlQKBgFbmgFW2Pw9Ad9QubSbO5xy13h3myfx3t1ESSnP646mxja9YbSeckuXxfk2nFOUJTJfjpUU7/mjEFxmWWjLO2AZ7F0osxwkmRdyDp2T6lqSWZrN5QqEWHrljXU8Ys+hUcSMwDMuWeroD955J1wjHi/kGOjEO/8O1KUMmxilShwCBAoGBAI3KqNideHNMgynnicGRRVUgxpfB0x1ZuO5yvqSyjaGucAxd7Xyvt48/CmdlNRgB7cb1jEG+z45XkZkx4eNhYxK8+GV3XuFaRnaGfC82yrD32nxnjU6/zaJdv2Qlqo9GvjviDuOsEDHWu9l8tmzT+K/adgek/MlaaRP7JHIUchBhAoGBAMTQ455rH4jtdJAo2+ESIVfJfY+pFh1qt3Ov3Yb7ztv+MXjXK1RFYUjPHM9nxuFOXWarqMkcafql6ClycsxTK8Ze20VIi8xjinfV7UlD8TpakZpu2NbcmCBR8hGm1rlCaqpfhb/PYKbs/pJ/GmsYdh/oMs5clWRNt/eAVj753ngD
        // 3 定义一个用于验签的json字符串内容
//        String content = "{\"a\":\"1\", \"b\":\"2\"}";
        String content = "{\"name\":\"xugang\",\"age\":23}";
//        String content = "{\"age\":23,\"name\":\"xugang\"}";

//        String content = "eyJuYW1lIjoieHVnYW5nIiwiYWdlIjoyM30=";
        // 4 定义私钥
        String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC6l4MhUlQQtsyjOA1ilGpJrGFgJ8BmcXtbExSjYHBDYt1hNeeXn17vzFUsyOXvB8FS9OoUSoRHbXQmsa5Kn9pDK8eoB3AjHnqhZogG70QenHVF6Yt8F8Q3jbnaKcKP8vJSnT2uUBfZWrv0aJF39nSvsDTpxuq3PR64ekpmQp9moPSeIUVL1rVgZURHW/E0kTu8EfjK/vxgTtmU9YpCxCMxuhL+PaK1CYLpAYb/DnGoFSLAf2MW6+Hi+4x+cOklvQhUeszMM48q6Jq4uMgEwJT14p0Kaa9fZcjm/gh8anqHutioRT399/8R6fJkNhMdgIlSME24EpmFEFPPTeCJ52ihAgMBAAECggEAabaxek10h0upwMde/fzsAvWA7cNXxDKDGuRCixV2RRdcKVkq7sJCPUXc89SWQMh6BQ/xFrHpI7alWc7M2kaal/fT1CTyqVL5UzwN0S/5vgYSgxuABTxJ1Nec9jc2neuc0qV3bqy99kzrs9sSn46oKWmBcPlbvZkJMKdIQ4t3V+LbBQdsrXmsTXMzHPVLkyVGTYGX0i6k8Kj0W9Luo5c94VVSQUbavY3cKCXd38CfMqKSCVI7jJeY5brB94TBglY8rX4h/NZKmPA+ICtGqV1CR8poov4m3MOjIjDoYdh3NxcFhOWu3RcnyU3zbjvCcJ25ICtzDYQthXFw1hbGhdI34QKBgQD6UOn+dCv9HT6FpriWdYjzuV7qz9dZrFRaH0ZIEkTSgvamDgC4dVEKfbUZQ7ggBlG/AzVjY8bN+vUsFAaVhmxBvs4C7RxUw1HDnhvSS5ADYyR6DwLRHHE7bsrrXUnSuhNZTqwOzJU/txtjwIKMxBqmjP+KuZ5GckilFhF2vH473QKBgQC+1CtrfQpkX6vV/9ncObz+unUhkEUjGdBZugCLNuV8AUVTDTcxO96EbfzA3uH/s1GYTiiUxWvx6Ab8RnYfj1JTsX8RCCPLTxDedvN0eJZEkOdrTDdLuqvbDMChW0xxeejh7k8DS8PDeX1068Urqufr6dMtsIkuQiBKjAJsuLxFlQKBgFbmgFW2Pw9Ad9QubSbO5xy13h3myfx3t1ESSnP646mxja9YbSeckuXxfk2nFOUJTJfjpUU7/mjEFxmWWjLO2AZ7F0osxwkmRdyDp2T6lqSWZrN5QqEWHrljXU8Ys+hUcSMwDMuWeroD955J1wjHi/kGOjEO/8O1KUMmxilShwCBAoGBAI3KqNideHNMgynnicGRRVUgxpfB0x1ZuO5yvqSyjaGucAxd7Xyvt48/CmdlNRgB7cb1jEG+z45XkZkx4eNhYxK8+GV3XuFaRnaGfC82yrD32nxnjU6/zaJdv2Qlqo9GvjviDuOsEDHWu9l8tmzT+K/adgek/MlaaRP7JHIUchBhAoGBAMTQ455rH4jtdJAo2+ESIVfJfY+pFh1qt3Ov3Yb7ztv+MXjXK1RFYUjPHM9nxuFOXWarqMkcafql6ClycsxTK8Ze20VIi8xjinfV7UlD8TpakZpu2NbcmCBR8hGm1rlCaqpfhb/PYKbs/pJ/GmsYdh/oMs5clWRNt/eAVj753ngD";
        // 5 定义公钥
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAupeDIVJUELbMozgNYpRqSaxhYCfAZnF7WxMUo2BwQ2LdYTXnl59e78xVLMjl7wfBUvTqFEqER210JrGuSp/aQyvHqAdwIx56oWaIBu9EHpx1RemLfBfEN4252inCj/LyUp09rlAX2Vq79GiRd/Z0r7A06cbqtz0euHpKZkKfZqD0niFFS9a1YGVER1vxNJE7vBH4yv78YE7ZlPWKQsQjMboS/j2itQmC6QGG/w5xqBUiwH9jFuvh4vuMfnDpJb0IVHrMzDOPKuiauLjIBMCU9eKdCmmvX2XI5v4IfGp6h7rYqEU9/ff/EenyZDYTHYCJUjBNuBKZhRBTz03giedooQIDAQAB";
        // 6 使用工具类生成签名
        String sign = RSAUtil.rsaSign(content, privateKey);
        System.out.println("sign:" + sign);
        // 7 使用工具类验签
        System.out.println("check result:" + RSAUtil.rsaCheck(content, sign, publicKey));

        // 模拟磐升的公钥私钥
        // publicKey:MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqzMIctrLmM7UExeWXHaFbyWjBTK0X9P8ySvE1fiAw+cjnHUe4B03RAgS6P5FXJ0iGx5SGc/6kVKztkGWtHfBDwmZbNz8/1X1sxILFyXvmYu9SROFNKLIkXlSQjFgJpzfbxksnXGFgYbTPribHCySddskrjYzny9DyHR1hTaxsbgrO/IjB0zmkUQTIYKbiYg1fAbt6pj8KHXdfME4H6MIFMOORS0A2mdw10VlNbEWq/VabT+iufTVA0IUYtGNcsS180FXo3uLRfnnsgacKC/xW/k4m6f0bqam1nghoB0hqO1bUlhxL8RQB0F1i4XFPinhby8TRINYiPiols73Oq+GOQIDAQAB
        //privateKey:MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCrMwhy2suYztQTF5ZcdoVvJaMFMrRf0/zJK8TV+IDD5yOcdR7gHTdECBLo/kVcnSIbHlIZz/qRUrO2QZa0d8EPCZls3Pz/VfWzEgsXJe+Zi71JE4U0osiReVJCMWAmnN9vGSydcYWBhtM+uJscLJJ12ySuNjOfL0PIdHWFNrGxuCs78iMHTOaRRBMhgpuJiDV8Bu3qmPwodd18wTgfowgUw45FLQDaZ3DXRWU1sRar9VptP6K59NUDQhRi0Y1yxLXzQVeje4tF+eeyBpwoL/Fb+Tibp/RupqbWeCGgHSGo7VtSWHEvxFAHQXWLhcU+KeFvLxNEg1iI+KiWzvc6r4Y5AgMBAAECggEAOyznpbOlHkuXmIXVrl41XybWQ4y0Nk9zGCIt5nwAHjb94y3En1jZ6oUT7D8/wHR7/n2riUqPZL3+amgfbH/cVpNN2ghE1wliZSQ+eyDcwelAY0sBByGhTE9wNHsWriIZRTChcEZ2DECLMinjS4jIkz5NXn5Tt5lBx78hhAtUvoXGiSQhBPsdIHqcG4qNWmyt20RXIiGQbVlDO06f3FKfK8N9yQ43Hc25JMo7dAbKolaZKbWbOnHz/F0QAr6egeivmaI5dJxSfUh8Vmcgy8xwLu2twZkF1/nCzbCpdipfFQsHLln1dgHj2DdpJKRMuXu/0JugA73sDeYxsrUD6CS7VQKBgQDS1KxJidpOxzsSUvsZtDymBW9IyIuqtpOs/w1wmPwDkIOH+c00QovWqPiHDbMzXVbIZQVjkQJsBxCRji2jeZEmvFK0aRT1BY9zb165qbezxWe23kS0gKuzqBddWkAXUQEc510appkqPQszTKrq8CK7K6EiSKh8p+GLYbISo8SGSwKBgQDP4LdpKzh1sAX9j8pXkWYXgSUsY3cZkA9/5W9lQ9X6jl5FXruSdgNqgfq62WQY7bYX42C3Pjl6DqiYlkwXcUKj8ruoeT9qsOTwG8lg4mx84Wp/4iEoKezFDpVNkeL+QhDJ2xXf7tPn9hcwPhe876YDvEnkMsRQ23LE6hbXCbSjCwKBgHImrFH0zbqxrI4zV7mHGP9bGfHyg4ye5j5YqlI4wtq7ODw7SJvvG//R5R5akJhTBVXjPJO4Q/Nxr8rfjF5ix/CNLsp3yt0PcagyuLsV5Vz0mT8uAU0qDc1sSZpF70WwmkWoD4UKhwuTmajg7fneoy9sZAZICNHVpAzvjJcDDLkZAoGANJCiUFm5gSjtmtyNUe9WXYMunzbXmwMuqCygKn8o0J7Hf8sgqzxkSL5tZGosQZF6hjfc8PkvF4o1X8BBpXviWgq7ITLnslbgzUZ5eaJHxJxLjeLkd3+Ce6HgZykIuTqDfEeGFIYT14gG/Q/2eLQwyqkGFkowEjIrp1d6Rw4RO1kCgYA/so77gk+corYRQ+oFaBZcL3vILKUt01Qa7t4XaXY83ovC3waPTUGLTXSko4vXoVfqL6GEdl8oOW6l7r6ueU/oJVtsa2UsaHOPExRS5hABuWdPMI1vCosTetWBLDmsUJux0Sr3GDtPpTSbfkD/1Mc01KO4snK262MKOxKz7EdVvQ==

    }
}
