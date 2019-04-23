package com.dm.learn.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dm.learn.rxjava.ch11.FragmentCh11;
import com.dm.learn.rxjava.ch2.FragmentRxjavaCh2;
import com.dm.learn.rxjava.ch2.FragmentRxjavaCh3;
import com.dm.learn.rxjava.rxbus.ExceptionEvent;
import com.dm.learn.rxjava.rxbus.RxBusTestActivity;
import com.dunkeng.BuildConfig;
import com.dunkeng.R;
import com.oklib.base.CoreBaseActivity;
import com.oklib.utils.Logger.Logger;
import com.oklib.utils.rxbus.BusManager;
import com.oklib.utils.rxbus.Subscribe;

import java.io.File;

import butterknife.BindView;

/**
 * 顾名思义
 */
public class LearnActivityRxjavaActivity extends CoreBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @Override
    protected void onResume() {
        super.onResume();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, (Toolbar) findViewById(R.id.toolbar), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public int getLayoutId() {
        return R.layout.learn_rxjava_activity_main;
    }

    @Override
    public void initUI(Bundle savedInstanceState) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        loadRootFragment(R.id.main_container, new FragmentRxjavaCh2());
        BusManager.getBus().register(this);
    }

    @Override
    public void onBackPressedSupport() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressedSupport();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.learn_activity_rxjava, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.rx_ch2) {
            loadRootFragment(R.id.main_container, new FragmentRxjavaCh2());
            // Handle the camera action
        } else if (id == R.id.rx_ch3) {
            loadRootFragment(R.id.main_container, new FragmentRxjavaCh3());

        } else if (id == R.id.rx_ch4) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
        } else if (id == R.id.nav_11) {
            loadRootFragment(R.id.main_container, new FragmentCh11());

        } else if (id == R.id.nav_13) {
            startActivity(new Intent(mContext, RxBusTestActivity.class));
        } else if (id == R.id.nav_test_update) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Subscribe
    public void testRxbusEvent(ExceptionEvent exceptionEvent) {
        exceptionEvent.text = null;
        Logger.e(exceptionEvent.text.substring(0));
    }

}
