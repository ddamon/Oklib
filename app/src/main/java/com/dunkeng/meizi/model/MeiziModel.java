package com.dunkeng.meizi.model;

import com.dunkeng.App;
import com.dunkeng.common.Config;
import com.dunkeng.common.api.GankApi;
import com.dunkeng.meizi.contract.MeiziContract;
import com.oklib.utils.helper.RxUtil;
import com.oklib.utils.network.NetWorker;

import io.reactivex.Observable;


/**
 * Created by Damon on 2016/12/02
 */

public class MeiziModel implements MeiziContract.Model {
    NetWorker netWorker = new NetWorker.Builder(App.getAppContext()).baseUrl(Config.BASE_URL_GANK).build();

    @Override
    public Observable<MeiziResult> getMeizi(int page) {
        return netWorker.create(GankApi.class).getMeizi(Config.Data.pageSize, page).compose(RxUtil.rxSchedulerHelper());
    }
}