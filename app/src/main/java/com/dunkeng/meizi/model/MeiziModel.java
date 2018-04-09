package com.dunkeng.meizi.model;

import com.dunkeng.common.Config;
import com.dunkeng.common.api.GankApi;
import com.dunkeng.meizi.contract.MeiziContract;
import com.oklib.utils.helper.RxUtil;
import com.oklib.utils.network.http.ViseHttp;

import io.reactivex.Observable;


/**
 * Created by Damon on 2016/12/02
 */

public class MeiziModel implements MeiziContract.Model {

    @Override
    public Observable<MeiziResult> getMeizi(int page) {
        return ViseHttp.RETROFIT().baseUrl(Config.BASE_URL_GANK).create(GankApi.class).getMeizi(Config.Data.pageSize, page).compose(RxUtil.rxSchedulerHelper());
    }
}