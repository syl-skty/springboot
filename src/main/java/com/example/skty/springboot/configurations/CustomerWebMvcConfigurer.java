package com.example.skty.springboot.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * springmvc组件配置
 */
@Configuration
public class CustomerWebMvcConfigurer implements WebMvcConfigurer {

    /**
     * 添加返回值处理器（用于自定义处理返回值）
     *
     * @param handlers
     */
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        //handlers.add(new MessageWrapper(new ArrayList<>()));
    }


}
