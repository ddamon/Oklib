package com.dm.learn.rxjava.ch2;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dm.learn.rxjava.ch2.contract.RxjavaContract;
import com.dm.learn.rxjava.ch2.model.RxjavaModel;
import com.dm.learn.rxjava.ch2.presenter.RxjavaPresenter2;
import com.dunkeng.R;
import com.dunkeng.common.OnFragmentOpenDrawerListener;
import com.oklib.base.CoreBaseFragment;
import com.oklib.utils.Logger.Logger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * rxjava学习使用
 * 第二章
 */
public class FragmentRxjavaCh2 extends CoreBaseFragment<RxjavaPresenter2, RxjavaModel> implements RxjavaContract.View {
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

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10, R.id.button11, R.id.button12})
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
            case R.id.button6:
                break;
            case R.id.button7:
                mPresenter.testMaybe();
                break;
            case R.id.button8:
                break;
            case R.id.button9:
                break;
            case R.id.button10:
                break;
            case R.id.button11:
                break;
            case R.id.button12:
                break;
        }

    }
}
