package com.dunkeng.news.presenter;

import com.dunkeng.news.contract.NewsContract;
import com.dunkeng.news.model.News;

import io.reactivex.functions.Consumer;


/**
 * Created by Damon.Han on 2016/12/5 0005.
 *
 * @author Damon
 */

public class NewsPresenter extends NewsContract.PresenterNews {


    @Override
    public void getNewsData(String type, int num) {
         mModel.getNewsData(type, num)
                .subscribe(
                        new Consumer<News>() {
                            @Override
                            public void accept(News news) throws Exception {
                                mView.showContent(news);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                mView.showMsg("数据加载失败");
                            }
                        }) ;
    }

    @Override
    public void onStart() {

    }
}
