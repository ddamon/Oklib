package com.dm.learn.rxjava.ch2.presenter;

import com.dm.learn.rxjava.ch2.contract.RxjavaContract;
import com.oklib.base.CoreBasePresenter;
import com.oklib.utils.Logger.Logger;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Created by Damon.Han on 2017/3/29 0029.
 *
 * @author Damon
 */

public class RxjavaPresenter3 extends CoreBasePresenter<RxjavaContract.Model, RxjavaContract.View> {

    private String testResult;

    @Override
    public void onStart() {

    }

    public void getData() {
        mView.showMsg("tool");
    }

    public void testDefer() {
        Observable<String> observable = Observable.defer(new Callable<ObservableSource<String>>() {
            @Override
            public ObservableSource<String> call() throws Exception {
                return Observable.just("Hello Defer!");
            }
        });
        observable.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                mView.showMsg(s);
            }
        });

    }

    /**
     * 测试多个订阅者数据直接是否共享
     */
    public void testObservableDataShare() {
        Observable<Long> observable = Observable.interval(1, TimeUnit.SECONDS).take(6);

        observable.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                Logger.i("Sub1" + aLong);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        observable.delaySubscription(3, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                Logger.e("Sub2" + aLong);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    /**
     * 在文本框显示结果
     *
     * @param msg
     */
    private void putResult(String msg) {
        mView.showMsg(msg);
    }

}
