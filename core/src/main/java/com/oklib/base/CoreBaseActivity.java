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
import com.oklib.widget.SwipeBackLayout;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Base Activity
 */

public abstract class CoreBaseActivity<P extends CoreBasePresenter, M extends CoreBaseModel> extends SupportActivity {

    protected String TAG;

    public P mPresenter;
    public M mModel;
    protected Context mContext;

    private SwipeBackLayout swipeBackLayout;
    private ImageView ivShadow;
    public boolean isSwapBackOpen = false;

    protected RelativeLayout container;
    protected FrameLayout content;
    protected Toolbar toolbar;
    private TextView mTitleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏透明
//        setStatusBarColor();
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
        if (this instanceof CoreBaseView) {
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

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
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
        if (isSwapBackOpen) {
            super.setContentView(getContainer());
            swipeBackLayout.addView(container);
        } else {
            content.removeAllViews();
            getLayoutInflater().inflate(layoutResID, content);
        }
    }

    @Override
    public final void setContentView(View view) {
    }

    @Override
    public final void setContentView(View view, ViewGroup.LayoutParams params) {
    }

    private View getContainer() {
        swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        ivShadow = new ImageView(this);
        ivShadow.setBackgroundColor(getResources().getColor(R.color.theme_black_7f));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        container.addView(ivShadow, params);
        container.addView(swipeBackLayout);
        swipeBackLayout.setOnSwipeBackListener((fa, fs) -> ivShadow.setAlpha(1 - fs));
        return container;
    }

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
//        return new DefaultNoAnimator();
        // return new FragmentAnimator(enter,exit,popEnter,popExit);
//        return super.onCreateFragmentAnimator();
    }

    public void setStatusBarColor() {
        StatusBarUtil.setTransparent(this);
//        StatusBarUtil.setTranslucent(this);
    }

    public String getStr(@StringRes int resId) {
        return getResources().getString(resId);
    }

}
