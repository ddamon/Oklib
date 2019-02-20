package com.oklib.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.oklib.utils.assist.StringUtils;

import java.io.File;
import java.util.List;

public class IntentUtils {

    private IntentUtils() {
    }

    // android获取一个用于打开HTML文件的intent(使用默认的htmlviewer)
//	public static Intent getHtmlFileIntent(File file) {
//		Uri uri = Uri.parse(file.toString()).buildUpon()
//				.encodedAuthority("com.android.htmlfileprovider")
//				.scheme("content").encodedPath(file.toString()).build();
//		Intent intent = new Intent("android.intent.action.VIEW");
//		intent.setDataAndType(uri, "text/html");
//		return intent;
//	}

    // android获取一个用于打开HTML文件的intent(使用默认浏览器)
    public static Intent getHtmlFileBrowserIntent(File file) {
        System.out.println(file.getName() + "-=");
        Uri content_url = Uri.parse("file://" + file.getAbsolutePath());
        // Uri uri =
        // Uri.parse(file.toString()).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(file.toString()).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(content_url);
        intent.setClassName("com.android.browser",
                "com.android.browser.BrowserActivity");
        return intent;
    }

    // android获取一个用于打开HTML文件的intent(使用默认浏览器)
    public static Intent getUrlIntent(String url) {
        Uri content_url = Uri.parse(url);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(content_url);
        return intent;
    }

    /**
     * android获取一个用于打开HTML文件的intent(使用默认浏览器)
     *
     * @param param
     * @return
     */
    public static Intent getHtmlFileIntent(String param) {
        Uri uri = Uri.parse(param).buildUpon()
                .encodedAuthority("com.android.htmlfileprovider")
                .scheme("content").encodedPath(param).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;

    }

    // android获取一个用于打开图片文件的intent
    public static Intent getImageFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    // android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    // android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "text/plain");
        return intent;
    }

    // android获取一个用于打开音频文件的intent
    public static Intent getAudioFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    // android获取一个用于打开视频文件的intent
    public static Intent getVideoFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    // android获取一个用于打开CHM文件的intent
    public static Intent getChmFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    // android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    // android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    // android获取一个用于打开PPT文件的intent
    public static Intent getPPTFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    // android获取一个用于打开apk文件的intent
    public static Intent getApkFileIntent(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        return intent;
    }

    // android获取一个执行应用程序的intent
    public static Intent getApkFileIntent(ComponentName cn) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(cn);
        return intent;
    }

    /**
     * 设置网络的intent
     *
     * @param
     * @return
     */
    public static Intent getWifiIntent() {
        Intent intent = new Intent();
        intent.setAction("android.settings.WIRELESS_SETTINGS");
        return intent;
    }

    public static Intent getUninstallIntent(String packageName) {
        Uri uri = Uri.parse("package:" + packageName);
        Intent intent = new Intent();
        intent.setType(Intent.ACTION_DELETE);
        intent.setData(uri);
        // startActivityForResult(intent,Activity.START_UNINSTALL_REQUEST_CODE);
        return intent;

    }

    /**
     * 解析intent字符串获得intent对象
     *
     * @param intentString
     * @return
     */
    public static Intent getShortcutIntent(String intentString) {
        if (DataValidation.isEmpty(intentString)) {
            return null;
        }
        Intent intent = new Intent();
        intentString.replace(";end", "");
        String[] uriAndOthers = intentString.split("#Intent;");
        String uri = uriAndOthers[0];
        String otherString = uriAndOthers[1];
        intent.setData(Uri.parse(uri));
        String[] othersArray = otherString.split(";");
        String tmp = null;

        for (int i = 0; i < othersArray.length; i++) {
            tmp = othersArray[i];
            String keyValueArray[] = tmp.split("=");
            if (keyValueArray.length == 2) {
                String name = keyValueArray[0];
                String value = keyValueArray[1];
                if (name.equals("action")) {
                    intent.setAction(value);
                    continue;
                } else if (name.equals("category")) {
                    intent.addCategory(value);
                    continue;
                } else if (name.equals("component")) {
                    String[] kv = value.split("/");
                    if (kv.length == 2) {
                        String pName = kv[0];
                        String className = "";
                        if (kv[1].startsWith(".")) {
                            className = pName + kv[1];
                        } else {
                            className = kv[1];
                        }
                        ComponentName cn = new ComponentName(pName, className);
                        intent.setComponent(cn);
                    }
                    continue;
                } else {
                    intent.putExtra(name, value);
                    continue;
                }
            }
        }
        return intent;
    }

    /**
     * 获得权限
     *
     * @param context
     * @param permission
     * @return
     */
    public static String getAuthorityFromPermission(Context context,
                                                    String permission) {
        if (permission == null) {
            return null;
        }
        List<PackageInfo> packs = context.getPackageManager()
                .getInstalledPackages(PackageManager.GET_PROVIDERS);
        if (packs != null) {
            for (PackageInfo pack : packs) {
                ProviderInfo[] providers = pack.providers;
                if (providers != null) {
                    for (ProviderInfo provider : providers) {
                        if (permission.equals(provider.readPermission)) {
                            return provider.authority;
                        }
                        if (permission.equals(provider.writePermission)) {
                            return provider.authority;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取启动launcher的路径
     *
     * @param context
     * @return
     */
    public static String getLauncherPackageName(Context context) {
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        final ResolveInfo res = context.getPackageManager().resolveActivity(
                intent, 0);
        if (res.activityInfo == null) {
            // should not happen. A home is always installed, isn't it?
            return null;
        }
        if (res.activityInfo.packageName.equals("android")) {
            // 有多个桌面程序存在，且未指定默认项时；
            return null;
        } else {
            return res.activityInfo.packageName;
        }
    }

    /*
     * 根据程序包名获取程序 图标
     */
    public static Drawable getAppIcon(Context context, String packname) {
        if (packname == null) {
            return null;
        }
        PackageManager pm = context.getPackageManager();
        try {
            return pm.getApplicationIcon(packname);
            // ApplicationInfo info = pm.getApplicationInfo(packname, 0);
            // return info.loadIcon(pm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 采用了新的办法获取APK图标，之前的失败是因为android中存在的一个BUG,通过
     * appInfo.publicSourceDir = apkPath;来修正这个问题，详情参见:
     * http://code.google.com/p/android/issues/detail?id=9151
     */
    public static Drawable getApkIcon(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            try {
                return appInfo.loadIcon(pm);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 跳转
     *
     * @param context
     * @param cls
     * @param data
     * @param flags
     * @param action
     */
    public static void intentToActivity(Context context, Class<?> cls, Bundle data, int[] flags, String action) {
        Intent intent = new Intent(context, cls);
        if (data != null) {
            intent.putExtras(data);
        }
        if (flags != null && flags.length > 0) {
            for (int i = 0; i < flags.length; i++) {
                intent.setFlags(flags[i]);
            }
        }
        if (!StringUtils.isEmpty(action)) {
            intent.setAction(action);
        }
        context.startActivity(intent);
    }

    /**
     * 获取日历intent
     *
     * @return
     */
    public static Intent getCalendarIntent() {
        Intent i = new Intent();
        ComponentName cn = null;
        if (Integer.parseInt(Build.VERSION.SDK) >= 8) {
            cn = new ComponentName("com.android.calendar", "com.android.calendar.LaunchActivity");
        } else {
            cn = new ComponentName("com.google.android.calendar", "com.android.calendar.LaunchActivity");
        }
        i.setComponent(cn);
        return i;
    }
}
