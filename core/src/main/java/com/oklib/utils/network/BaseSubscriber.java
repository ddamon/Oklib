package com.oklib.utils.network;

import android.content.Context;
import android.widget.Toast;

import com.oklib.CoreConstants;
import com.oklib.utils.network.Exception.NetworkException;
import com.oklib.utils.network.util.NetworkUtil;
import com.oklib.utils.network.util.ProgressDialogUtil;
import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * BaseSubscriber
 */
public abstract class BaseSubscriber<T> implements Observer<T> {

    private Context context;
    private ProgressDialogUtil progressDialogUtil;

    public BaseSubscriber(Context context) {
        this.context = context;
        if (CoreConstants.PROGRESS_DIALOG_DISPALY) {
            progressDialogUtil = new ProgressDialogUtil(context);
        }
    }

    @Override
    final public void onError(Throwable e) {
        Logger.e("NetWorker", e.getMessage());
        progressDialogUtil.dismissProgressDialog();
        if (e instanceof MThrowable) {
            onError((MThrowable) e);
        } else {
            onError(new MThrowable(e, NetworkException.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        Logger.e("NetWorker", "-->http is start");
        // todo some common as show loadding  and check netWork is NetworkAvailable
        // if  NetworkAvailable no !   must to call onCompleted
        if (!NetworkUtil.isNetworkAvailable(context)) {
            Toast.makeText(context, "当前网络不可用，请检查网络情况", Toast.LENGTH_SHORT).show();
            onComplete();
            return;
        }
        if (progressDialogUtil != null) {
            progressDialogUtil.showProgressDialog();
        }

    }

    @Override
    public void onComplete() {
        Logger.e("NetWorker", "-->http is Complete");
        // todo some common as  dismiss loadding
        if (progressDialogUtil != null) {
            progressDialogUtil.dismissProgressDialog();
        }
    }

    public abstract void onError(MThrowable e);


}
