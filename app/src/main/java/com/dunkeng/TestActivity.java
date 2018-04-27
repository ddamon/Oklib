package com.dunkeng;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.oklib.base.CoreBaseActivity;

public class TestActivity extends CoreBaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initUI(Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolBar(toolbar, "关于");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "你点这里没用", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
