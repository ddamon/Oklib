package com.dunkeng.common;

import java.util.List;

/**
 * Created by Damon.Han on 2019/2/20 0020.
 *
 * @author Damon
 */
public class CommonModel<T> {
    private int code;
    private String msg;
    private List<T> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<T> newslist) {
        this.newslist = newslist;
    }
}
