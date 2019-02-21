package com.dunkeng.main.presenter;

import com.dunkeng.main.contract.MainContract;
import com.dunkeng.main.model.Lunar;
import com.dunkeng.main.model.MainModel;
import com.oklib.base.CoreBasePresenter;

import io.reactivex.functions.Consumer;

/**
 * Created by Damon.Han on 2019/2/20 0020.
 *
 * @author Damon
 */
public class MainPresenter extends CoreBasePresenter<MainModel, MainContract.MainView> {
    @Override
    public void onStart() {

    }

    public void getLunarData() {
        mModel.getLunarData()
                .subscribe(
                        new Consumer<Lunar>() {
                            @Override
                            public void accept(Lunar lunar) throws Exception {
                                mView.showLunar(lunar);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                mView.showMsg("数据加载失败");
                            }
                        });
    }

}
