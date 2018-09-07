package com.youngbook.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Json {
    //FieldType fieldType() default FieldType.STRING;
    public String name() default "";
    public String formPrefix() default "";
    public boolean exclude() default false;
}
