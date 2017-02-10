// (c)2016 Flipboard Inc, All Rights Reserved.

package com.dunkeng.meizi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MeiziResult {
    public boolean error;
    public @SerializedName("results")
    List<Meizi> beauties;
}
