package com.oklib.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.CallSuper;
import android.support.annotation.MainThread;

import com.oklib.utils.RxLifecycleUtils;
import com.uber.autodispose.AutoDisposeConverter;

import org.jetbrains.annotations.NotNull;


/**
 * @author Damon
 */

public abstract class CoreBasePresenter<M, V> implements IPresenter {
    public M mModel;
    public V mView;

    public void attachVM(V v, M m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public void detachVM() {
        mView = null;
        mModel = null;
    }

    public abstract void onStart();


    private LifecycleOwner lifecycleOwner;

    protected <T> AutoDisposeConverter<T> bindLifecycle() {
        if (null == lifecycleOwner) {
            throw new NullPointerException("lifecycleOwner == null");
        }
        return RxLifecycleUtils.bindLifecycle(lifecycleOwner);
    }

    @Override
    @CallSuper
    @MainThread
    public void onLifecycleChanged(@NotNull LifecycleOwner owner, @NotNull Lifecycle.Event event) {

    }

    @Override
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    @Override
    @CallSuper
    @MainThread
    public void onCreate(@NotNull LifecycleOwner owner) {

    }

    @Override
    @CallSuper
    @MainThread
    public void onStart(@NotNull LifecycleOwner owner) {

    }

    @Override
    @CallSuper
    @MainThread
    public void onResume(@NotNull LifecycleOwner owner) {

    }

    @Override
    @CallSuper
    @MainThread
    public void onPause(@NotNull LifecycleOwner owner) {

    }

    @Override
    @CallSuper
    @MainThread
    public void onStop(@NotNull LifecycleOwner owner) {

    }

    @Override
    @CallSuper
    @MainThread
    public void onDestroy(@NotNull LifecycleOwner owner) {
    }

}
