package com.example.skty.springboot.configurations.value;

import com.example.skty.springboot.annotation.LoadProperties;

/**
 * 浏览器路由映射配置类
 */
//@PropertySource("classpath:config-value/application-url-mapping.properties")
@LoadProperties(path = "classpath:config-value/application-url-mapping.properties", prefix = "com.example.skty.springboot.controller.PersonController")
public class ControllerUrlMapping {
    public static final String queryPerson = "";
    public static final String queryPersonByName = new String("");

    static {
        System.out.println("===================================我记在了+++++++++++++++++++++++++++++");

    }

}
