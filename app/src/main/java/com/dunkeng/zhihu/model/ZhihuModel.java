package com.dunkeng.zhihu.model;

import com.dunkeng.common.Config;
import com.dunkeng.common.api.ZhiHuApi;
import com.dunkeng.zhihu.contract.ZhihuContract;
import com.oklib.utils.helper.RxUtil;
import com.oklib.utils.network.http.ViseHttp;

import io.reactivex.Observable;


/**
 * Created by Damon.Han on 2016/12/5 0005.
 *
 * @author Damon
 */

public class ZhihuModel implements ZhihuContract.Model {


    @Override
    public Observable<DailyListBean> getDailyData() {
        return ViseHttp.RETROFIT().baseUrl(Config.BASE_URL_ZHIHU).create(ZhiHuApi.class).getDailyList().compose(RxUtil.<DailyListBean>rxSchedulerHelper());
    }

    @Override
    public Observable<ZhihuDetailBean> getZhihuDetails(int anInt) {
        return ViseHttp.RETROFIT().baseUrl(Config.BASE_URL_ZHIHU).create(ZhiHuApi.class).getDetailInfo(anInt).compose(RxUtil.rxSchedulerHelper());
    }
}
