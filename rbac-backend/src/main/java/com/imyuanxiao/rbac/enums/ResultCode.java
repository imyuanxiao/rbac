package com.imyuanxiao.rbac.enums;

import lombok.Getter;

/**
 * @description result code
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Getter
public enum ResultCode {

    //操作成功
    SUCCESS(0, "操作成功"),

    // 参数校验相关
    PARAMS_ERROR(40000, "请求参数错误"),
    VALIDATE_FAILED(1006, "参数校验失败"),

    // token相关
    UNAUTHORIZED(1001, "没有登录"),
    INVALID_TOKEN(1002, "无效的token"),
    TOKEN_EXPIRED(1003, "token已过期"),

    // 权限相关
    FORBIDDEN(1004, "没有相关权限"),
    UNAUTHORIZED_OPERATION(1005, "未授权的操作"),


    // 未找到资源
    RESOURCE_NOT_FOUND(1007, "资源不存在"),
    USER_NOT_FOUND(1008, "用户不存在"),
    ROLE_NOT_FOUND(1009, "角色不存在"),
    PERMISSION_NOT_FOUND(1010, "权限不存在"),

    // 账号相关
    ACCOUNT_STATE_DISABLED(1011, "账号已禁用"),
    ACCOUNT_STATE_DELETED(1012, "账号已注销"),


    CODE_EXISTS(1013, "验证码已发送，请稍后再试"),
    REGISTER_SUCCESS(1014, "Register successful"),
    ACCOUNT_TAKEOVER(1015, "Account takeover by another user"),

    // 其他错误
    FAILED(2001, "操作失败"),
    DATABASE_ERROR(2002, "数据库操作异常"),

    ERROR(5000, "未知错误"),

    METHOD_NOT_ALLOWED(405, "Method not allowed!"),
    BAD_REQUEST(400, "Bad Request");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
