package com.oklib.base;

import android.support.v4.app.FragmentActivity;

import com.oklib.utils.ToastUtils;
import com.orhanobut.logger.Logger;


/**
 * Created by Damon.Han on 2018/4/2 0002.
 *
 * @author Damon
 */
public class BaseActivityDelegate implements IBaseActivity {
    private FragmentActivity mActivity;

    public BaseActivityDelegate(IBaseActivity mActivity) {
        this.mActivity = (FragmentActivity) mActivity;
    }


    @Override
    public void showToast(String string) {
        ToastUtils.showToast(mActivity, string);
    }

    @Override
    public void showLog(String string) {
        Logger.e(string);
    }
}
