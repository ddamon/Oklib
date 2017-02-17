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

import java.lang.reflect.Field;

public class CoreBaseNormalFragment extends Fragment {
    protected LayoutInflater inflater;
    protected View contentView;
    private Context context;
    private ViewGroup container;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        onCreateView(savedInstanceState);
        if (contentView == null)
            return super.onCreateView(inflater, container, savedInstanceState);
        return contentView;
    }

    protected void onCreateView(Bundle savedInstanceState) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contentView = null;
        container = null;
        inflater = null;
    }

    public Context getApplicationContext() {
        return context;
    }

    public void setContentView(int layoutResID) {
        setContentView((ViewGroup) inflater.inflate(layoutResID, container, false));
    }

    public void setContentView(View view) {
        contentView = view;
    }

    public View getContentView() {
        return contentView;
    }

    public View findViewById(int id) {
        if (contentView != null)
            return contentView.findViewById(id);
        return null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
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
        intent = new Intent(getApplicationContext(), cls);
        startActivity(intent);
    }

    /**
     * 新建一个activity打开
     *
     * @param cls
     */
    public void gotoActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(getApplicationContext(), cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void gotoActivity(Class<?> cls, int enterAnim, int exitAnim) {
        gotoActivity(cls);
        getActivity().overridePendingTransition(enterAnim, exitAnim);
    }
}
