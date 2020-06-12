package com.test.rbac.common.other;

/**
 * @author DNYY
 */

public enum ErrorCode {
    Normal(10001,null,"参数错误");

    /**
     * 返回码
     */
    public Integer code;

    /**
     * 返回内容
     */
    public Object data;

    /**
     * 返回信息
     */
    public String msg;

    ErrorCode(Integer code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    ErrorCode() {
    }
}
