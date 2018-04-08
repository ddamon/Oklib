package com.oklib.base;

import android.support.v4.app.Fragment;

import com.oklib.utils.Logger.Logger;
import com.oklib.utils.view.ToastUtils;


/**
 * Created by Damon.Han on 2018/4/2 0002.
 *
 * @author Damon
 */
public class BaseFragmentDelegate implements IBaseFragment {
    private Fragment fragment;

    public BaseFragmentDelegate(IBaseFragment fragment) {
        this.fragment = (Fragment) fragment;
    }


    @Override
    public void showToast(String string) {
        ToastUtils.showToast(fragment.getContext(), string);
    }

    @Override
    public void showLog(String string) {
        Logger.e(string);
    }
}
