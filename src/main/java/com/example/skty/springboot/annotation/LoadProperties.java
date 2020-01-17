package com.example.skty.springboot.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置加载指定的配置文件
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@RequestMapping
public @interface LoadProperties {

    @AliasFor("mappingFilePath")
    String value() default "classpath:mapping/mapping-config.properties";
    /**
     * 要注入到指定的类中的配置文件路径，支持classpath:格式
     *
     * @return
     */
    String mappingFilePath() default "classpath:mapping/mapping-config.properties";

    /**
     * 要注入的属性的前缀名
     *
     * @return
     */
    String prefix() default "";

}
