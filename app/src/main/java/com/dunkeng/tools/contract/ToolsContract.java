package com.dunkeng.tools.contract;

import com.dunkeng.tools.model.ToolObj;
import com.oklib.base.CoreBaseModel;
import com.oklib.base.CoreBasePresenter;
import com.oklib.base.CoreBaseView;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Damon.Han on 2017/3/29 0029.
 *
 * @author Damon
 */

public interface ToolsContract {

    interface View extends CoreBaseView {
        void showContent(List<ToolObj> result);
    }

    interface Model extends CoreBaseModel {
        Observable<List<ToolObj>> getData(int page);
    }

    abstract class Presenter extends CoreBasePresenter<Model, View> {
        public abstract void getData(int page);
    }
}
