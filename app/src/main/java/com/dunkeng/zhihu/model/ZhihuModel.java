package com.dunkeng.zhihu.model;

import com.dunkeng.App;
import com.dunkeng.Config;
import com.dunkeng.api.ZhiHuApi;
import com.dunkeng.zhihu.contract.ZhihuContract;
import com.oklib.utils.helper.RxUtil;
import com.oklib.utils.network.NetWorker;

import rx.Observable;

/**
 * Created by Damon.Han on 2016/12/5 0005.
 *
 * @author Damon
 */

public class ZhihuModel implements ZhihuContract.Model {

    private NetWorker netWorker = new NetWorker.Builder(App.getAppContext())
            .baseUrl(Config.BASE_URL_ZHIHU)
            .build();

    @Override
    public Observable<DailyListBean> getDailyData() {
        return netWorker.create(ZhiHuApi.class).getDailyList().compose(RxUtil.<DailyListBean>rxSchedulerHelper());
    }

    @Override
    public Observable<ZhihuDetailBean> getZhihuDetails(int anInt) {
        return netWorker.create(ZhiHuApi.class).getDetailInfo(anInt).compose(RxUtil.rxSchedulerHelper());
    }
}
