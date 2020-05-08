package com.jimzhang.dto;

/**
 * Created by admin on 2017/8/7.
 */
public class ErrorInfo<T> {

    public static final Integer OK = 0;
    public static final Integer ERROR = 500;

    private Integer code;
    private String message;
    private String url;
    private T data;

    public static Integer getERROR() {
        return ERROR;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
