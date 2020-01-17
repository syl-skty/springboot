package com.example.skty.springboot.configurations;

import com.example.skty.springboot.configurations.value.MyRequestMappingHandlerMapping;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;

/**
 * springmvc组件配置
 */
@Configuration
public class CustomerWebMvcConfigurer implements WebMvcConfigurer, WebMvcRegistrations {

    /**
     * 添加返回值处理器（用于自定义处理返回值）
     *
     * @param handlers
     */
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        //handlers.add(new MessageWrapper(new ArrayList<>()));
    }

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new MyRequestMappingHandlerMapping();
    }
}
