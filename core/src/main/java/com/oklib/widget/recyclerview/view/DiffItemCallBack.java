package com.oklib.widget.recyclerview.view;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

public class DiffItemCallBack<T> extends DiffUtil.ItemCallback<T> {

    @Override
    public boolean areItemsTheSame(@NonNull T t, @NonNull T t1) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(@NonNull T t, @NonNull T t1) {
        return false;
    }
}
