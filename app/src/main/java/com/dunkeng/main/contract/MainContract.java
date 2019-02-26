package com.dunkeng.main.contract;

import com.dunkeng.main.model.IpModel;
import com.dunkeng.main.model.LunarModel;
import com.oklib.base.CoreBaseModel;
import com.oklib.base.CoreBaseView;
import com.oklib.utils.network.http.mode.CacheResult;

import io.reactivex.Observable;

/**
 * Created by Damon on 2018/3/6.
 */

public interface MainContract {

    interface Model extends CoreBaseModel {
        Observable<LunarModel> getLunarData();
        Observable<IpModel> getIpData();

    }

    interface MainView extends CoreBaseView {
        void showLunar(LunarModel info);
        void showIp(CacheResult<IpModel> ipModel);
    }
}
