package com.dunkeng.news.presenter;

import com.dunkeng.news.contract.DailyContract;
import com.dunkeng.news.model.DailyListBean;

import rx.functions.Action1;

/**
 * Created by Damon.Han on 2016/12/5 0005.
 *
 * @author Damon
 */

public class DailyPresenter extends DailyContract.Presenter {
    @Override
    public void getDailyData() {
        mRxManager.add(mModel.getDailyData()
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
