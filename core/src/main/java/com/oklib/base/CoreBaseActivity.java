package com.oklib.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oklib.AppManager;
import com.oklib.CoreApp;
import com.oklib.R;
import com.oklib.utils.StatusBarUtil;
import com.oklib.utils.TUtil;
import com.oklib.utils.ThemeUtil;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Base Activity
 * @author Damon
 */

public abstract class CoreBaseActivity<P extends CoreBasePresenter, M extends CoreBaseModel> extends SupportActivity {

    public P mPresenter;
    public M mModel;

    protected Context mContext;
    protected String TAG;
    private ImageView ivShadow;

    protected RelativeLayout container;
    protected FrameLayout content;
    protected Toolbar toolbar;
    private TextView mTitleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        TAG = getClass().getSimpleName();
        setTheme(ThemeUtil.themeArr[CoreApp.getThemeIndex(this)][
                CoreApp.getNightModel(this) ? 1 : 0]);
        this.setContentView(this.getLayoutId());
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof CoreBaseView && mPresenter != null && mModel != null) {
            mPresenter.attachVM(this, mModel);
        }
        this.initView(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        if (mPresenter != null) {
            mPresenter.detachVM();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_basetoolbar);
        toolbar = (Toolbar) findViewById(R.id.core_toolbar);
        mTitleView = (TextView) findViewById(R.id.core_title);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        content = (FrameLayout) findViewById(R.id.core_content);
        toolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
        content.removeAllViews();
        getLayoutInflater().inflate(layoutResID, content);
    }

    @Override
    public final void setContentView(View view) {
    }

    @Override
    public final void setContentView(View view, ViewGroup.LayoutParams params) {
    }

    /**
     * @return
     */
    public abstract int getLayoutId();

    public abstract void initView(Bundle savedInstanceState);

    protected void hideToolbar() {
        getSupportActionBar().hide();
    }

    /**
     * change back icon
     *
     * @param id
     */
    protected void setNaviIcon(int id) {
        toolbar.setNavigationIcon(id);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitleView.setVisibility(View.VISIBLE);
        mTitleView.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        mTitleView.setVisibility(View.VISIBLE);
        mTitleView.setText(titleId);
    }

    public String getBaseTitle() {
        return mTitleView.getText().toString();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    public void setStatusBarColor() {
        StatusBarUtil.setTransparent(this);
    }

    public String getStr(@StringRes int resId) {
        return getResources().getString(resId);
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

}
