package com.dunkeng.tools.presenter;

import com.dunkeng.tools.contract.ToolsContract;
import com.oklib.base.CoreBasePresenter;
import com.oklib.utils.Logger.Logger;
import com.oklib.utils.helper.RxUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by Damon.Han on 2017/3/29 0029.
 *
 * @author Damon
 */

public class ToolsPresenter extends CoreBasePresenter<ToolsContract.Model, ToolsContract.View> {
    @Override
    public void onStart() {

    }

    public void getData() {
        mView.showMsg("tool");
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
