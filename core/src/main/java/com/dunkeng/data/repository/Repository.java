package com.dunkeng.data.repository;

import android.database.Observable;

import com.dunkeng.base.CoreBaseRepository;

import java.util.Map;

/**
 * .
 */
public abstract class Repository<T> extends CoreBaseRepository {
    public T data;

    public Map<String, String> param;

    public abstract Observable<Data<T>> getPageAt(int page);
}
