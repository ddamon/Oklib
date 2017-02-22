package com.oklib.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.oklib.widget.LoadingView;

import butterknife.ButterKnife;

public abstract class CoreBaseNormalFragment extends Fragment {
    protected View rootView;
    private LoadingView mLoginView;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, rootView);
        mLoginView = new LoadingView(getActivity());
        onInit();
        return rootView;
    }

    protected abstract int getLayoutId();

    protected abstract void onInit();

    public void showLoadingView() {
        mLoginView.show();
    }

    public void hideLoadingView() {
        mLoginView.hide();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private ProgressDialog progressBar;

    public void dismissProgressDialog() {
        if (progressBar != null && progressBar.isShowing()) {
            progressBar.cancel();
        }
    }

    public void showProgressDialog(String title, String msg) {
        showProgressDialog(title, msg, true);
    }

    public void showProgressDialog(String title, String msg,
                                   boolean cancelButton) {
        dismissProgressDialog();
        progressBar = new ProgressDialog(getActivity());
        progressBar.setTitle(title);
        progressBar.setMessage(msg);
        progressBar.setCancelable(false);
        if (cancelButton) {
            progressBar.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            if (progressBar != null) {
                                progressBar.dismiss();
                            }
                        }

                    });
        }
        progressBar.show();
    }

    public void toast(int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }


    public void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 从资源获取字符串
     *
     * @param resId
     * @return
     */
    public String getStr(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 从EditText 获取字符串
     *
     * @param editText
     * @return
     */
    public String getStr(EditText editText) {
        return editText.getText().toString();
    }

    /**
     * 新建一个activity打开
     *
     * @param cls
     */
    public void gotoActivity(Class<?> cls) {
        Intent intent;
        intent = new Intent(context, cls);
        startActivity(intent);
    }

    /**
     * 新建一个activity打开
     *
     * @param cls
     */
    public void gotoActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void gotoActivity(Class<?> cls, int enterAnim, int exitAnim) {
        gotoActivity(cls);
        getActivity().overridePendingTransition(enterAnim, exitAnim);
    }
}
