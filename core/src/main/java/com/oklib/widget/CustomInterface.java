package com.oklib.widget;


import androidx.viewpager.widget.PagerAdapter;

/**
 * @author: 蜡笔小新
 * @date: 2016-05-31 10:56
 * @GitHub: https://github.com/meikoz
 */
public interface CustomInterface {
    void updateIndicatorView(int size, int resid);

    void setAdapter(PagerAdapter adapter);

    void startScorll();

    void endScorll();
}
