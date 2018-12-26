package com.oklib.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.oklib.AppManager;
import com.oklib.CoreApp;
import com.oklib.R;
import com.oklib.base.swipeback.SwipeBackLayout;
import com.oklib.utils.TUtil;
import com.oklib.utils.view.ThemeUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Base Activity
 *
 * @author Damon
 */

public abstract class CoreBaseActivity<P extends CoreBasePresenter, M extends CoreBaseModel> extends SupportActivity implements IBaseActivity {
    protected String TAG;

    public P mPresenter;
    public M mModel;
    protected Context mContext;
    Unbinder binder;

    private SwipeBackLayout swipeBackLayout;
    private ImageView ivShadow;

    private boolean swipeBackEnable = false;

    final BaseActivityDelegate baseActivityDelegate = new BaseActivityDelegate(this);

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
        binder = ButterKnife.bind(this);
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof CoreBaseView) {
            mPresenter.attachVM(this, mModel);
        }
        this.initUI(savedInstanceState);
        initData();
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        if (binder != null) {
            binder.unbind();
        }
        if (mPresenter != null) {
            mPresenter.detachVM();
        }
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
        if (isSwipeBackEnable()) {
            super.setContentView(getContainer());
            View view = LayoutInflater.from(this).inflate(layoutResID, null);
            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            swipeBackLayout.addView(view);
        } else {
            super.setContentView(layoutResID);
        }
    }

    private View getContainer() {
        RelativeLayout container = new RelativeLayout(this);
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

    public abstract void initUI(Bundle savedInstanceState);
    /**
     * 在监听器之前把数据准备好
     */
    public void initData() {

    }
    @Override
    public void onBackPressedSupport() {
        supportFinishAfterTransition();
    }


    protected void setToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_36dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedSupport();
            }
        });
    }


    /**
     * 跳转页面,无extra简易型
     *
     * @param tarActivity 目标页面
     */
    public void startActivity(Class<? extends Activity> tarActivity, Bundle options) {
        Intent intent = new Intent(this, tarActivity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, options);
        } else {
            startActivity(intent);
        }
    }

    public void startActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }

    @Override
    public void showToast(String msg) {
        baseActivityDelegate.showToast(msg);
    }

    @Override
    public void showLog(String string) {
        baseActivityDelegate.showToast(string);
    }

    public void setSwipeBackEnable(boolean swipeBackEnable) {
        this.swipeBackEnable = swipeBackEnable;
    }

    public boolean isSwipeBackEnable() {
        return swipeBackEnable;
    }
}
