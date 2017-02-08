package com.dunkeng.zhihu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.dunkeng.OnFragmentOpenDrawerListener;
import com.dunkeng.R;
import com.dunkeng.zhihu.contract.DailyContract;
import com.dunkeng.zhihu.model.DailyListBean;
import com.dunkeng.zhihu.model.DailyModel;
import com.dunkeng.zhihu.presenter.DailyPresenter;
import com.oklib.base.CoreBaseFragment;
import com.oklib.utils.ToastUtils;
import com.oklib.widget.imageloader.ImageLoader;
import com.oklib.widget.imageloader.ImageLoaderUtil;
import com.oklib.widget.recyclerview.BaseQuickAdapter;
import com.oklib.widget.recyclerview.BaseViewHolder;
import com.oklib.widget.recyclerview.CoreRecyclerView;

import butterknife.BindView;

/**
 * Created by Damon on 2016/12/7.
 * 知乎日报
 */

public class FragmentDialy extends CoreBaseFragment<DailyPresenter, DailyModel> implements DailyContract.View {
    @BindView(R.id.recycler)
    CoreRecyclerView coreRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    protected OnFragmentOpenDrawerListener mOpenDraweListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentOpenDrawerListener) {
            mOpenDraweListener = (OnFragmentOpenDrawerListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOpenDraweListener = null;
    }

    @Override
    public void initData() {
        mPresenter.getDailyData();
    }

    @Override
    public void showContent(DailyListBean info) {
        coreRecyclerView.getAdapter().addData(info.getStories());
    }

    @Override
    public void doInterval(int i) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_dialy;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        toolbar.setTitle("知乎");
        toolbar.setNavigationIcon(R.mipmap.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(v -> {
            if (mOpenDraweListener != null) {
                mOpenDraweListener.onOpenDrawer();
            }
        });
        coreRecyclerView.init(new BaseQuickAdapter<DailyListBean.StoriesBean, BaseViewHolder>(R.layout.item_daily) {
            @Override
            protected void convert(BaseViewHolder helper, DailyListBean.StoriesBean item) {
                helper.setText(R.id.tv_daily_item_title, item.getTitle());
                ImageLoaderUtil.getInstance().loadImage(mContext,
                        new ImageLoader.Builder()
                                .imgView((ImageView) helper.getView(R.id.iv_daily_item_image))
                                .url(item.getImages().get(0)).build());
            }
        });
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showToast(mContext, msg);
    }
}
