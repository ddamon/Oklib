package com.dunkeng.ganhuo.model;

/**
 * Created by Damon.Han on 2019/2/27 0027.
 *
 * @author Damon
 */
public class XianDuCategory {
    /**
     * _id : 57c83777421aa97cbd81c74d
     * en_name : wow
     * name : 科技资讯
     * rank : 1
     */

    private String _id;
    private String en_name;
    private String name;
    private int rank;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
