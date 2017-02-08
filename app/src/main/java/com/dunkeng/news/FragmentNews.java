package com.dunkeng.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.dunkeng.Config;
import com.dunkeng.R;
import com.dunkeng.news.contract.NewsContract;
import com.dunkeng.news.model.News;
import com.dunkeng.news.model.NewsModel;
import com.dunkeng.news.presenter.NewsPresenter;
import com.oklib.base.CoreBaseFragment;
import com.oklib.utils.ToastUtils;
import com.oklib.widget.imageloader.ImageLoader;
import com.oklib.widget.imageloader.ImageLoaderUtil;
import com.oklib.widget.recyclerview.BaseQuickAdapter;
import com.oklib.widget.recyclerview.BaseViewHolder;
import com.oklib.widget.recyclerview.CoreRecyclerView;


public class FragmentNews extends CoreBaseFragment<NewsPresenter, NewsModel> implements NewsContract.ViewNews {
    CoreRecyclerView coreRecyclerView;
    private int pageNum = 20;
    private static String type;

    public static FragmentNews newInstance(int position) {
        FragmentNews fragment = new FragmentNews();
        Bundle bundle = new Bundle();
        bundle.putInt(Config.ARG_POSITION, position);
        fragment.setArguments(bundle);
        type = Config.getApiType(position);
        return fragment;
    }

    /**
     * 初始化view
     *
     * @return
     */
    @Override
    public View getLayoutView() {
        coreRecyclerView = new CoreRecyclerView(mContext).init(new BaseQuickAdapter<News.NewslistBean, BaseViewHolder>(R.layout.item_daily) {
            @Override
            protected void convert(BaseViewHolder helper, News.NewslistBean item) {
                helper.setText(R.id.tv_daily_item_title, item.getTitle());
                ImageLoaderUtil.getInstance().loadImage(mContext,
                        new ImageLoader.Builder()
                                .imgView((ImageView) helper.getView(R.id.iv_daily_item_image))
                                .url(item.getPicUrl()).build());
            }
        }).openRefresh(num -> mPresenter.getNewsData(type, pageNum));
        return coreRecyclerView;
    }

    @Override
    public void initData() {
        mPresenter.getNewsData(type, pageNum);
    }

    @Override
    public void showContent(News info) {
        coreRecyclerView.getAdapter().addData(info.getNewslist());
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