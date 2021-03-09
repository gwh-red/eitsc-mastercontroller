package com.allimu.mastercontroller.config;

public class CommonsResult<T> {
    /**
     * 状态
     */
    private  Integer code;
    /**
     * 描述
     */
    private  String  msg;
    /**
     * 总数
     */
    private  Integer count;
    /**
     * 数据源
     */
    private  T  data;

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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public CommonsResult(Integer code, String msg, Integer count, T data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }
}
