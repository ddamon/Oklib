package com.dunkeng.zhihu.presenter;

import com.dunkeng.zhihu.contract.ZhihuContract;


/**
 * Created by Damon.Han on 2016/12/5 0005.
 *
 * @author Damon
 */

public class ZhihuPresenter extends ZhihuContract.Presenter {
    @Override
    public void getDailyData() {
        mModel.getDailyData()
                .subscribe(bean -> mView.showContent(bean),
                        e -> mView.showMsg("数据加载失败"));
    }


    @Override
    public void onStart() {

    }
}
