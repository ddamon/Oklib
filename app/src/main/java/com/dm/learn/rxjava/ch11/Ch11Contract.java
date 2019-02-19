package com.dm.learn.rxjava.ch11;

import com.dm.learn.rxjava.ch2.model.ToolObj;
import com.oklib.base.CoreBaseModel;
import com.oklib.base.CoreBaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Damon.Han on 2019/2/19 0019.
 *
 * @author Damon
 */
public interface Ch11Contract {
    interface View extends CoreBaseView {
    }

    interface Model extends CoreBaseModel {
        Observable<List<ToolObj>> getData();
    }
}
