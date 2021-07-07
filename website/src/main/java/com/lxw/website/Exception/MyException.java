package com.lxw.website.Exception;

/**
 * @author LXW
 * @date 2021年04月26日 13:17
 */
public class MyException  extends  RuntimeException{

    private int code;   //异常编码

    public MyException(int code, String message, String decinfo) {
        this.code = code;
        this.message = message;
        this.decinfo = decinfo;
    }

    private String message;  //异常信息
    private String decinfo;   //描述

    public MyException() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDecinfo() {
        return decinfo;
    }

    public void setDecinfo(String decinfo) {
        this.decinfo = decinfo;
    }
}
