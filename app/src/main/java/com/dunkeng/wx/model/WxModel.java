package com.dunkeng.wx.model;

import com.dunkeng.common.Config;
import com.dunkeng.common.api.TianxingApi;
import com.dunkeng.wx.contract.WxContract;
import com.oklib.utils.helper.RxUtil;
import com.oklib.utils.network.http.ViseHttp;

import io.reactivex.Observable;


/**
 * Created by Damon.Han on 2017/1/12 0012.
 *
 * @author Damon
 */

public class WxModel implements WxContract.Model {

    @Override
    public Observable<Wx> getNewsData(String type, int num) {
        Observable<Wx> observable = ViseHttp.RETROFIT().baseUrl(Config.BASE_URL_TIANXING)
                .create(TianxingApi.class).getWeixinNews(Config.API_KEY_TIANXING, num);
        return observable.compose(RxUtil.<Wx>rxSchedulerHelper());
    }
}
