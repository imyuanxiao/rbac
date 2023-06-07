package com.imyuanxiao.rbac.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @cl
 * @ClassName NotResponseBody
 * @Description Used to bypass the unified response body.
 * @Author: imyuanxiao
 * @Date: 2023/5/3 10:25
 * @Version 1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NotResponseBody {

}
