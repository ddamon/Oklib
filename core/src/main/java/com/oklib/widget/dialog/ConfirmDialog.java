package com.oklib.widget.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.oklib.R;

/**
 * @author Damon.Han
 */
public class ConfirmDialog extends SweetDialog implements View.OnClickListener {

    private TextView mTvMessage;
    private TextView mTvTitle;
    private Button mBtnCancle, mBtnSubmit;

    private String title;
    private String message;
    private String nevigateText, positiveText;
    private View.OnClickListener onNevigateClickListener, onPositiveClickListener;

    public ConfirmDialog(Context context) {
        super(context);
    }

    @Override
    public int getContentResourceId() {
        return R.layout.core_dialog_confirm;
    }

    @Override
    public void init() {
        mTvMessage = (TextView) v(R.id.confirm_message);
        mTvTitle = (TextView) v(R.id.confirm_title);
        mBtnCancle = (Button) v(R.id.confirm_cancle);
        mBtnSubmit = (Button) v(R.id.confirm_submit);
    }

    /**
     * 设置标题
     *
     * @return
     */
    public ConfirmDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public ConfirmDialog setMessage(String msg) {
        this.message = msg;
        return this;
    }


    public ConfirmDialog setMessage(String title, String msg) {
        this.title = title;
        this.message = msg;
        return this;
    }

    public ConfirmDialog setNevigateButton(String nevigateButton, View.OnClickListener listener) {
        nevigateText = nevigateButton;
        onNevigateClickListener = listener;
        return this;
    }

    public ConfirmDialog setPositiveButton(String positiveButton, View.OnClickListener listener) {
        positiveText = positiveButton;
        onPositiveClickListener = listener;
        return this;
    }

    /**
     * 默认显示两个按钮
     * 默认不显示title
     */
    private void setData() {
        mTvMessage.setText(message);
        if (!TextUtils.isEmpty(nevigateText)) {
            mBtnCancle.setText(nevigateText);
        } else {
            mBtnCancle.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(positiveText)) {
            mBtnSubmit.setText(positiveText);
        } else {
            mBtnSubmit.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(title)) {
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(title);
        } else {
            mTvTitle.setVisibility(View.GONE);
        }
        if (onNevigateClickListener != null) {
            mBtnCancle.setOnClickListener(onNevigateClickListener);
        } else {
            mBtnCancle.setOnClickListener(this);
        }
        if (onPositiveClickListener != null) {
            mBtnSubmit.setOnClickListener(onPositiveClickListener);
        } else {
            mBtnSubmit.setOnClickListener(this);
        }
    }

    @Override
    public void show() {
        super.show();
        setData();
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}
