/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: ExampleException
 * Author:   gengxin.hao
 * Date:     2021/4/7 16:47
 * Description:
 * History:
 */
package com.hgx.springboot.example.redis.exception;

/**
 * 〈〉
 *
 * @author gengxin.hao
 * @create 2021/4/7
 * @since 1.0.0
 */
public class ExampleException extends Exception{

    private String errCode;

    public ExampleException() {
    }

    public ExampleException(String errCode) {
        super(errCode);
        this.errCode = errCode;
    }

    public ExampleException(String errCode, Throwable cause) {
        super(cause);
        this.errCode = errCode;
    }

    public ExampleException(String errCode, String message) {
        super(message);
        this.errCode = errCode;
    }

    public ExampleException(String errCode, String message, Throwable cause) {
        super(message, cause);
        this.errCode = errCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
}
