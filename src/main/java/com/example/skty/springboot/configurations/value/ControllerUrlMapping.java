package com.example.skty.springboot.configurations.value;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 浏览器路由映射配置类
 */
@Configuration
@PropertySource("classpath:config-value/application-url-mapping.properties")
@ConfigurationProperties(prefix = "com.example.skty.springboot.controller.personcontroller.queryperson")
public class ControllerUrlMapping {
    public String queryPerson;
    public String queryPersonByName;

    public String getQueryPerson() {
        return queryPerson;
    }

    public void setQueryPerson(String queryPerson) {
        this.queryPerson = queryPerson;
    }

    public String getQueryPersonByName() {
        return queryPersonByName;
    }

    public void setQueryPersonByName(String queryPersonByName) {
        this.queryPersonByName = queryPersonByName;
    }
}
