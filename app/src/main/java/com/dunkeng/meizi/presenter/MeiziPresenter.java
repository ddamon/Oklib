package com.dunkeng.meizi.presenter;

import com.dunkeng.meizi.contract.MeiziContract;
import com.dunkeng.meizi.model.MeiziResult;

import rx.functions.Action1;

/**
 * Created by Damon.Han on 2017/2/10 0010.
 *
 * @author Damon
 */

public class MeiziPresenter extends MeiziContract.Presenter {
    @Override
    public void getMeizi(int page) {
        mRxManager.add(mModel.getMeizi(page).subscribe(
                new Action1<MeiziResult>() {
                    @Override
                    public void call(MeiziResult meiziResult) {
                        mView.showContent(meiziResult);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showMsg("数据加载失败");
                    }
                }));
    }

    @Override
    public void onStart() {

    }
}
