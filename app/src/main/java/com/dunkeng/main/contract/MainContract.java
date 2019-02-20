package com.dunkeng.main.contract;

import com.dunkeng.main.model.Lunar;
import com.oklib.base.CoreBaseModel;
import com.oklib.base.CoreBaseView;

import io.reactivex.Observable;

/**
 * Created by Damon on 2018/3/6.
 */

public interface MainContract {

    interface Model extends CoreBaseModel {
        Observable<Lunar> getLunarData();

    }

    interface MainView extends CoreBaseView {
        void showLunar(Lunar info);
    }
}
