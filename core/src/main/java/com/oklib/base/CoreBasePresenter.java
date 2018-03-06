package com.oklib.base;

import com.oklib.utils.rxmanager.RxManager;

/**
 * @author Damon
 */

public abstract class CoreBasePresenter<M, V> {
    public M mModel;
    public V mView;
    public RxManager mRxManager = new RxManager();

    public void attachVM(V v, M m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public void detachVM() {
        mRxManager.clear();
        mView = null;
        mModel = null;
    }

    public abstract void onStart();
}
