package com.youngbook.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Security {

    // 是否需要网站登录
    boolean needWebLogin() default false;
    boolean needWebCode() default false;
    boolean needToken() default false;
    boolean needMobileCode() default false;
}