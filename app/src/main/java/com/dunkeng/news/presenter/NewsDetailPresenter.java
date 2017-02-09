package com.dunkeng.news.presenter;

import com.dunkeng.news.contract.NewsContract;

/**
 * Created by Damon.Han on 2016/12/5 0005.
 *
 * @author Damon
 */

public class NewsDetailPresenter extends NewsContract.PresenterNewsDetail {


    @Override
    public void onStart() {

    }

    @Override
    public void getDetail() {
        mView.showContent();
    }
}
