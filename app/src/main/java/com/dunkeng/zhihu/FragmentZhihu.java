package com.dunkeng.zhihu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.dunkeng.common.OnFragmentOpenDrawerListener;
import com.dunkeng.R;
import com.dunkeng.zhihu.contract.ZhihuContract;
import com.dunkeng.zhihu.model.DailyListBean;
import com.dunkeng.zhihu.model.ZhihuModel;
import com.dunkeng.zhihu.presenter.ZhihuPresenter;
import com.oklib.base.CoreBaseFragment;
import com.oklib.utils.view.ToastUtils;
import com.oklib.widget.imageloader.ImageLoader;
import com.oklib.widget.imageloader.ImageLoaderUtil;
import com.oklib.widget.recyclerview.BaseQuickAdapter;
import com.oklib.widget.recyclerview.BaseViewHolder;
import com.oklib.widget.recyclerview.CoreRecyclerView;
import com.oklib.widget.recyclerview.listener.OnItemClickListener;

import butterknife.BindView;

/**
 * Created by Damon on 2016/12/7.
 * 知乎日报
 */

public class FragmentZhihu extends CoreBaseFragment<ZhihuPresenter, ZhihuModel> implements ZhihuContract.ZhihuView {
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
        coreRecyclerView.addOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                ActZhihuDetail.start(mActivity, view.findViewById(R.id.iv_daily_item_image), ((DailyListBean.StoriesBean) adapter.getData().get(position)).getId());
            }
        });
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_zhihu;
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
        coreRecyclerView.init(new BaseQuickAdapter<DailyListBean.StoriesBean, BaseViewHolder>(R.layout.item_news) {
            @Override
            protected void convert(BaseViewHolder helper, DailyListBean.StoriesBean item) {
                helper.setText(R.id.tv_daily_item_title, item.getTitle());
                ImageLoaderUtil.getInstance().loadImage(mContext,
                        new ImageLoader.Builder()
                                .imgView((ImageView) helper.getView(R.id.iv_daily_item_image))
                                .url(item.getImages().get(0)).build());
            }
        });
        //单独使用refresh需要使用带参数的
        coreRecyclerView.openRefresh(new CoreRecyclerView.addDataListener() {
            @Override
            public void addData(int page) {
                initData();
            }
        });
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showToast(mContext, msg);
    }
}
