package com.dunkeng.zhihu.contract;

import com.dunkeng.zhihu.model.DailyListBean;
import com.oklib.base.CoreBaseModel;
import com.oklib.base.CoreBasePresenter;
import com.oklib.base.CoreBaseView;

import rx.Observable;

/**
 * Created by Damon.Han on 2016/12/5 0005.
 *
 * @author Damon
 */

public interface DailyContract {
    interface Model extends CoreBaseModel {
        Observable<DailyListBean> getDailyData();
    }

    interface View extends CoreBaseView {
        void showContent(DailyListBean info);

        void doInterval(int i);
    }

    abstract class Presenter extends CoreBasePresenter<Model, View> {
        public abstract void getDailyData();

        public abstract void startInterval();
    }
}
