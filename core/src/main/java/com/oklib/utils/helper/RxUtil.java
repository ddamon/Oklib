package com.oklib.utils.helper;


import android.view.View;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * @author Damon
 */
public class RxUtil {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Rx点击事件
     *
     * @param view
     * @param listener
     */
    public static void click(View view, OnRxClickListener listener) {
//        RxView.clicks(view)
//                .throttleFirst(500, TimeUnit.MILLISECONDS)
//                .subscribe(new Observer<Object>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                    @Override
//                    public void onNext(Object value) {
//                        if (listener != null) {
//                            listener.onRxClick(view);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                        Logger.e("RxUtils.click error: " + e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }
}
