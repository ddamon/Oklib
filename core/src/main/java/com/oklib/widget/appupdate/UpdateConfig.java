package com.oklib.widget.appupdate;

import android.support.annotation.NonNull;

/**
 * Created by Damon.Han on 2019/3/13 0013.
 *
 * @author Damon
 */
public class UpdateConfig {
    /**
     * 是否强制更新
     */
    private boolean isForceUpdate;
    /**
     * apk网址
     */
    @NonNull
    private String apkUrl;
    /**
     * 本地保存路径
     */
    private String saveApkPath;
    /**
     * 本地保存文件名
     */
    private String apkName;
    /**
     * 更新详情
     */
    private String desc;
    /**
     * 包名用于下载完成后自动安装
     */
    private String packageName;
    /**
     * 自定义通知栏更新view的id
     */
    private int notificationViewResId;
    /**
     * 自定义通知栏更新smallIcon
     */
    private int smallIcon;

    public int getSmallIcon() {
        return smallIcon;
    }

    public void setSmallIcon(int smallIcon) {
        this.smallIcon = smallIcon;
    }

    /**
     * 是否显示通知栏(默认显示)
     */
    private boolean isShowNotification = true;

    public boolean isShowNotification() {
        return isShowNotification;
    }

    public void setShowNotification(boolean showNotification) {
        isShowNotification = showNotification;
    }


    public int getNotificationViewResId() {
        return notificationViewResId;
    }

    /**
     * @param notificationViewResId
     */
    public void setNotificationViewResId(int notificationViewResId) {
        this.notificationViewResId = notificationViewResId;
    }


    public UpdateConfig(boolean isForceUpdate, String apkUrl, String saveApkPath, String apkName, String desc, String packageName, int notificationViewResId, boolean isShowNotification) {
        this.isForceUpdate = isForceUpdate;
        if (apkUrl == null || "".equals(apkUrl)) {
            throw new RuntimeException("必须指定apk地址");
        }
        this.apkUrl = apkUrl;
        if (saveApkPath == null || "".equals(saveApkPath)) {
            throw new RuntimeException("必须指定保存路径saveApkPath");
        }
        this.saveApkPath = saveApkPath;
        if (apkName == null || "".equals(apkName)) {
            throw new RuntimeException("必须指定文件名apkName");
        }
        this.apkName = apkName;
        this.desc = desc;

        if (packageName == null || "".equals(packageName)) {
            throw new RuntimeException("必须指定packageName");
        }
        if (isShowNotification && notificationViewResId <= 0) {
            throw new RuntimeException("设置显示通知时必须指定notificationViewResId");
        }
        this.packageName = packageName;
        this.notificationViewResId = notificationViewResId;
        this.isShowNotification = isShowNotification;
    }

    public boolean isForceUpdate() {
        return isForceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        isForceUpdate = forceUpdate;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getSaveApkPath() {
        return saveApkPath;
    }

    public void setSaveApkPath(String saveApkPath) {
        this.saveApkPath = saveApkPath;
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
