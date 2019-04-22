package com.oklib.widget.appupdate;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener3;
import com.oklib.R;
import com.oklib.utils.Logger.Logger;
import com.oklib.utils.file.SdCardUtil;
import com.oklib.widget.notification.NotificationUtils;

import java.io.File;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/05/29
 *     desc  : 版本更新弹窗
 *     revise:
 * </pre>
 */
public class UpdateFragment extends BaseDialogFragment implements View.OnClickListener {


    private static final String[] mPermission = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    private int downloadStatus = UpdateUtils.DownloadStatus.START;

    private FragmentActivity mActivity;
    private DownloadTask downloadTask;

    private ProgressBar mProgress;
    private TextView mTvCancel;
    private TextView mTvOk;
    private static UpdateConfig updateConfig;
    private static final int notificationId = 5555;

    NotificationUtils notificationUtils;

    /**
     * 版本更新
     */
    public static void showFragment(FragmentActivity activity, UpdateConfig updateConfig) {
        if (updateConfig == null) {
            return;
        }
        UpdateFragment updateFragment = new UpdateFragment();
        UpdateFragment.updateConfig = updateConfig;
        updateFragment.show(activity.getSupportFragmentManager());
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setLocal(Local.CENTER);
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (FragmentActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    /**
     * 如果isForceUpdate是true，那么就是强制更新，则设置cancel为false
     * 如果isForceUpdate是false，那么不是强制更新，则设置cancel为true
     */
    @Override
    protected boolean isCancel() {
        return !updateConfig.isForceUpdate();
    }


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_update_app;
    }

    @Override
    public void bindView(View view) {
        initView(view);
        onKeyListener();
        createFilePath();
    }


    private void initView(View view) {
        //ImageView mIvTop = view.findViewById(R.id.iv_top);
        TextView mTvDesc = view.findViewById(R.id.tv_desc);
        mProgress = view.findViewById(R.id.progress);
        mTvCancel = view.findViewById(R.id.tv_cancel);
        mTvOk = view.findViewById(R.id.tv_ok);

        mProgress.setMax(100);
        mProgress.setProgress(0);
        mTvDesc.setText(updateConfig.getDesc() == null ? "" : updateConfig.getDesc());
        if (updateConfig.isForceUpdate()) {
            mTvOk.setVisibility(View.VISIBLE);
            mTvCancel.setVisibility(View.GONE);
        } else {
            mTvOk.setVisibility(View.VISIBLE);
            mTvCancel.setVisibility(View.VISIBLE);
        }
        mTvOk.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
    }

    /**
     * 这里主要是处理返回键逻辑
     */
    private void onKeyListener() {
        if (getDialog() != null) {
            getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    switch (keyCode) {
                        // 返回键
                        case KeyEvent.KEYCODE_BACK:
                            if (updateConfig.isForceUpdate()) {
                                return true;
                            }
                        default:
                            break;
                    }
                    return false;
                }
            });
        }
    }


    private void createFilePath() {
        //获取下载保存path
        String saveApkPath = updateConfig.getSaveApkPath();
        String apkName = updateConfig.getApkName();
        if (new File(saveApkPath + File.pathSeparator + apkName).exists()) {
            changeUploadStatus(UpdateUtils.DownloadStatus.FINISH);
        } else {
            changeUploadStatus(UpdateUtils.DownloadStatus.START);
        }
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_ok) {
            //当下载中，点击后则是暂停下载
            //当不是下载中，点击后先判断apk是否存在，若存在则提示安装；如果不存在，则下载
            //当出现错误时，点击后继续开始下载
            switch (downloadStatus) {
                case UpdateUtils.DownloadStatus.START:
                case UpdateUtils.DownloadStatus.UPLOADING:
                    if (downloadTask != null) {
                        downloadTask.cancel();
                    } else {
                        checkPermissionAndDownApk();
                    }
                    break;
                case UpdateUtils.DownloadStatus.FINISH:
                    File file = new File(updateConfig.getSaveApkPath() + File.pathSeparator + updateConfig.getApkName());
                    if (file.exists()) {
                        //检测是否有apk文件，如果有直接普通安装
                        UpdateUtils.installNormal(mActivity, file.getPath(), updateConfig.getPackageName());
                        dismissDialog();
                    } else {
                        checkPermissionAndDownApk();
                    }
                    break;
                case UpdateUtils.DownloadStatus.PAUSED:
                case UpdateUtils.DownloadStatus.ERROR:
                    checkPermissionAndDownApk();
                    break;
            }
        } else if (i == R.id.tv_cancel) {
            //如果正在下载，那么就先暂停，然后finish
            if (downloadStatus == UpdateUtils.DownloadStatus.UPLOADING) {
                if (downloadTask != null) {
                    downloadTask.cancel();
                }
            }
            dismissDialog();
        }
    }


    private void changeUploadStatus(int upload_status) {
        this.downloadStatus = upload_status;
        switch (upload_status) {
            case UpdateUtils.DownloadStatus.START:
                mTvOk.setText("开始下载");
                mProgress.setVisibility(View.GONE);
                break;
            case UpdateUtils.DownloadStatus.UPLOADING:
                mTvOk.setText("下载中……");
                mProgress.setVisibility(View.VISIBLE);
                break;
            case UpdateUtils.DownloadStatus.FINISH:
                mTvOk.setText("开始安装");
                mProgress.setVisibility(View.INVISIBLE);
                break;
            case UpdateUtils.DownloadStatus.PAUSED:
                mProgress.setVisibility(View.VISIBLE);
                mTvOk.setText("暂停下载");
                break;
            case UpdateUtils.DownloadStatus.ERROR:
                mProgress.setVisibility(View.VISIBLE);
                mTvOk.setText("错误，点击继续");
                break;
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("WrongConstant")
    private void checkPermissionAndDownApk() {
        if (mActivity == null) {
            return;
        }
        PermissionUtils.init(mActivity);
        boolean granted = PermissionUtils.isGranted(mPermission);
        if (granted) {
            setNotification(0);
            String sdCardPath = "";
            if (updateConfig.getSaveApkPath() != null) {
                sdCardPath = updateConfig.getSaveApkPath();
            } else {
                sdCardPath = SdCardUtil.getNormalSDCardPath();
            }
            String apkPath = sdCardPath + File.separator + updateConfig.getApkName();
            downloadTask = downApk(updateConfig.getApkUrl(), apkPath);
        } else {
            /*PermissionUtils permission = PermissionUtils.permission(mPermission);
            permission.callback(new PermissionUtils.SimpleCallback() {
                @Override
                public void onGranted() {
                    setNotification(0);
                    downloadTask = downApk(mActivity, apkUrl, saveApkPath, getListener());
                }
                @Override
                public void onDenied() {
                    PermissionUtils.openAppSettings();
                    Toast.makeText(mActivity,"请允许权限",Toast.LENGTH_SHORT).show();
                }
            });
            permission.request();*/
            Toast.makeText(mActivity, "请先申请读写权限", Toast.LENGTH_SHORT).show();
        }
    }


    private DownloadTask downApk(String apkUrl, final String saveApkPath) {
        DownloadTask baseDownloadTask =
                new DownloadTask.Builder(apkUrl, new File(saveApkPath).getParent(), new File(saveApkPath).getName())
                        .setMinIntervalMillisCallbackProcess(16).build();

        baseDownloadTask.enqueue(new DownloadListener3() {
            @Override
            protected void started(@NonNull DownloadTask task) {
                changeUploadStatus(UpdateUtils.DownloadStatus.UPLOADING);
            }

            @Override
            protected void completed(@NonNull DownloadTask task) {
                setNotification(100);
                if (updateConfig.isForceUpdate()) {
                    mProgress.setProgress(100);
                }
                changeUploadStatus(UpdateUtils.DownloadStatus.FINISH);
                UpdateUtils.installNormal(mActivity, saveApkPath, updateConfig.getPackageName());
                dismissDialog();
            }

            @Override
            protected void canceled(@NonNull DownloadTask task) {
                changeUploadStatus(UpdateUtils.DownloadStatus.PAUSED);
            }

            @Override
            protected void error(@NonNull DownloadTask task, @NonNull Exception e) {
                setNotification(-1);
                changeUploadStatus(UpdateUtils.DownloadStatus.ERROR);
                Log.e("UpdateFragment", e.getLocalizedMessage());
            }

            @Override
            protected void warn(@NonNull DownloadTask task) {
                changeUploadStatus(UpdateUtils.DownloadStatus.ERROR);
            }

            @Override
            public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {

            }

            @Override
            public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset, long totalLength) {

            }

            @Override
            public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
                float progress = (float) currentOffset / totalLength;
                mProgress.setProgress((int) (progress * mProgress.getMax()));
                setNotification((int) (progress * mProgress.getMax()));
            }
        });
        return baseDownloadTask;
    }

    /**
     * 暂不支持自定义
     * TODO
     *
     * @param progress
     */
    protected void setNotification(int progress) {
        if (mActivity == null) {
            return;
        }
        if (!updateConfig.isShowNotification()) {
            return;
        }
        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(mActivity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews remoteViews = new RemoteViews(mActivity.getPackageName(), R.layout.remote_notification_view);
        remoteViews.setTextViewText(R.id.title, "下载中...");
        remoteViews.setProgressBar(R.id.progress, 100, progress, false);
        notificationUtils = new NotificationUtils.Builder()
                .setIntent(pendingIntent)
                .setRemoteViews(remoteViews)
                .setSmallIcon(updateConfig.getSmallIcon() > 0 ? updateConfig.getSmallIcon() : R.drawable.lib_img_default)
                .setFlags(Notification.FLAG_AUTO_CANCEL)
                .setOnlyAlertOnce(true).build(mActivity);
        notification = notificationUtils.getNotification("提示", "下载中...", updateConfig.getSmallIcon() > 0 ? updateConfig.getSmallIcon() : R.drawable.ic_file_download_black_36dp);
        notificationUtils.setNotificationProgress(notificationId, progress);
    }

    private Notification notification;

    /**
     * 设置一个通知
     *
     * @param notification
     */
    public void setNotification(Notification notification) {
        this.notification = notification;
    }

}
