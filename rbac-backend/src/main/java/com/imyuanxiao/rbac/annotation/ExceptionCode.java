package com.imyuanxiao.rbac.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName NotResponseBody
 * @Description Custom annotation for field validation.
 * @Author: imyuanxiao
 * @Date: 2023/5/3 9:57
 * @Version 1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExceptionCode {
    int value() default 100000;
    String message() default  "Parameter validation error.";
}
