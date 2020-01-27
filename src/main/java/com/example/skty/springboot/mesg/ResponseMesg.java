package com.example.skty.springboot.mesg;

import java.io.Serializable;

/**
 * 包装返回给客户端的响应信息类
 */
public class ResponseMesg<T> implements Serializable {
    /**
     * 响应码
     */
    private Integer status;
    /**
     * 响应信息
     */
    private String msg;
    /**
     * 响应数据
     */
    private T data;

    public ResponseMesg(Integer code, String msg, T data) {
        this.status = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseMesg() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
