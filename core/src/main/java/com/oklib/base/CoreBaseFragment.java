package com.oklib.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.oklib.utils.StatusBarUtil;
import com.oklib.utils.TUtil;
import com.oklib.utils.ToastUtils;
import com.oklib.utils.logger.Logger;

import me.yokeyword.fragmentation.SupportFragment;

/**
 *
 */


public abstract class CoreBaseFragment<P extends CoreBasePresenter, M extends CoreBaseModel> extends SupportFragment {
    protected String TAG;

    public P mPresenter;
    public M mModel;
    protected Context mContext;
    protected Activity mActivity;
    protected View mainView;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutView() != null) {
            mainView = getLayoutView();
        } else {
            mainView = inflater.inflate(getLayoutId(), container, false);
        }
        initUI(mainView, savedInstanceState);
        return mainView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TAG = getClass().getSimpleName();
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof CoreBaseView) {
            mPresenter.attachVM(this, mModel);
        }
        getBundle(getArguments());
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachVM();
        }
    }


    public abstract int getLayoutId();

    public View getLayoutView() {
        return null;
    }

    /**
     * 得到Activity传进来的值
     */
    public void getBundle(Bundle bundle) {

    }

    /**
     * 初始化控件
     */
    public abstract void initUI(View view, @Nullable Bundle savedInstanceState);

    /**
     * 在监听器之前把数据准备好
     */
    public void initData() {

    }

    public void setStatusBarColor() {
        StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(), null);
    }

    protected void setToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(com.oklib.R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    public void showToast(String msg) {
        ToastUtils.showToast(mContext, msg, Toast.LENGTH_SHORT);
    }

    public void showLog(String msg) {
        Logger.i(TAG, msg);
    }


}
