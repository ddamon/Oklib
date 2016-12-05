package com.dunkeng.news.model;

import com.dunkeng.news.contract.DailyContract;

import rx.Observable;

/**
 * Created by Damon.Han on 2016/12/5 0005.
 *
 * @author Damon
 */

public class DailyModel implements DailyContract.Model {
    @Override
    public Observable<DailyListBean> getData() {
        return null;
    }
}
