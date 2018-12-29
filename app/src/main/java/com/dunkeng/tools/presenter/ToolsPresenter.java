package com.dunkeng.tools.presenter;

import com.dunkeng.tools.contract.ToolsContract;
import com.oklib.base.CoreBasePresenter;
import com.oklib.utils.helper.RxUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
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
        final int[] i = {1};
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                for (int j = 0; j < 1000; j++) {
                    Thread.sleep(2000);
                    i[0]++;
                    e.onNext(i[0] + "");
                }

            }
        }).compose(RxUtil.rxSchedulerHelper())
                .compose(RxLifecycleUtils.bindToLifecycle(mView))
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (d.isDisposed()) {
                            mView.showMsg("disposed");
                        }
                    }

                    @Override
                    public void onNext(String value) {
                        mView.showMsg(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showMsg(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mView.showMsg("onComplete");
                    }
                });
    }
}
