package com.syc.perms.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一的返回结果
 * {'code':0,'msg':'xxx',count:0,'data':{xxx}}
 */
@Data
public class R implements Serializable {

    private Integer code;
    private String msg = "";
    private Long count = 0L;
    private Object data;

    public R(){

    }

    public R(Integer code) {
        super();
        this.code = code;
    }

    public R(Integer code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public static R ok() {
        return new R(0);
    }

    public static R ok(Object list) {
        R result = new R();
        result.setCode(0);
        result.setData(list);
        ;
        return result;
    }

    public static R ok(String msg) {
        R result = new R();
        result.setCode(0);
        result.setMsg(msg);
        return result;
    }

    public static R error() {
        return new R(500, "没有此权限，请联系超管！");
    }

    public static R error(String str) {
        return new R(500, str);
    }

}
