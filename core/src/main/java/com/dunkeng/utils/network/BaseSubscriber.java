package com.dunkeng.utils.network;

import android.content.Context;
import android.util.Log;

import com.dunkeng.utils.network.Exception.NovateException;

import rx.Subscriber;

/**
 * BaseSubscriber
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private Context context;

    public BaseSubscriber(Context context) {
        this.context = context;
    }

    @Override
    final public void onError(Throwable e) {
        Log.e("NetWorker", e.getMessage());
        if (e instanceof Throwable) {
            Log.e("NetWorker", "--> e instanceof MThrowable");
            onError((MThrowable) e);
        } else {
            Log.e("NetWorker", "e !instanceof MThrowable");
            onError(new MThrowable(e, NovateException.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("NetWorker", "-->http is start");
        // todo some common as show loadding  and check netWork is NetworkAvailable
        // if  NetworkAvailable no !   must to call onCompleted
    }

    @Override
    public void onCompleted() {
        Log.e("NetWorker", "-->http is Complete");
        // todo some common as  dismiss loadding
    }

    public abstract void onError(MThrowable e);

}
