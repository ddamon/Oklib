package com.dunkeng.news.presenter;

import com.dunkeng.App;
import com.dunkeng.news.contract.DailyContract;
import com.dunkeng.news.model.DailyListBean;
import com.oklib.utils.network.BaseSubscriber;
import com.oklib.utils.network.MThrowable;
import com.oklib.widget.imageloader.ImageLoaderUtil;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Damon.Han on 2016/12/5 0005.
 *
 * @author Damon
 */

public class DailyPresenter extends DailyContract.Presenter {
    @Override
    public void getDailyData() {
        mRxManager.add(mModel.getDailyData()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DailyListBean>() {
                    @Override
                    public void call(DailyListBean dailyListBean) {
                        mView.showContent(dailyListBean);
                    }
                }));
    }

    @Override
    public void startInterval() {

    }

    @Override
    public void onStart() {

    }
}
