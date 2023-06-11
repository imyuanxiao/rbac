package com.imyuanxiao.rbac.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description  Custom annotation for field validation.
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExceptionCode {
    int value() default 100000;
    String message() default  "Parameter validation error.";
}
