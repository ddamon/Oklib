package com.dunkeng.zhihu.contract;

import com.dunkeng.zhihu.model.StoryItemBean;
import com.dunkeng.zhihu.model.ZhihuDetailBean;
import com.oklib.base.CoreBaseModel;
import com.oklib.base.CoreBasePresenter;
import com.oklib.base.CoreBaseView;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Damon.Han on 2016/12/5 0005.
 *
 * @author Damon
 */

public interface ZhihuContract {
    interface Model extends CoreBaseModel {
        /**
         * 获取知乎日报列表
         *
         * @return
         */
        Observable<List<StoryItemBean>> getDailyData();

        Observable<ZhihuDetailBean> getZhihuDetails(int anInt);
    }

    interface ZhihuView extends CoreBaseView {
        void showContent(List<StoryItemBean> info);
    }

    abstract class Presenter extends CoreBasePresenter<Model, ZhihuView> {
        public abstract void getDailyData();

    }

    abstract class AbsZhihuDetailsPresenter extends CoreBasePresenter<Model, ZhihuDetailsView> {
        public abstract void getZhihuDetails(int anInt);
    }

    interface ZhihuDetailsView extends CoreBaseView {
        void showContent(ZhihuDetailBean info);
    }
}
