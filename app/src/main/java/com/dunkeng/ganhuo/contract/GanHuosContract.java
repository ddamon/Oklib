package com.dunkeng.ganhuo.contract;

import com.dunkeng.ganhuo.model.GanHuos;
import com.oklib.base.CoreBaseModel;
import com.oklib.base.CoreBaseView;

import io.reactivex.Observable;


/**
 * Created by Damon.Han on 2017/1/11 0011.
 *
 * @author Damon
 */

public interface GanHuosContract {
    interface Model extends CoreBaseModel {
        Observable<GanHuos> getGanHuosData(String type, int num);
    }

    interface ViewGanHuos extends CoreBaseView {
        void showContent(GanHuos info);

        void showTabList(String[] mTabs);
    }


}