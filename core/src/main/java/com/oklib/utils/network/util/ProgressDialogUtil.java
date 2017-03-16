package com.oklib.utils.network.util;

import android.content.Context;

import com.oklib.CoreConstants;

/**
 * Created by Damon.Han on 2017/3/15 0015.
 *
 * @author Damon
 */

public class ProgressDialogUtil {
    private Context mContext;
    private ProgressDialogHandler mHandler;

    public ProgressDialogUtil(Context mContext) {
        this.mContext = mContext;
        this.mHandler = new ProgressDialogHandler(mContext, CoreConstants.PROGRESS_DIALOG_CANCANCEL);
    }

    public void showProgressDialog() {
        if (this.mHandler!=null){
            mHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }
    public void dismissProgressDialog(){
        if (this.mHandler!=null){
            mHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
        }
    }
}
