package com.dunkeng.utils.network;

import android.content.Context;

import com.dunkeng.utils.logger.Logger;

/**
 * DownSubscriber
 */
public class DownSubscriber<ResponseBody extends okhttp3.ResponseBody> extends BaseSubscriber<ResponseBody> {
    private DownLoadCallBack callBack;
    private Context context;
    private String path;
    private String name;

    public DownSubscriber(String path, String name, DownLoadCallBack callBack, Context context) {
        super(context);
        this.path = path;
        this.name = name;
        this.callBack = callBack;
        this.context = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (callBack != null) {
            callBack.onStart();
        }
    }

    @Override
    public void onCompleted() {
        if (callBack != null) {
            callBack.onCompleted();
        }
    }

    @Override
    public void onError(final MThrowable e) {
        Logger.d(DownLoadManager.TAG, "DownSubscriber:>>>> onError:" + e.getMessage());
        callBack.onError(e);
    }

    @Override
    public void onNext(ResponseBody responseBody) {

        Logger.d(DownLoadManager.TAG, "DownSubscriber:>>>> onNext");

        DownLoadManager.getInstance(callBack).writeResponseBodyToDisk(path, name, context, responseBody);

    }
}
