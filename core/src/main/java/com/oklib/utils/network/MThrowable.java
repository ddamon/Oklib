package com.oklib.utils.network;


/**
 * @author Damon.Han
 */
public class MThrowable extends Exception {

    private int code;
    private String message;

    public MThrowable(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
