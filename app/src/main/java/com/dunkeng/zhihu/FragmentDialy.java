package com.dunkeng.zhihu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.dunkeng.R;
import com.dunkeng.news.presenter.NewsPresenter;
import com.oklib.base.CoreBaseFragment;
import com.oklib.utils.ToastUtils;
import com.oklib.widget.imageloader.ImageLoader;
import com.oklib.widget.imageloader.ImageLoaderUtil;
import com.oklib.widget.recyclerview.BaseQuickAdapter;
import com.oklib.widget.recyclerview.BaseViewHolder;
import com.oklib.widget.recyclerview.CoreRecyclerView;

/**
 * Created by Damon on 2016/12/7.
 * 知乎日报
 */

public class FragmentDialy extends CoreBaseFragment<NewsPresenter, DailyModel> implements DailyContract.View {
    CoreRecyclerView coreRecyclerView;

    /**
     * 初始化view
     *
     * @return
     */
    @Override
    public View getLayoutView() {
        coreRecyclerView = new CoreRecyclerView(mContext).init(new BaseQuickAdapter<DailyListBean.StoriesBean, BaseViewHolder>(R.layout.item_daily) {
            @Override
            protected void convert(BaseViewHolder helper, DailyListBean.StoriesBean item) {
                helper.setText(R.id.tv_daily_item_title, item.getTitle());
                ImageLoaderUtil.getInstance().loadImage(mContext,
                        new ImageLoader.Builder()
                                .imgView((ImageView) helper.getView(R.id.iv_daily_item_image))
                                .url(item.getImages().get(0)).build());
            }
        });
        return coreRecyclerView;
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
        return 0;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void showError(String msg) {
        ToastUtils.showToast(mContext, msg);
    }
}
