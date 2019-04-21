package com.oklib.widget.appupdate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/05/29
 *     desc  : 版本更新弹窗
 *     revise:
 * </pre>
 */
public class UpdateUtils {
    /*
     注意配置清单文件
     <provider
     android:name="android.support.v4.content.FileProvider"
     android:authorities="你的包名.fileprovider"
     android:exported="false"
     android:grantUriPermissions="true">
     <meta-data
     android:name="android.support.FILE_PROVIDER_PATHS"
     android:resource="@xml/file_paths" />
     </provider>
     */

    /**
     * 关于在代码中安装 APK 文件，在 Android N 以后，为了安卓系统为了安全考虑，不能直接访问软件
     * 需要使用 fileProvider 机制来访问、打开 APK 文件。
     * 普通安装
     *
     * @param context 上下文
     * @param apkPath path，文件路径
     */
    static boolean installNormal(Context context, String apkPath, String application_id) {
        if (apkPath == null) {
            return false;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            File apkFile = new File(apkPath);
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //版本在7.0以上是不能直接通过uri访问的
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
                Uri apkUri = FileProvider.getUriForFile(context, application_id + ".fileprovider", apkFile);
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            } else {
                Uri uri = Uri.fromFile(apkFile);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
            }
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 下载状态
     * START            开始下载
     * UPLOADING.       下载中
     * FINISH           下载完成，可以安装
     * ERROR            下载错误
     * PAUSED           下载暂停中，继续
     */
    @Retention(RetentionPolicy.SOURCE)
    public @interface DownloadStatus {
        int START = 6;
        int UPLOADING = 7;
        int FINISH = 8;
        int ERROR = 9;
        int PAUSED = 10;
    }


}
