package com.oklib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

/**
 * Created by Damon.Han on 2017/2/16 0016.
 *
 * @author Damon
 */

public class JumpUtil {
    /**
     * 从view放大显示新activity
     *
     * @param intent
     * @param context
     * @param view
     */
    public static void startActivityScale(Intent intent, Context context, View view) {
        //让新的Activity从一个小的范围扩大到全屏
        ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(view, (int) view.getWidth() / 2, (int) view.getHeight() / 2, //拉伸开始的坐标
                0, 0);//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }

    /**
     *
     * @param intent
     * @param context
     * @param view
     */
    public static void startActivityTranslate(Intent intent, Context context, View view) {
        ActivityCompat.startActivity(context, intent, ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, view, "translate_view").toBundle());
    }
}
