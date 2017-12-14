package com.dunkeng.news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dunkeng.App;
import com.dunkeng.common.Config;
import com.dunkeng.R;
import com.dunkeng.news.contract.NewsContract;
import com.dunkeng.news.model.NewsModel;
import com.dunkeng.news.model.NewslistBean;
import com.dunkeng.news.presenter.NewsDetailPresenter;
import com.oklib.base.CoreBaseActivity;
import com.oklib.utils.NetUtils;
import com.oklib.utils.SnackbarUtil;
import com.oklib.widget.imageloader.ImageLoader;
import com.oklib.widget.imageloader.ImageLoaderUtil;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Damon.Han on 2017/2/9 0009.
 *
 * @author Damon
 */

public class ActNewsDetail extends CoreBaseActivity<NewsDetailPresenter, NewsModel> implements NewsContract.ViewNewsDetail {

    @BindView(R.id.detail_bar_image)
    ImageView detailBarImage;
    @BindView(R.id.detail_bar_copyright)
    TextView detailBarCopyright;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.wv_detail_content)
    WebView wvDetailContent;
    @BindView(R.id.nsv_scroller)
    NestedScrollView nsvScroller;
    @BindView(R.id.fab)
    FloatingActionButton fab;


    @Override
    public int getLayoutId() {
        return R.layout.zhihu_details;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setToolBar(toolbar, "");
        WebSettings settings = wvDetailContent.getSettings();
        if (App.getNoImageState()) {
            settings.setBlockNetworkImage(true);
        }
        if (App.getAutoCacheState()) {
            settings.setAppCacheEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setDatabaseEnabled(true);
            if (NetUtils.isConnected(mContext)) {
                settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            } else {
                settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
            }
        }
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);

        wvDetailContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mPresenter.getDetail();
    }

    public static void start(Context context, View view, NewslistBean newslistBean) {
        Intent intent = new Intent(context, ActNewsDetail.class);
        intent.putExtra(Config.ArgumentKey.ARG_NEWS_BEAN, newslistBean);
        ActivityCompat.startActivity(context, intent, ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, view, "translate_view").toBundle());
    }

    @Override
    public void showContent() {
        NewslistBean info = (NewslistBean) getIntent().getSerializableExtra(Config.ArgumentKey.ARG_NEWS_BEAN);
        if (info == null) {
            return;
        }
        ImageLoaderUtil.getInstance().loadImage(mContext, new ImageLoader.Builder().imgView(detailBarImage).url(info.getPicUrl()).build());
        collapsingToolbar.setTitle(info.getTitle());
        detailBarCopyright.setText(info.getDescription());
        wvDetailContent.loadUrl(info.getUrl());
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showMsg(String msg) {
        SnackbarUtil.showShort(getWindow().getDecorView(), msg);
    }
}
