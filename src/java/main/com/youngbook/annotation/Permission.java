package com.youngbook.annotation;
/**
 *
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Permission {

    /**
     * 可以判断多个权限，用“,”隔开
     * 例如 @Permission(require = "查看,修改")
     * 表明：需要拥有 查看 或 修改 权限，即可满足通过条件
     * @return
     */
    String require() default "";
}