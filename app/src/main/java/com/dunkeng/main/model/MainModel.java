package com.dunkeng.main.model;

import com.dunkeng.common.Config;
import com.dunkeng.common.api.TianxingApi;
import com.dunkeng.main.contract.MainContract;
import com.oklib.utils.assist.DateUtil;
import com.oklib.utils.helper.RxUtil;
import com.oklib.utils.network.http.ViseHttp;

import io.reactivex.Observable;


/**
 * Created by Damon.Han on 2017/1/12 0012.
 *
 * @author Damon
 */

public class MainModel implements MainContract.Model {

    @Override
    public Observable<Lunar> getLunarData() {
        String dateStr = DateUtil.getDateYYYYMDD(System.currentTimeMillis());
        Observable<Lunar> observable = ViseHttp.RETROFIT().baseUrl(Config.BASE_URL_TIANXING)
                .create(TianxingApi.class).getLunar(Config.API_KEY_TIANXING, dateStr);
        return observable.compose(RxUtil.<Lunar>rxSchedulerHelper());
    }
}
