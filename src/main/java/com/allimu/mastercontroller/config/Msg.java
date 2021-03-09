package com.allimu.mastercontroller.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回格式
 *
 * @author Administrator
 */
public class Msg {

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 状态
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回的数据
     */
    private Map<String, Object> data = new HashMap<String, Object>();

    // 构造方法私有化
    private Msg() {
    }

    /**
     * 成功：
     * setSuccess：true
     * setCode：ResultCode.SUCCEED
     * setMessage：成功
     *
     * @return
     */
    public static Msg ok() {
        Msg msg = new Msg();
        msg.setSuccess(true);
        msg.setCode(ResultCode.SUCCEED);
        msg.setMessage("成功");
        return msg;
    }
    /**
     * 成功：
     *  setSuccess：false
     * setCode：ResultCode.ERROR
     * setMessage：失败
     *
     * @return
     */
    public static Msg fail() {
        Msg msg = new Msg();
        msg.setSuccess(false);
        msg.setCode(ResultCode.ERROR);
        msg.setMessage("失败");
        return msg;
    }

    // 链式编程
    public Msg dataPut(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Msg data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

    public Msg code(Integer code) {
        this.setCode(code);
        return this;
    }

    public Msg success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public Msg message(String message) {
        this.setMessage(message);
        return this;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
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

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

}
