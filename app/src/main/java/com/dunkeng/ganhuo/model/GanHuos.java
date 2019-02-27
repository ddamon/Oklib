package com.dunkeng.ganhuo.model;

import java.util.List;

/**
 * Created by Damon.Han on 2019/2/27 0027.
 *
 * @author Damon
 */
public class GanHuos {
    private boolean error;

    private List<GanHuoBean> results;

    public List<GanHuoBean> getResults() {
        return results;
    }

    public void setResults(List<GanHuoBean> results) {
        this.results = results;
    }


    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

}
