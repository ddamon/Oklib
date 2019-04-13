package com.oklib.widget.recyclerview.inter;

import android.view.View;

/**
 * <pre>
 *     @author 杨充
 *     blog  : https://github.com/yangchong211
 *     time  : 2016/4/28
 *     desc  : item中长按点击监听接口
 *     revise:
 * </pre>
 */
public interface OnItemLongClickListener {
    /**
     * item中长按点击监听接口
     * @param position              索引
     * @return
     */
    boolean onItemLongClick(View view, int position);
}
