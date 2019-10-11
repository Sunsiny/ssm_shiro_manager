package com.syc.perms.exception;

/**
 * 自定义异常
 * RuntimeException运行时异常
 */
public class CommonException extends RuntimeException {


    private String msg;
    private int code;

    public CommonException() {
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonException(String message, int code) {
        super(message);
        this.msg = message;
        this.code = code;
    }

    public CommonException(String message, int code,Throwable cause) {
        super(message,cause);
        this.msg = message;
        this.code = code;
    }

}
