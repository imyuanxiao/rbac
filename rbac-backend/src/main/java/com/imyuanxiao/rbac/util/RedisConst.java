package com.imyuanxiao.rbac.util;

/**
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 */
public class RedisConst {
    public static final String APP_NAME = "rbac";
    public static final String REGISTER_CODE_KEY = APP_NAME + ":register:code:";
    public static final Long REGISTER_CODE_TTL = 10L;
    public static final String LOGIN_USER_KEY = APP_NAME + ":login:user:";
    public static final Long LOGIN_USER_TTL = 30L;


}
