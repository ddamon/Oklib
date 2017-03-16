package com.dunkeng.meizi.presenter;

import com.dunkeng.meizi.contract.MeiziContract;
import com.dunkeng.meizi.model.MeiziResult;
import com.oklib.utils.network.BaseSubscriber;
import com.oklib.utils.network.MThrowable;

/**
 * Created by Damon.Han on 2017/2/10 0010.
 *
 * @author Damon
 */

public class MeiziPresenter extends MeiziContract.Presenter {
    @Override
    public void getMeizi(int page) {
        mRxManager.add(mModel.getMeizi(page).subscribe(
                new BaseSubscriber<MeiziResult>(mView.getContext()) {
                    @Override
                    public void onError(MThrowable e) {
                        mView.showMsg("数据加载失败");
                    }

                    @Override
                    public void onNext(MeiziResult meiziResult) {
                        mView.showContent(meiziResult);
                    }
                }));
    }

    @Override
    public void onStart() {

    }
}
