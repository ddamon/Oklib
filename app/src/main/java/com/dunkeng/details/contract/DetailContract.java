package com.dunkeng.details.contract;

import com.oklib.base.CoreBaseModel;
import com.oklib.base.CoreBasePresenter;
import com.oklib.base.CoreBaseView;

/**
 * Created by Damon.Han on 2018/3/7 0007.
 *
 * @author Damon
 */

public interface DetailContract {
    interface Model extends CoreBaseModel {
    }

    abstract class AbsDetailPresenter extends CoreBasePresenter<Model, DetailView> {
        public abstract void getDetail();
    }

    interface DetailView extends CoreBaseView {
        void showContent();
    }
}
