package com.dunkeng.zhihu.presenter;

import com.dunkeng.zhihu.contract.ZhihuContract;
import com.dunkeng.zhihu.model.DailyListBean;

import rx.functions.Action1;

/**
 * Created by Damon.Han on 2016/12/5 0005.
 *
 * @author Damon
 */

public class ZhihuPresenter extends ZhihuContract.Presenter {
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
    public void onStart() {

    }
}
