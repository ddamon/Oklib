package com.dunkeng.zhihu.model;

import com.dunkeng.common.Config;
import com.dunkeng.common.api.ZhiHuApi;
import com.dunkeng.zhihu.contract.ZhihuContract;
import com.oklib.utils.DataValidation;
import com.oklib.utils.helper.RxUtil;
import com.oklib.utils.network.http.ViseHttp;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;


/**
 * Created by Damon.Han on 2016/12/5 0005.
 *
 * @author Damon
 */

public class ZhihuModel implements ZhihuContract.Model {


    @Override
    public Observable<List<StoryItemBean>> getDailyData() {
        return ViseHttp.RETROFIT().baseUrl(Config.BASE_URL_ZHIHU).create(ZhiHuApi.class).getDailyList().map(new Function<DailyListBean, List<StoryItemBean>>() {
            @Override
            public List<StoryItemBean> apply(DailyListBean dailyListBean) throws Exception {
                List<StoryItemBean> list = new ArrayList<>();
                List<DailyListBean.StoriesBean> storiesBeans = dailyListBean.getStories();
                List<StoryItemBean> storyItemBeans = dailyListBean.getTop_stories();
                if (!DataValidation.isEmpty(storiesBeans)) {
                    for (int i = 0; i < storiesBeans.size(); i++) {
                        DailyListBean.StoriesBean storiesBean = storiesBeans.get(i);
                        StoryItemBean storyItemBean = new StoryItemBean();
                        storyItemBean.setId(storiesBean.getId());
                        storyItemBean.setGa_prefix(storiesBean.getGa_prefix());
                        storyItemBean.setTitle(storiesBean.getTitle());
                        storyItemBean.setType(storiesBean.getType());
                        if (!DataValidation.isEmpty(storiesBean.getImages())) {
                            storyItemBean.setImage(storiesBean.getImages().get(0));
                        }
                        list.add(storyItemBean);
                    }
                }
                list.addAll(dailyListBean.getTop_stories());
                return list;
            }
        }).compose(RxUtil.<List<StoryItemBean>>rxSchedulerHelper());
    }

    @Override
    public Observable<ZhihuDetailBean> getZhihuDetails(int anInt) {
        return ViseHttp.RETROFIT().baseUrl(Config.BASE_URL_ZHIHU).create(ZhiHuApi.class).getDetailInfo(anInt).compose(RxUtil.rxSchedulerHelper());
    }
}
