package com.luoyenot.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WQ
 * @version 创建时间：2019年8月10日 上午9:39:03
 * @ClassName 类名称
 * @Description 类描述 此类作用是将返回给浏览器的数据再次封装
 */

public class Msg implements Serializable {

    // 操作是否成功代码，1表示出错，0表示成功
    private Integer code;

    // 提示信息
    private String msg;

    // 以map的形式封装返回的数据
    private Map<String, Object> map = new HashMap<String, Object>();

    // 操作成功调用的方法
    public static Msg success() {
        Msg msg = new Msg();
        msg.setCode(0);
        msg.setMsg("处理成功！");
        return msg;
    }

    // 操作失败调用的方法
    public static Msg failure() {
        Msg msg = new Msg();
        msg.setCode(1);
        msg.setMsg("处理失败！");
        return msg;
    }

    //对链式编程的支持
    public Msg add(String key, Object value) {
        this.getMap().put(key, value);
        return this;
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

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

}
