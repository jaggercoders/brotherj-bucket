package com.brotherj.brotherjserver.GenerateController;

import com.brotherj.brotherjutil.util.ResultCodeEnum;

import java.io.Serializable;

/**
 * 通用的返回给web对象的封装
 * @param <T> 用来携带需要返回给web的对象
 */
public  class JsonResult<T> implements Serializable {

    private static final long serialVersionUID = 4247603462952716106L;

    private String resultCode;

    private String resultMsg;

    private T data;

    public JsonResult(){}

    public JsonResult(String resultCode, String resultMsg, T data){
        this.resultCode=resultCode;
        this.resultMsg=resultMsg;
        this.data = data;
    }

    public JsonResult(String resultCode, String resultMsg){
        this.resultCode=resultCode;
        this.resultMsg=resultMsg;
    }

    public static <T> JsonResult<T> fail(String code, String msg) {
        return new JsonResult<>(code, msg, null);
    }

    public static <T> JsonResult<T> fail(String msg) {
        return new JsonResult<>(ResultCodeEnum.exchangeRequestFail.getResultCode(), msg, null);
    }
    public static <T> JsonResult<T> fail(ResultCodeEnum codeEnum, T data) {
        return new JsonResult<>(codeEnum.getResultCode(), codeEnum.getResultMsg(), data);
    }

    public static <T> JsonResult<T> ok(T data) {
        return new JsonResult<T>(ResultCodeEnum.success.getResultCode(), ResultCodeEnum.success.getResultMsg(), data);
    }

    public static <T> JsonResult<T> timeout(String msg) {
        return new JsonResult<>(ResultCodeEnum.EXCHANGE_REQUEST_TIMEOUT.getResultCode(), msg, null);
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
