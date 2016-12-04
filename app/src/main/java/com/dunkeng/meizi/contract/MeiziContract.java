package com.dunkeng.meizi.contract;

import com.dunkeng.base.CoreBaseModel;
import com.dunkeng.base.CoreBasePresenter;
import com.dunkeng.base.CoreBaseView;

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