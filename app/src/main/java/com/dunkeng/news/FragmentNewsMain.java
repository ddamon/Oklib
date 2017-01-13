package com.dunkeng.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dunkeng.Config;
import com.dunkeng.R;
import com.dunkeng.news.contract.NewsContract;
import com.dunkeng.news.model.NewsModel;
import com.dunkeng.news.presenter.NewsPresenterMain;
import com.oklib.base.CoreBaseFragment;
import com.oklib.utils.helper.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Damon.Han on 2017/1/13 0013.
 *
 * @author Damon
 */

public class FragmentNewsMain extends CoreBaseFragment<NewsPresenterMain, NewsModel> implements NewsContract.ViewNewsMain {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    List<Fragment> fragments = new ArrayList<>();

    public static FragmentNewsMain newInstance() {
        FragmentNewsMain fragment = new FragmentNewsMain();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void showTabList(String[] mTabs) {
        for (int i = 0; i < mTabs.length; i++) {
            tabs.addTab(tabs.newTab().setText(mTabs[i]));
            FragmentNews fragmentNews = FragmentNews.newInstance(i);
            fragments.add(fragmentNews);
        }
        int position = getArguments().getInt(Config.ARG_POSITION, 1);
        viewpager.setAdapter(new FragmentAdapter(getChildFragmentManager(), fragments));
        viewpager.setCurrentItem(position);//要设置到viewpager.setAdapter后才起作用
        tabs.setupWithViewPager(viewpager);
        tabs.setVerticalScrollbarPosition(position);
        for (int i = 0; i < mTabs.length; i++) {
            tabs.getTabAt(i).setText(mTabs[i]);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.app_bar_main_tab;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void showError(String msg) {

    }


}
