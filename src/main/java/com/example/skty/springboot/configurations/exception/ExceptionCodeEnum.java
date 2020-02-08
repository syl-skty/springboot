package com.example.skty.springboot.configurations.exception;

/**
 * 异常响应码枚举类
 */
public enum ExceptionCodeEnum {
    OTHEREXCEPTION("系统异常", Exception.class, 9999);

    /**
     * 描述
     */
    private String description;

    /**
     * 异常类型
     */
    private Class<? extends Throwable> exception;

    /**
     * 异常编码
     */
    private Integer code;

    ExceptionCodeEnum(String description, Class<? extends Throwable> exceptionClazz,
                      Integer code) {
        this.description = description;
        this.exception = exceptionClazz;
        this.code = code;
    }

    /**
     * 通过异常获取指定的异常码
     *
     * @param exceptionClass 异常类
     * @return 返回对应的异常码，否则返回未知异常
     */
    public static Integer transformExceptionCode(Class<? extends Throwable> exceptionClass) {
        return getExceptionCodeEnum(exceptionClass).code;
    }

    /**
     * 获取异常码枚举对象
     *
     * @param exceptionClass
     * @return
     */
    public static ExceptionCodeEnum getExceptionCodeEnum(Class<? extends Throwable> exceptionClass) {
        ExceptionCodeEnum[] values = ExceptionCodeEnum.values();
        for (ExceptionCodeEnum codeEnum : values) {
            if (codeEnum.exception.equals(exceptionClass)) {
                return codeEnum;
            }
        }
        return OTHEREXCEPTION;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Class<? extends Throwable> getException() {
        return exception;
    }

    public void setException(Class<? extends Throwable> exception) {
        this.exception = exception;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
