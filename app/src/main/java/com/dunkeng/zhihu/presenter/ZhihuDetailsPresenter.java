package com.dunkeng.zhihu.presenter;


import com.dunkeng.zhihu.contract.ZhihuContract;


public class ZhihuDetailsPresenter extends ZhihuContract.AbsZhihuDetailsPresenter {
    @Override
    public void getZhihuDetails(int anInt) {
        mRxManager.add(mModel
                .getZhihuDetails(anInt)
                .subscribe(
                        bean -> mView.showContent(bean),
                        e -> mView.showError("数据加载失败")
                ));
    }

    @Override
    public void onStart() {

    }
}
