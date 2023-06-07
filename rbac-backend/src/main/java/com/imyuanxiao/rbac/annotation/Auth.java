package com.imyuanxiao.rbac.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName Auth
 * @Description This annotation is used to mark whether this API needs to be managed by permission control.
 * @Author imyuanxiao
 * @Date 2023/5/7 11:10
 * @Version 1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE}) // Indicates that this annotation can be applied to both classes and methods.
public @interface Auth {
    /**
     * permission ID，unique
     */
    long id();
    /**
     * permission name
     */
    String name();
}