package com.oklib.widget.notification;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.widget.RemoteViews;

import com.oklib.BuildConfig;

import static androidx.core.app.NotificationCompat.PRIORITY_DEFAULT;
import static androidx.core.app.NotificationCompat.VISIBILITY_SECRET;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://www.jianshu.com/p/514eb6193a06
 *     time  : 2018/2/10
 *     desc  : 通知栏工具类
 *
 * </pre>
 */
public class NotificationUtils {


    /**
     * 构造器
     *
     * @param builder
     */
    public NotificationUtils(Builder builder) {
        this.context = builder.context;
        this.CHANNEL_ID = builder.CHANNEL_ID;
        this.CHANNEL_NAME = builder.CHANNEL_NAME;
        this.progress = builder.progress;
        this.flags = builder.flags;
        this.ongoing = builder.ongoing;
        this.remoteViews = builder.remoteViews;
        this.intent = builder.intent;
        this.ticker = builder.ticker;
        this.onlyAlertOnce = builder.onlyAlertOnce;
        this.when = builder.when;
        this.sound = builder.sound;
        this.defaults = builder.defaults;
        this.vibrate = builder.vibrate;
        this.priority = builder.priority;
        this.smallIcon = builder.smallIcon;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //android 8.0以上需要特殊处理，也就是targetSDKVersion为26以上
            createNotificationChannel();
        }
    }

    private NotificationManager mManager;
    private NotificationChannel channel;


    private NotificationCompat.Builder builder;
    private Context context;
    private int smallIcon;
    private String CHANNEL_ID;
    private String CHANNEL_NAME;
    private int progress;
    private int[] flags;
    private boolean ongoing;
    private RemoteViews remoteViews;
    private PendingIntent intent;
    private String ticker;
    private boolean onlyAlertOnce;
    private long when;
    private Uri sound;
    private int defaults;
    private long[] vibrate;
    private int priority;


    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        //第一个参数：channel_id
        //第二个参数：channel_name
        //第三个参数：设置通知重要性级别
        //注意：该级别必须要在 NotificationChannel 的构造函数中指定，总共要五个级别；
        //范围是从 NotificationManager.IMPORTANCE_NONE(0) ~ NotificationManager.IMPORTANCE_HIGH(4)
        channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        channel.canBypassDnd();//是否绕过请勿打扰模式
        channel.enableLights(true);//闪光灯
        channel.setLockscreenVisibility(VISIBILITY_SECRET);//锁屏显示通知
        channel.setLightColor(Color.RED);//闪关灯的灯光颜色
        channel.canShowBadge();//桌面launcher的消息角标
        channel.enableVibration(true);//是否允许震动
        channel.getAudioAttributes();//获取系统通知响铃声音的配置
        channel.getGroup();//获取通知取到组
        channel.setBypassDnd(true);//设置可绕过 请勿打扰模式
        channel.setVibrationPattern(new long[]{100, 100, 200});//设置震动模式
        channel.shouldShowLights();//是否会有灯光
        getManager().createNotificationChannel(channel);
    }

    /**
     * 获取创建一个NotificationManager的对象
     *
     * @return NotificationManager对象
     */
    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    /**
     * 清空所有的通知
     */
    public void clearNotification() {
        getManager().cancelAll();
    }

    /**
     * 获取但未显示
     *
     * @param title   title
     * @param content content
     */
    public Notification getNotification(String title, String content, int icon) {
        Notification notification;
        builder = getNotificationBuilder(title, content, icon);
        builder.setProgress(100, progress, false);
        notification = builder.build();

        if (flags != null && flags.length > 0) {
            for (int a = 0; a < flags.length; a++) {
                notification.flags |= flags[a];
            }
        }
        return notification;
    }

    /**
     * 建议使用这个发送通知
     * 调用该方法可以发送通知
     *
     * @param notifyId notifyId
     * @param title    title
     * @param content  content
     */
    public Notification sendNotification(int notifyId, String title, String content, int icon) {
        Notification notification;
        builder = getNotificationBuilder(title, content, icon);
        if (progress >= 0) {
            builder.setProgress(100, progress, false);
        }
        notification = builder.build();
        if (flags != null && flags.length > 0) {
            for (int a = 0; a < flags.length; a++) {
                notification.flags |= flags[a];
            }
        }
        getManager().notify(notifyId, notification);
        return notification;
    }


    public NotificationCompat.Builder getNotificationBuilder(String title, String content, int icon) {
        if (builder == null) {
            builder = new NotificationCompat.Builder(context, CHANNEL_ID);
            builder.setPriority(priority);
            builder.setContentTitle(title);
            builder.setContentText(content);
            builder.setSmallIcon(icon);
            builder.setOnlyAlertOnce(onlyAlertOnce);
            builder.setOngoing(ongoing);
            if (remoteViews != null) {
                builder.setContent(remoteViews);
            }
            if (intent != null) {
                builder.setContentIntent(intent);
            }
            if (ticker != null && ticker.length() > 0) {
                builder.setTicker(ticker);
            }
            if (when != 0) {
                builder.setWhen(when);
            }
            if (sound != null) {
                builder.setSound(sound);
            }
            if (defaults != 0) {
                builder.setDefaults(defaults);
            }
            if (vibrate != null) {
                //自定义震动效果
                builder.setVibrate(vibrate);
            }
            //点击自动删除通知
            builder.setAutoCancel(true);
        }
        return builder;
    }

    /**
     * 更新进度条
     *
     * @param notiId
     * @param progress
     */
    public void setNotificationProgress(int notiId, int progress) {
        builder.setProgress(100, progress, false);
        if (progress >= 100 || progress < 0) {
            getManager().cancelAll();
        } else {
            getManager().notify(notiId, getBuilder().build());
        }
    }

    /**
     * 获取notification builder
     *
     * @return
     */
    public NotificationCompat.Builder getBuilder() {
        return builder;
    }


    public static class Builder {
        private Context context;
        private String CHANNEL_ID = BuildConfig.APPLICATION_ID + "_Channel_Id";
        private String CHANNEL_NAME = BuildConfig.APPLICATION_ID + "_Channel_Name";
        private int progress = -1;
        private int[] flags;
        private boolean ongoing = false;
        private RemoteViews remoteViews = null;
        private PendingIntent intent = null;
        private String ticker = "";
        private boolean onlyAlertOnce = false;
        private long when = 0;
        private Uri sound = null;
        private int defaults = 0;
        private int smallIcon;

        public long[] getVibrate() {
            return vibrate;
        }

        public Builder setVibrate(long[] vibrate) {
            this.vibrate = vibrate;
            return this;
        }

        private long[] vibrate = null;
        private int priority = PRIORITY_DEFAULT;

        public String getCHANNEL_ID() {
            return CHANNEL_ID;
        }

        public Builder setCHANNEL_ID(String CHANNEL_ID) {
            this.CHANNEL_ID = CHANNEL_ID;
            return this;
        }

        public String getCHANNEL_NAME() {
            return CHANNEL_NAME;
        }

        public Builder setCHANNEL_NAME(String CHANNEL_NAME) {
            this.CHANNEL_NAME = CHANNEL_NAME;
            return this;
        }

        public int getProgress() {
            return progress;
        }

        public Builder setProgress(int progress) {
            this.progress = progress;
            return this;
        }

        public int[] getFlags() {
            return flags;
        }

        public Builder setFlags(int... flags) {
            this.flags = flags;
            return this;
        }

        public boolean isOngoing() {
            return ongoing;
        }

        public Builder setOngoing(boolean ongoing) {
            this.ongoing = ongoing;
            return this;
        }

        public RemoteViews getRemoteViews() {
            return remoteViews;
        }

        public Builder setRemoteViews(RemoteViews remoteViews) {
            this.remoteViews = remoteViews;
            return this;
        }

        public PendingIntent getIntent() {
            return intent;
        }

        public Builder setIntent(PendingIntent intent) {
            this.intent = intent;
            return this;
        }

        public String getTicker() {
            return ticker;
        }

        public Builder setTicker(String ticker) {
            this.ticker = ticker;
            return this;
        }

        public boolean isOnlyAlertOnce() {
            return onlyAlertOnce;
        }

        public Builder setOnlyAlertOnce(boolean onlyAlertOnce) {
            this.onlyAlertOnce = onlyAlertOnce;
            return this;
        }

        public long getWhen() {
            return when;
        }

        public Builder setWhen(long when) {
            this.when = when;
            return this;
        }

        public Uri getSound() {
            return sound;
        }

        public Builder setSound(Uri sound) {
            this.sound = sound;
            return this;
        }

        public int getDefaults() {
            return defaults;
        }

        public Builder setDefaults(int defaults) {
            this.defaults = defaults;
            return this;
        }


        public int getPriority() {
            return priority;
        }

        public Builder setPriority(int priority) {
            this.priority = priority;
            return this;
        }

        /**
         * 设置图标 如果sendNotification传0则此处必须设置
         *
         * @param icon
         * @return
         */
        public Builder setSmallIcon(int icon) {
            this.smallIcon = icon;
            return this;
        }

        public NotificationUtils build(Context context) {
            this.context = context;
            return new NotificationUtils(this);
        }
    }
}
