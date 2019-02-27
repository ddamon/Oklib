package com.dunkeng.ganhuo.presenter;

import com.dunkeng.R;
import com.dunkeng.ganhuo.contract.GanHuosContract;
import com.oklib.base.CoreBasePresenter;

/**
 * Created by Damon.Han on 2016/12/5 0005.
 *
 * @author Damon
 */

public class GanHuosPresenterMain extends CoreBasePresenter<GanHuosContract.Model, GanHuosContract.ViewGanHuos> {

    public void getTabList() {
        String[] tabs = mView.getContext().getResources().getStringArray(R.array.ganhuo_tab);
        mView.showTabList(tabs);
    }


    @Override
    public void onStart() {
        getTabList();
    }
}
