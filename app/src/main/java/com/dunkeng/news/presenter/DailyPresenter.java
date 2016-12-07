package com.dunkeng.news.presenter;

import com.dunkeng.App;
import com.dunkeng.news.contract.DailyContract;
import com.dunkeng.news.model.DailyListBean;
import com.oklib.utils.network.BaseSubscriber;
import com.oklib.utils.network.MThrowable;
import com.oklib.widget.imageloader.ImageLoaderUtil;

/**
 * Created by Damon.Han on 2016/12/5 0005.
 *
 * @author Damon
 */

public class DailyPresenter extends DailyContract.Presenter {
    @Override
    public void getDailyData() {
        mRxManager.add(mModel.getData().subscribe(new BaseSubscriber<DailyListBean>(App.getAppContext()) {
            @Override
            public void onError(MThrowable e) {

            }

            @Override
            public void onNext(DailyListBean dailyListBean) {
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
