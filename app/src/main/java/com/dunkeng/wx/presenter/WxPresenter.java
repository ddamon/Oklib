package com.dunkeng.wx.presenter;

import com.dunkeng.wx.contract.WxContract;
import com.dunkeng.wx.model.Wx;

import io.reactivex.functions.Consumer;


/**
 * Created by Damon.Han on 2016/12/5 0005.
 *
 * @author Damon
 */

public class WxPresenter extends WxContract.PresenterWx {


    @Override
    public void getNewsData(String type, int num) {
        mModel.getNewsData(type, num)
                .subscribe(
                        new Consumer<Wx>() {
                            @Override
                            public void accept(Wx news) throws Exception {
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
