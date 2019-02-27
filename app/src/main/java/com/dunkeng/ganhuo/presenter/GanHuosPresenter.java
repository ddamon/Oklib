package com.dunkeng.ganhuo.presenter;

import com.dunkeng.ganhuo.contract.GanHuosContract;
import com.dunkeng.ganhuo.model.GanHuos;
import com.oklib.base.CoreBasePresenter;

import io.reactivex.functions.Consumer;


/**
 * Created by Damon.Han on 2016/12/5 0005.
 *
 * @author Damon
 */

public class GanHuosPresenter extends CoreBasePresenter<GanHuosContract.Model, GanHuosContract.ViewGanHuos> {

    public void getGanHuosData(String type, int num) {
        mModel.getGanHuosData(type, num)
                .subscribe(
                        new Consumer<GanHuos>() {
                            @Override
                            public void accept(GanHuos news) throws Exception {
                                mView.showContent(news);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                mView.showMsg("数据加载失败");
                            }
                        });
    }

    @Override
    public void onStart() {

    }
}
