package com.dm.learn.rxjava.ch2.contract;

import com.dm.learn.rxjava.ch2.model.ToolObj;
import com.oklib.base.CoreBaseModel;
import com.oklib.base.CoreBaseView;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Damon.Han on 2017/3/29 0029.
 *
 * @author Damon
 */

public interface RxjavaContract {

    interface View extends CoreBaseView {
    }

    interface Model extends CoreBaseModel {
        Observable<List<ToolObj>> getData();
    }

}
