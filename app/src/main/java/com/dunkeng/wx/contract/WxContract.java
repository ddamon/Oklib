package com.dunkeng.wx.contract;

import com.dunkeng.wx.model.Wx;
import com.oklib.base.CoreBaseModel;
import com.oklib.base.CoreBasePresenter;
import com.oklib.base.CoreBaseView;

import io.reactivex.Observable;

/**
 * Created by Damon on 2018/3/6.
 */

public interface WxContract {
    interface Model extends CoreBaseModel {
        Observable<Wx> getNewsData(String type, int num);

    }

    interface WxView extends CoreBaseView {
        void showContent(Wx info);
    }


    abstract class PresenterWx extends CoreBasePresenter<com.dunkeng.wx.contract.WxContract.Model, WxView> {
        public abstract void getNewsData(String type, int num);
    }

}
