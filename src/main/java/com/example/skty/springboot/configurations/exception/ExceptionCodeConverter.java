package com.example.skty.springboot.configurations.exception;

import com.example.skty.springboot.mesg.ResponseMessage;
import org.springframework.util.StringUtils;

/**
 * 异常码转换器，通过指定的异常生成对应的异常码
 */
class ExceptionCodeConverter {

    /**
     * 获取指定异常对应的异常返回体
     *
     * @param e
     * @return
     */
    public static ResponseMessage.ErrorMessage convertFromException(Exception e) {
        ExceptionCodeEnum exceptionCodeEnum = ExceptionCodeEnum.getExceptionCodeEnum(e.getClass());
        String message = null;
        if (exceptionCodeEnum == ExceptionCodeEnum.OTHEREXCEPTION) {
            message = ExceptionCodeEnum.OTHEREXCEPTION.getDescription();
        } else {
            message = StringUtils.hasText(e.getMessage()) ? e.getMessage() : exceptionCodeEnum.getDescription();
        }
        return ResponseMessage.errorMessage(message, exceptionCodeEnum.getCode());
    }
}
