package com.oklib.base;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import org.jetbrains.annotations.NotNull;


/**
 * IPresenter-带生命周期管理
 */
public interface IPresenter extends LifecycleObserver {

    void setLifecycleOwner(LifecycleOwner lifecycleOwner);

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(@NotNull LifecycleOwner owner);

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart(@NotNull LifecycleOwner owner);

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume(@NotNull LifecycleOwner owner);

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause(@NotNull LifecycleOwner owner);

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop(@NotNull LifecycleOwner owner);

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(@NotNull LifecycleOwner owner);

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    void onLifecycleChanged(@NotNull LifecycleOwner owner,
                            @NotNull Lifecycle.Event event);
}
