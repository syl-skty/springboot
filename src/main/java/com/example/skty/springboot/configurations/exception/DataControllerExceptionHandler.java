package com.example.skty.springboot.configurations.exception;

import com.example.skty.springboot.mesg.ResponseMesg;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 专门处理rest接口的异常处理类（现在是统一处理所有接口的异常，后面可能根据每一个业
 * 务的不同自己实现不同的异常处理器）
 */
@RestControllerAdvice(basePackages = "com.example.skty.springboot.controller")
public class DataControllerExceptionHandler {

    private static final Log logger = LogFactory.getLog(DataControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    // @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity handler(Exception e) {
        logger.error("Controller出现异常:", e);
        ResponseMesg<Object> responseMesg = new ResponseMesg<Object>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage(), "");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(responseMesg);
    }


}
