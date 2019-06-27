package com.brotherj.brotherjutil.ex;

import com.brotherj.brotherjutil.util.ResultCodeEnum;

public class AssertsException extends RuntimeException {

    private static final long serialVersionUID = 6760942289204361077L;


    private String code;

    public String getCode() {
        return code == null ? ResultCodeEnum.exchangeRequestFail.getResultCode() : code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AssertsException() {
        super();
    }

    public AssertsException(String message) {
        super(message);
        setCode(ResultCodeEnum.exchangeRequestFail.getResultCode());
    }

    public AssertsException(String message, Throwable cause) {
        super(message, cause);
    }


    public AssertsException(ResultCodeEnum codeEnum){
        super(codeEnum.getResultMsg());
        setCode(codeEnum.getResultCode());
    }

//    public AssertsException(ResultCodeEnum codeEnum,String message){
//        super(String.join(codeEnum.getResultMsg(),",",message));
//        setCode(codeEnum.getResultCode());
//    }

    public AssertsException(String code, String message){
        super(message);
        setCode(code);
    }
}
