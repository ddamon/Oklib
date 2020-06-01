package com.dunkeng.ganhuo;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.dunkeng.R;
import com.dunkeng.common.Config;
import com.dunkeng.common.OnFragmentOpenDrawerListener;
import com.dunkeng.ganhuo.contract.GanHuosContract;
import com.dunkeng.ganhuo.model.GanHuos;
import com.dunkeng.ganhuo.model.GanHuosModel;
import com.dunkeng.ganhuo.presenter.GanHuosPresenterMain;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
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

public class FragmentGanHuosMain extends CoreBaseFragment<GanHuosPresenterMain, GanHuosModel> implements GanHuosContract.ViewGanHuos {

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private List<Fragment> fragments = new ArrayList<>();
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
    public static FragmentGanHuosMain newInstance(int position) {
        FragmentGanHuosMain fragment = new FragmentGanHuosMain();
        Bundle bundle = new Bundle();
        bundle.putInt(Config.ArgumentKey.ARG_POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void showContent(GanHuos info) {

    }

    @Override
    public void showTabList(String[] mTabs) {
        for (int i = 0; i < mTabs.length; i++) {
            tabs.addTab(tabs.newTab().setText(mTabs[i]));
            FragmentGanHuos fragmentNews = FragmentGanHuos.newInstance(i);
            fragments.add(fragmentNews);
        }
        int position = getArguments().getInt(Config.ArgumentKey.ARG_POSITION, 0);
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
        return R.layout.fragment_news_main;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        toolbar.setTitle("干货");
        toolbar.setNavigationIcon(R.mipmap.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(v -> {
            if (mOpenDraweListener != null) {
                mOpenDraweListener.onOpenDrawer();
            }
        });
        fab.setOnClickListener(v -> Snackbar.make(v, "Snackbar comes out", Snackbar.LENGTH_LONG).setAction("action", vi -> {
        }));
    }

    @Override
    public void showMsg(String msg) {

    }


}
