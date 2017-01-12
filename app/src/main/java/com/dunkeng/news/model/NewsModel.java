package com.dunkeng.news.model;

import com.dunkeng.App;
import com.dunkeng.Config;
import com.dunkeng.api.TianxingApi;
import com.dunkeng.news.contract.NewsContract;
import com.oklib.utils.helper.RxUtil;
import com.oklib.utils.network.NetWorker;

import rx.Observable;

/**
 * Created by Damon.Han on 2017/1/12 0012.
 *
 * @author Damon
 */

public class NewsModel implements NewsContract.Model {
    private NetWorker netWorker = new NetWorker.Builder(App.getAppContext())
            .baseUrl(Config.BASE_URL_TIANXING)
            .build();

    @Override
    public Observable<News> getNewsData(int page) {
        return netWorker.create(TianxingApi.class).getSocialNews(Config.API_KEY_TIANXING, page).compose(RxUtil.<News>rxSchedulerHelper());
    }
}
