package com.oklib.widget.recyclerview.inter;


import android.view.View;

/**
 * <pre>
 *     @author 杨充
 *     blog  : https://github.com/yangchong211
 *     time  : 2016/4/28
 *     desc  : item中点击监听接口
 *     revise:
 * </pre>
 */
public interface OnItemClickListener {
    /**
     * item中点击监听接口
     * @param position          索引
     */
    void onItemClick(View view, int position);
}
