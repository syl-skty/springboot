package com.example.skty.springboot.configurations;

import com.example.skty.springboot.configurations.customer.MyRequestMappingHandlerMapping;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 这里配置springmvc的核心功能注册组件，可以通过继承并重写spring内部的某些组件，来实现我们自定义的一些功能，之后再将我们的组件在这里注册，以替换原先使用的组件
 *
 * @author skty
 */
@Configuration
public class CustomerWebMvcRegistrations implements WebMvcRegistrations {
    /**
     * Return the custom {@link RequestMappingHandlerMapping} that should be used and
     * processed by the MVC configuration.
     * <p>
     * 配置在mvc对Controller读取路由映射的组件，这个组件的功能主要是读取Controller上的注解，并将所有的RequestMapping注解的方法与其对应书写的请求地址进行映射。
     * 生成路由映射表，进行保存。以供在请求时对url分析，找到对应的执行方法
     *
     * @return the custom {@link RequestMappingHandlerMapping} instance
     */
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new MyRequestMappingHandlerMapping();
    }
}
