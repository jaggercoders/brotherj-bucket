package com.brotherj.brotherjutil.util;

/**
 * 类描述：返回码
 *
 * @author 王洁
 */
public enum ResultCodeEnum {

    success("exchange请求成功","10110000"),
    EXCHANGE_REQUEST_TIMEOUT("exchange请求超时","99999999"),
    exchangeRequestFail("exchange请求失败","10110001"),

    ;

    private String resultMsg;
    private String resultCode;

    ResultCodeEnum(String resultMsg, String resultCode) {
        this.resultCode=resultCode;
        this.resultMsg =resultMsg;
    }
    public String getResultCode() {
        return resultCode;
    }
    public String getResultMsg() {
        return resultMsg;
    }
}
