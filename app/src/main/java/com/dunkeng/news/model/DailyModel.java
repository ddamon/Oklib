package com.dunkeng.news.model;

import com.dunkeng.API.GankApi;
import com.dunkeng.App;
import com.dunkeng.news.contract.DailyContract;
import com.oklib.utils.network.NetWorker;

/**
 * Created by Damon.Han on 2016/12/5 0005.
 *
 * @author Damon
 */

public class DailyModel implements DailyContract.Model {
    @Override
    public void getData() {
        NetWorker netWorker = new NetWorker.Builder(App.getAppContext())
                .baseUrl("")
                .addLog(true)
                .build();
        netWorker.call(netWorker.create(GankApi.class).getBeauties(10, 1));
    }
}
