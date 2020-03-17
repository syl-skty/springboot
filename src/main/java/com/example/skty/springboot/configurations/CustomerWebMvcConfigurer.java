package com.example.skty.springboot.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * springmvc组件配置,这边是通过回调函数的方式将配置注入到mvc配置中。更为便捷地配置
 * 如果要更深层的定制，可以继承WebMvcConfigurationSupport类，重写里面的一些方法
 * 在这里可以自定义大部分springmvc的组件
 *
 * @author skty
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

    /**
     * 自定义的Controller异常处理
     *
     * @param resolvers
     */
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        //resolvers.add(new CustomerExceptionHandler());
    }

    /**
     * 配置静态资源取消拦截（这个配置也可以直接在配置文件中配置spring.resources.static-locations）
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //js静态资源url映射，直接通过localhost:8080/js/文件名   既可以访问/static/public/javascript/下的文件，可以隐藏文件路径
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/public/javascript/");
        //css静态资源url映射，直接通过localhost:8080/css/文件名   既可以访问，可以隐藏文件路径
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/public/css/");
    }
}
