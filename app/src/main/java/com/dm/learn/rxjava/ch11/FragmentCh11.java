package com.dm.learn.rxjava.ch11;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

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
public class FragmentCh11 extends CoreBaseFragment<Ch11Presenter, Ch11Model> implements Ch11Contract.View {
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
    @BindView(R.id.button6)
    Button button6;
    @BindView(R.id.button7)
    Button button7;

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
        return R.layout.learn_rxjava_fragment_ch11;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mPresenter.testClick(button1);
        mPresenter.testLongClick(button2);
        mPresenter.testRepeatClick(button3);
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showToast(String string) {
        super.showToast(string);
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

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.button1:

                break;
            case R.id.button2:

                break;
            case R.id.button3:

                break;
            case R.id.button4:
                startActivity(new Intent(getActivity(), ActivityValidateText.class));
                break;
            case R.id.button5:
                break;
            case R.id.button6:
                break;
            case R.id.button7:
                break;
        }

    }
}
