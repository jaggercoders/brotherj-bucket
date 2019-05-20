package com.brotherj.brotherjserver.GenerateController;

import java.io.Serializable;

/**
 * 通用的返回给web对象的封装
 * @param <T> 用来携带需要返回给web的对象
 */
public  class JsonResult<T> implements Serializable {

    private static final long serialVersionUID = 8604307160974083205L;

    private String resultCode;

    private String resultMsg;

    private T data;

    public JsonResult(){}

    public JsonResult(String resultCode, String resultMsg, T data){
        this.resultCode=resultCode;
        this.resultMsg=resultMsg;
        this.data = data;
    }


    public static <T> JsonResult<T> ok(T data) {
        return new JsonResult<T>("1000000", "成功", data);
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
