package com.ljdll.nettyServer.common.constant;

import lombok.Data;

@Data
public class R<T> {
    private int code;
    private String msg;
    private T data;

    public static int SUCCESS = 200;
    public static int FAIL = 500;
    public static int UNAUTHORIZED = 401;
    public static int FORBIDDEN = 403;
    public static int NOT_FOUND = 404;
    public static int METHOD_NOT_ALLOWED = 405;

    private R() {
    }

    public static <T> R<T> ok() {
        R<T> r = new R<T>();
        r.setCode(SUCCESS);
        r.setMsg("成功");
        return r;
    }

    public static <T> R<T> ok(T data) {
        R<T> r = new R<T>();
        r.setCode(SUCCESS);
        r.setMsg("成功");
        r.setData(data);
        return r;
    }

    public static <T> R<T> ok(String msg, T data) {
        R<T> r = new R<T>();
        r.setCode(SUCCESS);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static <T> R<T> fail() {
        R<T> r = new R<T>();
        r.setCode(FAIL);
        r.setMsg("失败");
        return r;
    }

    public static <T> R<T> fail(String msg) {
        R<T> r = new R<T>();
        r.setCode(FAIL);
        r.setMsg(msg);
        return r;
    }

    public static <T> R<T> fail(int code, String msg) {
        R<T> r = new R<T>();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static <T> R<T> fail(T data) {
        R<T> r = new R<T>();
        r.setCode(FAIL);
        r.setMsg("失败");
        r.setData(data);
        return r;
    }

    public static <T> R<T> fail(String msg, T data) {
        R<T> r = new R<T>();
        r.setCode(FAIL);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static <T> R<T> fail(int code, String msg, T data) {
        R<T> r = new R<T>();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
}
