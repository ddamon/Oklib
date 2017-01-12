package com.dunkeng.news.contract;

import com.dunkeng.news.model.News;
import com.oklib.base.CoreBaseModel;
import com.oklib.base.CoreBasePresenter;
import com.oklib.base.CoreBaseView;

import rx.Observable;

/**
 * Created by Damon.Han on 2017/1/11 0011.
 *
 * @author Damon
 */

public interface NewsContract {
    interface Model extends CoreBaseModel {
        Observable<News> getNewsData(int num);
    }

    interface View extends CoreBaseView {
        void showContent(News info);

        void doInterval(int i);
    }

    abstract class Presenter extends CoreBasePresenter<NewsContract.Model, NewsContract.View> {
        public abstract void getNewsData(int page);

        public abstract void startInterval();
    }
}
