package com.monitor.master.common;

public enum ResponseEnum {
    SUCCESS(12000, "请求成功"),
    WARNING(14001, "数据格式问题"),
    INTERL_ERROR(15000, "内部错误");

    private Integer code;

    private String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
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
}
