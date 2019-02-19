package com.oklib.base;

import android.content.Context;

/**
 * @author Damon
 */

public interface CoreBaseView {
    /**
     * 获取context
     *
     * @return
     */
    Context getContext();

    /**
     * 用来显示日志或者消息
     *
     * @param msg
     */
    void showMsg(String msg);

    /**
     * 用来显示toast
     *
     * @param msg
     */
    void showToast(String msg);

}
