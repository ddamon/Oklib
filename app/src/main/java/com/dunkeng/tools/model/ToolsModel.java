package com.dunkeng.tools.model;

import com.dunkeng.tools.contract.ToolsContract;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Damon.Han on 2017/3/29 0029.
 *
 * @author Damon
 */

public class ToolsModel implements ToolsContract.Model {
    @Override
    public Observable<List<ToolObj>> getData() {
        return null;
    }
}
