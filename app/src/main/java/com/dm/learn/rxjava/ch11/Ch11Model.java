package com.dm.learn.rxjava.ch11;

import com.dm.learn.rxjava.ch2.contract.RxjavaContract;
import com.dm.learn.rxjava.ch2.model.ToolObj;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Damon.Han on 2017/3/29 0029.
 *
 * @author Damon
 */

public class Ch11Model implements RxjavaContract.Model {
    @Override
    public Observable<List<ToolObj>> getData() {
        return null;
    }
}
