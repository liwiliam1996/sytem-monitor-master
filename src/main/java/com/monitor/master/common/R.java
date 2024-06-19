package com.monitor.master.common;

public class R<T> {
    private T data;

    public R(T data, Integer code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public static R ok() {
        return new R(null, ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg());
    }


    public static R ok(Object data) {
        return new R(data, ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg());
    }


    public static R error(String msg) {
        return new R(null, ResponseEnum.WARNING.getCode(), msg);
    }

}
