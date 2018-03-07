package com.dunkeng.details.presenter;

import com.dunkeng.details.contract.DetailContract;

/**
 * Created by Damon.Han on 2018/3/7 0007.
 *
 * @author Damon
 */

public class DetailPresenter extends DetailContract.AbsDetailPresenter {

    @Override
    public void onStart() {

    }

    @Override
    public void getDetail() {
        mView.showContent();
    }
}
