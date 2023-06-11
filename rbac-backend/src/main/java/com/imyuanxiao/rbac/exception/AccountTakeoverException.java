package com.imyuanxiao.rbac.exception;

/**
 * @description AccountTakeoverException
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
public class AccountTakeoverException extends RuntimeException {
    private String msg;
    public AccountTakeoverException(String msg) {
        super(msg);
    }

}
