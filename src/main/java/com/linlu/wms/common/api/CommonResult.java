package com.linlu.wms.common.api;

/**
 * 通用结果返回
 *
 * @author xi
 */
public class CommonResult<T> {
    private Long code;
    private String msg;
    private T data;

    private CommonResult() {
    }

    ;

    private CommonResult(Long code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param data 结果
     * @return 封装结果
     */
    public static <T> CommonResult<T> success(T data) {
        return success(ResultCode.SUCCESS.getMsg(), data);
    }

    /**
     * 成功返回自定义消息及结果
     *
     * @param msg  提示消息
     * @param data 结果
     * @return 封装结果
     */
    public static <T> CommonResult<T> success(String msg, T data) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), msg, data);
    }

    /**
     * 失败返回自定义消息及结果
     *
     * @param msg  提示消息
     * @param data 结果
     * @return 封装结果
     */
    public static <T> CommonResult<T> fail(String msg, T data) {
        return new CommonResult<T>(ResultCode.FAIL.getCode(), msg, data);
    }

    /**
     * 失败返回自定义消息及结果
     *
     * @param resultCode 提示信息
     * @return 封装结果
     */
    public static <T> CommonResult<T> fail(ResultCode resultCode) {
        return fail(resultCode.getMsg(), null);
    }
}
