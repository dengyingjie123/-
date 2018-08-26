package com.youngbook.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DataAdapter {
    /**
     * 如果类型为 FieldType.DATE，则可以设置fieldFormat的取值，对日期字段进行格式化，
     * 取值可以参考Database.DATEFORMAT_YYYYMMDDHH24MISS，其值就是MySQL对日期格式化的字符串
     * @return
     */
    FieldType fieldType() default FieldType.STRING;
    String fieldName() default "";
    String fieldFormat() default "";
    String fieldResource() default "";
}