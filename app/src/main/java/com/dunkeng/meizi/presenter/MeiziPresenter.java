package com.dunkeng.meizi.presenter;

import com.dunkeng.meizi.contract.MeiziContract;

/**
 * Created by Damon.Han on 2017/2/10 0010.
 *
 * @author Damon
 */

public class MeiziPresenter extends MeiziContract.Presenter {
    @Override
    public void getMeizi(int page) {
        mModel.getMeizi(page).subscribe(bean -> mView.showContent(bean),
                e -> mView.showMsg("数据加载失败"));
    }


    @Override
    public void onStart() {

    }
}
