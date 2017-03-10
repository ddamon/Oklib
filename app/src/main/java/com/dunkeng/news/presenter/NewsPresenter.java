package com.dunkeng.news.presenter;

import com.dunkeng.news.contract.NewsContract;
import com.dunkeng.news.model.News;

import rx.functions.Action1;

/**
 * Created by Damon.Han on 2016/12/5 0005.
 *
 * @author Damon
 */

public class NewsPresenter extends NewsContract.PresenterNews {


    @Override
    public void getNewsData(String type,int num) {
        mRxManager.add(mModel.getNewsData(type,num)
                .subscribe(
                        new Action1<News>() {
                            @Override
                            public void call(News newsGson) {
                                mView.showContent(newsGson);
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
