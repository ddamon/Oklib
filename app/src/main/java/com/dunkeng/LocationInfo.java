package com.dunkeng;

import java.io.Serializable;

/**
 * 后台定位获取的信息
 *
 * @author Damon.Han
 */
public class LocationInfo implements Serializable {
    public double longitude;
    public double latitude;
    public long locationTime;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public long getLocationTime() {
        return locationTime;
    }

    public void setLocationTime(long locationTime) {
        this.locationTime = locationTime;
    }
}
