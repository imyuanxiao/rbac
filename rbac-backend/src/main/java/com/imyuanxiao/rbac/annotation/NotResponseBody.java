package com.imyuanxiao.rbac.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description  Used to bypass the unified response body.
 * @author : imyuanxiao
 * @date : 2023/5/3 10:25
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NotResponseBody {

}
