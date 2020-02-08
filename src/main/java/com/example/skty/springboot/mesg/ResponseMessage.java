package com.example.skty.springboot.mesg;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 包装返回给客户端的响应信息类
 */
public class ResponseMessage implements Serializable {

    private static final String SUCCESS = "success";

    private static final String ERROR = "error";

    /**
     * 响应状态，success,error
     */
    protected String status;

    private ResponseMessage() {
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 方便的创建一个响应码为200，只包含响应数据的响应体
     *
     * @param data 响应体包含的数据
     * @return 返回包装当前数据的返回体对象
     */
    public static SuccessMessage successMessage(Object data) {
        return new SuccessMessage(data);
    }

    /**
     * 创建一个异常的响应体数据
     *
     * @param description 响应的数据
     * @param errorCode   错误码
     * @return 返回包含当前错误数据的错误体
     */
    public static ErrorMessage errorMessage(String description, Integer errorCode) {
        return new ErrorMessage(description, errorCode);
    }

    /**
     * 成功返回的响应体，只能通过外部类进行创建,响应码为200
     */
    public static class SuccessMessage extends ResponseMessage {

        private Integer code = HttpStatus.OK.value();

        /**
         * 响应数据体
         */
        private Object data;

        private SuccessMessage(Object data) {
            this.data = data;
            this.status = SUCCESS;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }
    }

    /**
     * 成功返回的响应体，只能通过外部类进行创建
     */
    public static class ErrorMessage extends ResponseMessage {

        /**
         * 响应数据体
         */
        private String description;

        /**
         * 当前错误数据体对应的错误码
         */
        private Integer errorCode;

        private ErrorMessage(String description, Integer errorCode) {
            this.description = description;
            this.errorCode = errorCode;
            this.status = ERROR;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(Integer errorCode) {
            this.errorCode = errorCode;
        }
    }
}
