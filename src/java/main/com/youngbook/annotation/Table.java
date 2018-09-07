package com.youngbook.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {
    //FieldType fieldType() default FieldType.STRING;
    public String name() default "";
    public String jsonPrefix() default ""; // JSON前缀，用于初始化JEasyUI表单
    public String backupTableName() default ""; // JSON前缀，用于初始化JEasyUI表单
}
