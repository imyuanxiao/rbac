package com.imyuanxiao.rbac.model.vo;

import com.imyuanxiao.rbac.annotation.ExceptionCode;
import com.imyuanxiao.rbac.enums.ResultCode;
import lombok.Getter;

/**
 * @description  Customize unified response body. 和umiJs最佳实践统一
 * @author  imyuanxiao
 **/
@Getter
public class ResultVO<T> {

    private boolean success;

    private int errorCode;

    private String errorMessage;

    private T data;

    public ResultVO(T data) {
        this(ResultCode.SUCCESS, data);
        this.success = true;
    }

    public ResultVO(ResultCode resultCode, T data) {
        this.errorCode = resultCode.getCode();
        this.errorMessage = resultCode.getMsg();
        this.data = data;
    }

    public ResultVO(int code, String message, T data) {
        this.errorCode = code;
        this.errorMessage = message;
        this.data = data;
    }

    public ResultVO(ExceptionCode annotation, T data) {
        this.errorCode = annotation.value();
        this.errorMessage = annotation.message();
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format("{\"code\":%d,\"msg\":\"%s\",\"data\":\"%s\"}", errorCode, errorMessage, data.toString());
    }
}
