package com.dunkeng.meizi.contract;

import com.dunkeng.meizi.model.MeiziResult;
import com.oklib.base.CoreBaseModel;
import com.oklib.base.CoreBasePresenter;
import com.oklib.base.CoreBaseView;

import io.reactivex.Observable;


/**
 * Created by Damon.Han on 2016/12/2 0002.
 *
 * @author Damon
 */

public interface MeiziContract {

    interface View extends CoreBaseView {
        void showContent(MeiziResult result);
    }

    interface Model extends CoreBaseModel {
        Observable<MeiziResult> getMeizi(int page);
    }

    abstract class Presenter extends CoreBasePresenter<Model, View> {
        public abstract void getMeizi(int page);
    }
}