package com.dunkeng.news.presenter;

import com.dunkeng.news.contract.NewsContract;

/**
 * Created by Damon.Han on 2016/12/5 0005.
 *
 * @author Damon
 */

public class NewsPresenterMain extends NewsContract.PresenterNewsMain {

    @Override
    public void getTabList() {
        mView.showTabList(mModel.getTabs());
    }


    @Override
    public void onStart() {
        getTabList();
    }
}
