package com.oklib.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.CallSuper;
import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;

import com.oklib.R;
import com.oklib.base.swipeback.SwipeBackLayout;
import com.oklib.utils.RxLifecycleUtils;
import com.oklib.utils.TUtil;
import com.uber.autodispose.AutoDisposeConverter;

import org.jetbrains.annotations.NotNull;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Damon
 */


public abstract class CoreBaseFragment<P extends CoreBasePresenter, M extends CoreBaseModel> extends SupportFragment implements IBaseFragment {
    public P mPresenter;
    public M mModel;
    protected Context mContext;
    protected Activity mActivity;
    protected View mainView;
    protected String TAG;
    Unbinder binder;
    private SwipeBackLayout swipeBackLayout;
    private ImageView ivShadow;
    final BaseFragmentDelegate baseFragmentDelegate = new BaseFragmentDelegate(this);
    private boolean swipeBackEnable = false;


    protected <T> AutoDisposeConverter<T> bindLifecycle() {
        return RxLifecycleUtils.bindLifecycle(this);
    }

    @CallSuper
    @MainThread
    protected void initLifecycleObserver(@NotNull Lifecycle lifecycle) {
        if (mPresenter != null) {
            mPresenter.setLifecycleOwner(this);
            lifecycle.addObserver(mPresenter);
        }
    }

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setSwipeBackEnable(boolean swipeBackEnable) {
        this.swipeBackEnable = swipeBackEnable;
    }

    public boolean isSwipeBackEnable() {
        return swipeBackEnable;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (isSwipeBackEnable()) {
            mainView = getContainer();
            View childView = getLayoutView();
            if (childView == null) {
                childView = inflater.inflate(getLayoutId(), container, false);
                childView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
            swipeBackLayout.addView(childView);
        } else {
            mainView = getLayoutView();
            if (mainView == null) {
                mainView = inflater.inflate(getLayoutId(), container, false);
            }
        }
        binder = ButterKnife.bind(this, mainView);
        return mainView;
    }

    public View getLayoutView() {
        return null;
    }

    private View getContainer() {
        RelativeLayout container = new RelativeLayout(mContext);
        swipeBackLayout = new SwipeBackLayout(mContext);
        swipeBackLayout.setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        ivShadow = new ImageView(mContext);
        ivShadow.setBackgroundColor(getResources().getColor(R.color.theme_black_7f));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        container.addView(ivShadow, params);
        container.addView(swipeBackLayout);
        swipeBackLayout.setOnSwipeBackListener((fa, fs) -> ivShadow.setAlpha(1 - fs));
        return container;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TAG = getClass().getSimpleName();
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof CoreBaseView && mPresenter != null && mModel != null) {
            mPresenter.attachVM(this, mModel);
        }
        initLifecycleObserver(getLifecycle());
        this.initUI(mainView, savedInstanceState);
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (binder != null) {
            binder.unbind();
        }
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

    /**
     * 获取设置布局id
     *
     * @return
     */
    public abstract int getLayoutId();


    /**
     * 初始化控件
     */
    public abstract void initUI(View view, @Nullable Bundle savedInstanceState);

    /**
     * 在监听器之前把数据准备好
     */
    public void initData() {

    }


    @Override
    public void showToast(String string) {
        baseFragmentDelegate.showToast(string);
    }

    @Override
    public void showLog(String string) {
        baseFragmentDelegate.showLog(string);
    }
}
