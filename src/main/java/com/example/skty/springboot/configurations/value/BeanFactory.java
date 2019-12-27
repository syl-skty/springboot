package com.example.skty.springboot.configurations.value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class BeanFactory {

    @Order(Ordered.HIGHEST_PRECEDENCE + 110)
    @Bean
    public RequestMappingHandlerMapping RequestMappingHandlerMapping() {
        return new MyRequestMappingHandlerMapping();
    }
}
