package com.dunkeng.news.model;

import com.dunkeng.API.ZhiHuApi;
import com.dunkeng.App;
import com.dunkeng.Config;
import com.dunkeng.news.contract.DailyContract;
import com.oklib.utils.helper.RxUtil;
import com.oklib.utils.network.NetWorker;

import rx.Observable;

/**
 * Created by Damon.Han on 2016/12/5 0005.
 *
 * @author Damon
 */

public class DailyModel implements DailyContract.Model {

    NetWorker netWorker = new NetWorker.Builder(App.getAppContext())
            .baseUrl(Config.BASE_URL_ZHIHU)
            .build();

    @Override
    public Observable<DailyListBean> getDailyData() {
        return netWorker.create(ZhiHuApi.class).getDailyList().compose(RxUtil.<DailyListBean>rxSchedulerHelper());
    }

}
