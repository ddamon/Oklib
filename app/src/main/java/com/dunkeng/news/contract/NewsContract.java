package com.dunkeng.news.contract;

import com.dunkeng.news.model.News;
import com.oklib.base.CoreBaseModel;
import com.oklib.base.CoreBasePresenter;
import com.oklib.base.CoreBaseView;

import io.reactivex.Observable;


/**
 * Created by Damon.Han on 2017/1/11 0011.
 *
 * @author Damon
 */

public interface NewsContract {
    interface Model extends CoreBaseModel {
        Observable<News> getNewsData(String type, int num);

        String[] getTabs();
    }

    ///////////////新闻列表页view//////////////
    interface ViewNews extends CoreBaseView {
        void showContent(News info);
    }


    abstract class PresenterNews extends CoreBasePresenter<NewsContract.Model, ViewNews> {
        public abstract void getNewsData(String type, int num);
    }

    ///////////////新闻主界面view//////////////
    interface ViewNewsMain extends CoreBaseView {

        void showTabList(String[] mTabs);
    }

    abstract class PresenterNewsMain extends CoreBasePresenter<NewsContract.Model, ViewNewsMain> {
        public abstract void getTabList();
    }

    ///////////////新闻详情页view//////////////
    interface ViewNewsDetail extends CoreBaseView {
        void showContent();
    }

    abstract class PresenterNewsDetail extends CoreBasePresenter<NewsContract.Model, ViewNewsDetail> {
        public abstract void getDetail();
    }

}