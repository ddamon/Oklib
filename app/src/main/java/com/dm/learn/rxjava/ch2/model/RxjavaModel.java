package com.dm.learn.rxjava.ch2.model;

import com.dm.learn.rxjava.ch2.contract.RxjavaContract;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Damon.Han on 2017/3/29 0029.
 *
 * @author Damon
 */

public class RxjavaModel implements RxjavaContract.Model {
    @Override
    public Observable<List<ToolObj>> getData() {
        return null;
    }
}
