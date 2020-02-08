package com.example.skty.springboot.mesg;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;

/**
 * 信息转换拦截器，用于将接口返回的数据包装为标准的信息返回体，{@link ResponseMessage },同时将一些错误的异常信息进行封装为消息体对象
 */

@RestControllerAdvice("com.example.skty.springboot.controller")
public class MessageWrapper implements ResponseBodyAdvice<Object> {

    /**
     * 配置使用当前的返回体转换类的，符合注解
     * 这边暂时配置为当方法上存在ResponseBody注解和方法上使用了RestController注解时，才使用这个转换类进行转换
     */
    private static final Class<Annotation>[] suitableAnnotations = new Class[]{ResponseBody.class, RestController.class};

    /**
     * Whether this component supports the given controller method return type
     * and the selected {@code HttpMessageConverter} type.
     *
     * @param returnType    the return type
     * @param converterType the selected converter type
     * @return {@code true} if {@link #beforeBodyWrite} should be invoked;
     * {@code false} otherwise
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return Stream.of(suitableAnnotations).anyMatch(annotation -> {
            return returnType.hasMethodAnnotation(annotation) || AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), annotation);
        });
    }


    /**
     * Invoked after an {@code HttpMessageConverter} is selected and just before
     * its write method is invoked.
     *
     * @param body                  the body to be written
     * @param returnType            the return type of the controller method
     * @param selectedContentType   the content type selected through content negotiation
     * @param selectedConverterType the converter type selected to write to the response
     * @param request               the current request
     * @param response              the current response
     * @return the body that was passed in or a modified (possibly new) instance
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return ResponseMessage.successMessage(body);
    }
}
