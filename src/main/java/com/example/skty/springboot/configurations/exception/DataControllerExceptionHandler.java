package com.example.skty.springboot.configurations.exception;

import com.example.skty.springboot.mesg.ResponseMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

/**
 * 专门处理rest接口的异常处理类（现在是统一处理所有接口的异常，后面可能根据每一个业
 * 务的不同自己实现不同的异常处理器）
 */
@RestControllerAdvice(basePackages = "com.example.skty.springboot.controller")
public class DataControllerExceptionHandler {

    private static final Log logger = LogFactory.getLog(DataControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public Object handler(Exception e, HttpServletRequest request) throws Exception {
        //判断是否需要返回json数据，如果需要，则在这边进行返回json数据，否则继续将异常进行上抛
        //交给根异常处理器进行处理异常
        if (acceptJsonType(request)) {
            logger.error("Controller出现异常:", e);
            ResponseMessage.ErrorMessage errorMessage = ExceptionCodeConverter.convertFromException(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body
                    (errorMessage);
        } else {
            //处理不了。继续上抛
            throw e;
        }
    }

    /**
     * 判断当前浏览器请求是否是请求json数据，如果是的话，就将异常信息包装为json异常对象进行返回
     *
     * @return true：请求json数据   false:请求其他类型的数据
     */
    private boolean acceptJsonType(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeaders(HttpHeaders.ACCEPT)).map(StringUtils::toStringArray)
                .map(Arrays::asList).map(MediaType::parseMediaTypes)
                .filter(mediaTypes -> mediaTypes.stream().anyMatch(MediaType.APPLICATION_JSON::equals))
                .isPresent();
    }
}
