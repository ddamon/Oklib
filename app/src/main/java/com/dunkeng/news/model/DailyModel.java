package com.dunkeng.news.model;

import com.dunkeng.API.ZhiHuApi;
import com.dunkeng.App;
import com.dunkeng.Config;
import com.dunkeng.news.contract.DailyContract;
import com.oklib.data.net.RxService;
import com.oklib.utils.helper.RxUtil;
import com.oklib.utils.network.NetWorker;

import rx.Observable;

/**
 * Created by Damon.Han on 2016/12/5 0005.
 *
 * @author Damon
 */

public class DailyModel implements DailyContract.Model {
    @Override
    public Observable<DailyListBean> getDailyData() {
        NetWorker netWorker = new NetWorker.Builder(App.getAppContext())
                .baseUrl(Config.BASE_URL_ZHIHU)
                .build();
        return netWorker.create(ZhiHuApi.class).getDailyList();
//        return RxService.createApi(ZhiHuApi.class).getDailyList().compose(RxUtil.rxSchedulerHelper());
    }

}
