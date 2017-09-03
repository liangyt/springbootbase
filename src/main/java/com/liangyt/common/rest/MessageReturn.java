package com.liangyt.common.rest;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：RestController 返回格式统一处理
 *
 * @author liangyt
 * @创建时间 2017-06-25 12:07
 */
@SuppressWarnings("all")
public class MessageReturn<T> {
    private final static int SUCCESS_CODE = 1;
    private final static int FAIL_CODE = 0;
    private final static int NOLOGIN = 401; // 未验证身份
    private final static int UNAUTHORIZED = 403; // 未授权
    private final static String SUCCESS = "成功";
    private final static String FAIL = "失败";
    private int code = 1;
    private String message = "";
    private T data;

    public MessageReturn(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 生成一个返回成功的模板，并初始化数据为一个 Map，可以通过调用 add 方法往里面添加数据。
     * @param <E>
     * @return
     */
    public static <E> MessageReturn<E> success() {
        return new MessageReturn(SUCCESS_CODE, SUCCESS, new HashMap());
    }

    /**
     * 通过调用 success 方法后可以调用该方法添加返回数据；
     * @param key
     * @param value
     * @return
     */
    public MessageReturn add(String key, Object value) {
        ((Map)this.getData()).put(key, value);
        return this;
    }

    /**
     * 返回成功的数据，类型随意
     * @param data
     * @param <E>
     * @return
     */
    public static <E> MessageReturn<E> success(E data) {
        return new MessageReturn(SUCCESS_CODE, SUCCESS, data);
    }

    /**
     * 返回成功提示，成功提示的内容通过参数传送
     * @param message
     * @param <E>
     * @return
     */
    public static <E> MessageReturn<E> success(String message) {
        return new MessageReturn(SUCCESS_CODE, message, null);
    }

    /**
     * 返回失败提示，失败提示的内容通过参数传送
     * @param message
     * @param <E>
     * @return
     */
    public static <E> MessageReturn<E> fail(String message) {
        return new MessageReturn(FAIL_CODE, message, null);
    }

    /**
     * 失败 并初始化数据为一个 Map，可以通过调用 add 方法往里面添加数据。
     * @param <E>
     * @return
     */
    public static <E> MessageReturn<E> fail() {
        return new MessageReturn(FAIL_CODE, FAIL, new HashMap());
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
