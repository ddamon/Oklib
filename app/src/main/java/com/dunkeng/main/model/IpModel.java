package com.dunkeng.main.model;

/**
 * Created by Damon.Han on 2019/2/25 0025.
 *
 * @author Damon
 */
public class IpModel {
    /**
     * code : 0
     * data : {"ip":"60.209.238.22","country":"中国","area":"","region":"山东","city":"青岛","county":"XX","isp":"联通","country_id":"CN","area_id":"","region_id":"370000","city_id":"370200","county_id":"xx","isp_id":"100026"}
     */

    private int code;
    private IpBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public IpBean getData() {
        return data;
    }

    public void setData(IpBean data) {
        this.data = data;
    }

}
