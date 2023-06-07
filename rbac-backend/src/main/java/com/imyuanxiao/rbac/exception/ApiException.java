package com.imyuanxiao.rbac.exception;

import com.imyuanxiao.rbac.enums.ResultCode;
import lombok.Getter;

/**
 * @Author: imyuanxiao
 */
@Getter
public class ApiException extends RuntimeException{

    private ResultCode resultCode;
    private String msg;

    public ApiException(ResultCode resultCode, String msg) {
        this.resultCode = resultCode;
        this.msg = msg;
    }

    public ApiException(ResultCode resultCode) {
        this.resultCode = resultCode;
        this.msg = resultCode.getMsg();
    }

}
