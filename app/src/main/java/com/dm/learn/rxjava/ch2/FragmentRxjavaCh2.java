package com.dm.learn.rxjava.ch2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dunkeng.R;
import com.dm.learn.rxjava.ch2.contract.RxjavaContract;
import com.dm.learn.rxjava.ch2.model.RxjavaModel;
import com.dm.learn.rxjava.ch2.presenter.RxjavaPresenter;
import com.dunkeng.common.OnFragmentOpenDrawerListener;
import com.oklib.base.CoreBaseFragment;
import com.oklib.utils.Logger.Logger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * rxjava学习使用
 * 第二章
 */
public class FragmentRxjavaCh2 extends CoreBaseFragment<RxjavaPresenter, RxjavaModel> implements RxjavaContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.button5)
    Button button5;
    @BindView(R.id.txt_result)
    TextView txtResult;

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
        mPresenter.getData();
    }


    @Override
    public int getLayoutId() {
        return R.layout.learn_rxjava_fragment_ch2;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        toolbar.setTitle(getString(R.string.rx_ch2));
        toolbar.setNavigationIcon(R.mipmap.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(v -> {
            if (mOpenDraweListener != null) {
                mOpenDraweListener.onOpenDrawer();
            }
        });
    }

    @Override
    public void showMsg(String msg) {
        txtResult.setText(msg);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.e("onDestroy");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.e("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.e("onPause");
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.button1:
                mPresenter.testDo();
                break;
            case R.id.button2:
                mPresenter.testSingle();
                break;
            case R.id.button3:
                mPresenter.testCompleteable();
                break;
            case R.id.button4:
                mPresenter.testMaybe();
                break;
            case R.id.button5:
                break;
        }

    }
}
