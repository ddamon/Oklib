package com.dunkeng;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.dunkeng.main.MainActivity;
import com.oklib.base.CoreBaseActivity;
import com.oklib.widget.dialog.ConfirmDialog;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Damon.Han
 */
public class IndexActivity extends CoreBaseActivity {

    private RxPermissions permissions;
    private ConfirmDialog confirmDialog;
    private String appName;
    private Map<String, Permission> allNeadPermissions;

    private void getPermission() {
        appName = getResources().getString(R.string.app_name);
        if (allNeadPermissions == null) {
            allNeadPermissions = new HashMap<>();
        }
        if (permissions == null) {
            permissions = new RxPermissions(IndexActivity.this);
        }
        permissions
                .requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
                .subscribe(permission -> {
                    allNeadPermissions.put(permission.name, permission);
                    //全部权限请求完毕
                    if (allNeadPermissions.size() == 2) {
                        Permission externalPermission = allNeadPermissions.get(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        Permission readPhonePermission = allNeadPermissions.get(Manifest.permission.READ_PHONE_STATE);
                        if (externalPermission.granted && readPhonePermission.granted) {
                            initApp();
                            forwardToAct();
                        } else {
                            allNeadPermissions.clear();
                            Permission deniedPermission = !externalPermission.granted ? externalPermission : readPhonePermission;
                            showConfirmDialog(deniedPermission, !deniedPermission.shouldShowRequestPermissionRationale);
                        }
                    }
                });
    }

    /**
     * 在获取权限的情况下做一些初始化工作
     */
    private void initApp() {

    }

    private void showConfirmDialog(Permission permission, boolean gotoSet) {
        if (confirmDialog == null) {
            confirmDialog = new ConfirmDialog(this);
            confirmDialog.setCanceledOnTouchOutside(false);
            confirmDialog.setCancelable(false);
            confirmDialog.setMessage("我们需要这些权限，为你提供服务;否则你将无法使用" + appName);
            confirmDialog.setNevigateButton("取消", view -> {
                confirmDialog.dismiss();
                IndexActivity.this.finish();
            });
        }

        if (permission != null && permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            confirmDialog.setMessage("我们需要获取存储空间权限，为你存储个人信息；否则你将无法使用" + appName);
        }

        if (permission != null && permission.name.equals(Manifest.permission.READ_PHONE_STATE)) {
            confirmDialog.setMessage("我们需要获取设备信息，为你进行设备识别；否则你将无法使用" + appName);
        }
        if (gotoSet) {
            confirmDialog.setPositiveButton("去设置", view -> {
                confirmDialog.dismiss();
                Intent localIntent = new Intent();
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(localIntent);
                IndexActivity.this.finish();
            });

        } else {
            confirmDialog.setPositiveButton("确定", view -> {
                confirmDialog.dismiss();
                getPermission();
            });
        }
        confirmDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (confirmDialog != null) {
            confirmDialog.dismiss();
            confirmDialog = null;
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_index;
    }

    @Override
    public void initUI(Bundle savedInstanceState) {
        if (!isTaskRoot()) {
            finish();
            return;
        }
        getPermission();
    }

    /**
     * 根据是否初次安装选择跳转页面
     */
    private void forwardToAct() {
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoMain();
            }
        }, 2000);
    }

    /**
     *
     */
    private void gotoMain() {
        Intent intent = new Intent();
        intent.setClass(IndexActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}