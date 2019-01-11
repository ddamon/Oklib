package com.dm.learn.rxjava.ch2.presenter;

import com.dm.learn.rxjava.ch2.contract.RxjavaContract;
import com.oklib.base.CoreBasePresenter;
import com.oklib.utils.Logger.Logger;
import com.oklib.utils.helper.RxUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;


/**
 * Created by Damon.Han on 2017/3/29 0029.
 *
 * @author Damon
 */

public class RxjavaPresenter extends CoreBasePresenter<RxjavaContract.Model, RxjavaContract.View> {
    @Override
    public void onStart() {

    }

    public void getData() {
        mView.showMsg("tool");
    }

    public void testDo() {
        Observable.just("Hello")
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Logger.e("doOnNext: " + s);
                    }
                })
                .doAfterNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Logger.e("doAfterNext ");
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Logger.e("doOnSubscribe: ");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Logger.e("doOnComplete: ");
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        Logger.e("doAfterTerminate: ");
                    }
                })
                //每发射一个数据调用一次（包含onNext,onError,onComplete）
                .doOnEach(new Consumer<Notification<String>>() {
                    @Override
                    public void accept(Notification<String> stringNotification) throws Exception {
                        Logger.e("doOnEach: ");
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        Logger.e("doFinally: ");
                    }
                })
                .doOnLifecycle(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Logger.e("doOnLifecycle: ");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Logger.e("收到消息 : " + s);
                    }
                });

    }

    public void testRxLifecycle() {
        Observable.interval(2, TimeUnit.SECONDS)
                .compose(RxUtil.rxSchedulerHelper())
                .as(bindLifecycle())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (d.isDisposed()) {
                            Logger.e("disposed");
                        }
                    }

                    @Override
                    public void onNext(Long value) {
                        Logger.e(value + "");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Logger.e("onComplete");
                    }
                });
    }

}
