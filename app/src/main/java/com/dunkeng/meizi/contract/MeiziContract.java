package com.dunkeng.meizi.contract;

import com.oklib.base.CoreBaseModel;
import com.oklib.base.CoreBasePresenter;
import com.oklib.base.CoreBaseView;

/**
 * Created by Damon.Han on 2016/12/2 0002.
 *
 * @author Damon
 */

public interface MeiziContract {

    interface View extends CoreBaseView {
    }

    interface Model extends CoreBaseModel {

    }

    abstract class Presenter extends CoreBasePresenter<Model, View> {
    }


}