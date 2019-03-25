package com.oklib.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenganyuan.smac.R;

public abstract class BottomDialogBase extends DialogFragment {
    public static final String Arg_Data = "Data";
    public View mContentView;

    RelativeLayout mDialogHeader;
    TextView mDialogHeaderTitle;
    ImageView mDialogHeaderClose;

    LinearLayout mDialogFooter;
    Button mFooterBtnNegative;
    Button mFooterBtnPositive;
    LinearLayout mDialogContainer;

    private String mDialogTitle;
    private String mDialogNegativeText;
    private View.OnClickListener mDialogNegativeListener;
    private String mDialogPositiveText;
    private View.OnClickListener mDialogPositiveListener;
    private boolean isBottomHiden;
    private boolean isTitleHiden;
    private View mContainerChildView;
    private View mFooterDivider;

    private int dialogHeight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(R.layout.common_dialog_fragment_bottom_base, container, false);

            mDialogHeader = mContentView.findViewById(R.id.dialog_header);
            mDialogHeaderTitle = mContentView.findViewById(R.id.dialog_header_title);
            mDialogHeaderClose = mContentView.findViewById(R.id.dialog_header_right);
            mDialogFooter = mContentView.findViewById(R.id.dialog_footer);
            mFooterBtnNegative = mContentView.findViewById(R.id.dialog_footer_negative);
            mFooterBtnPositive = mContentView.findViewById(R.id.dialog_footer_positive);
            mDialogContainer = mContentView.findViewById(R.id.dialog_container);
            mFooterDivider = mContentView.findViewById(R.id.dialog_footer_divider);
            init();
            bindData();
        }
        if (mContentView != null) {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
        }
        return mContentView;
    }

    private void bindData() {
        if (isTitleHiden) {
            mDialogHeader.setVisibility(View.GONE);
        } else {
            if (mDialogTitle != null && mDialogTitle.length() > 0) {
                mDialogHeaderTitle.setText(mDialogTitle);
            } else {
                mDialogHeader.setVisibility(View.GONE);
            }
        }

        if (isBottomHiden) {
            mDialogFooter.setVisibility(View.GONE);
            mFooterDivider.setVisibility(View.GONE);
        } else {
            if (mDialogNegativeText != null && mDialogNegativeText.length() > 0) {
                mFooterBtnNegative.setText(mDialogNegativeText);
            }
            if (mDialogNegativeListener != null) {
                mFooterBtnNegative.setOnClickListener(mDialogNegativeListener);
            }
            if (mDialogPositiveText != null && mDialogPositiveText.length() > 0) {
                mFooterBtnPositive.setText(mDialogPositiveText);
            }
            if (mDialogPositiveListener != null) {
                mFooterBtnPositive.setOnClickListener(mDialogPositiveListener);
            }
        }

        if (mContainerChildView != null) {
            mDialogContainer.removeAllViews();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            mDialogContainer.addView(mContainerChildView, params);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        resizeDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
        removeBorder();
    }

    public static int[] getScreenSize(Activity activity) {
        if (activity != null) {
            DisplayMetrics outMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
            return new int[]{outMetrics.widthPixels, outMetrics.heightPixels};
        }
        return null;
    }

    /**
     * 设定DIALOG大小与位置
     */
    private void resizeDialog() {
        Dialog mDialog = getDialog();
        WindowManager.LayoutParams p = mDialog.getWindow().getAttributes();
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialogHeight = (int) (getScreenSize(getActivity())[1] * 0.6);
        p.height = dialogHeight;
        p.gravity = Gravity.BOTTOM;
        p.windowAnimations = R.style.BottomDialogAnimation;
        mDialog.getWindow().setAttributes(p);
        mDialog.getWindow().setBackgroundDrawable(null);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);

        mDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**
     * 重置窗口高度
     *
     * @param newDialogHeight
     */
    public void resetDialogHeight(int newDialogHeight) {
        Dialog mDialog = getDialog();
        WindowManager.LayoutParams p = mDialog.getWindow().getAttributes();
        p.height = newDialogHeight;
        mDialog.getWindow().setAttributes(p);
    }

    /**
     * 去边框
     */
    private void removeBorder() {
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(-1, -2);
        }
    }

    public abstract void init();

    /**
     * 是否隐藏底部栏
     */
    public void hideBottom() {
        isBottomHiden = true;
    }

    /**
     * 按钮 - 取消
     *
     * @param text
     * @param listener
     */
    public void setNegativeButton(String text, View.OnClickListener listener) {
        mDialogNegativeText = text;
        mDialogNegativeListener = listener;
    }

    /**
     * 按钮 - 提交
     *
     * @param text
     * @param listener
     */
    public void setPositiveButton(String text, View.OnClickListener listener) {
        mDialogPositiveText = text;
        mDialogPositiveListener = listener;
    }

    /**
     * 标题
     *
     * @param title
     */
    public void setTitle(String title) {
        mDialogTitle = title;
    }

    /**
     * 标题栏隐藏
     */
    public void hideTitle() {
        isTitleHiden = true;
    }

    /**
     * 添加窗体View
     *
     * @param view
     */
    public void addDialogBody(View view) {
        mContainerChildView = view;
    }


    public void setPositiveShown(boolean shown) {
        mFooterBtnPositive.setVisibility(shown ? View.VISIBLE : View.GONE);
    }

    public void setNegativeShown(boolean shown) {
        mFooterBtnNegative.setVisibility(shown ? View.VISIBLE : View.GONE);
    }

    public int getDialogHeight() {
        return dialogHeight;
    }

    public void addCloseButton() {
        mDialogHeaderClose.setVisibility(View.VISIBLE);
        mDialogHeaderClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }


    protected void refresh() {

    }

}
