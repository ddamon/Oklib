package com.dm.learn.rxjava;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.dunkeng.R;
import com.dunkeng.common.Config;
import com.oklib.base.CoreBaseActivity;
import com.oklib.utils.view.SnackbarUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Damon.Han on 2017/2/9 0009.
 *
 * @author Damon
 */

public class ActX5FileDetail extends CoreBaseActivity implements TbsReaderView.ReaderCallback {

    private static final int REQUEST_CHOOSEFILE = 200;


    @BindView(R.id.X5TbsView)
    RelativeLayout mRelativeLayout;

    private RxPermissions rxPermissions;

    @Override
    public int getLayoutId() {
        return R.layout.act_web_file_details;
    }

    @Override
    public void initUI(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        rxPermissions = new RxPermissions(this);
        requestPermission();
    }

    private void requestPermission() {
        rxPermissions.request(Manifest.permission.READ_PHONE_STATE)
                .subscribe(permission -> {
                    if (permission) {
//                        String url = "https://view.officeapps.live.com/op/view.aspx?src=newnet.live/t.pptx";
//                        wvDetailContent.loadData(url,"","");
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        String PPT = "application/vnd.ms-powerpoint";
                        String PPTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
                        String[] mimeTypes = {PPT, PPTX};
                        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                        intent.setType("*/*");
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        startActivityForResult(intent, REQUEST_CHOOSEFILE);

                    } else {
                        showMsg("请授予权限");
                        requestPermission();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CHOOSEFILE:
                    Uri uri = data.getData();
                    String chooseFilePath = FileChooseUtil.getInstance(this).getChooseFileResultPath(uri);
                    Log.e(TAG, "选择文件返回：" + chooseFilePath);

                    /* 取得扩展名 */
                    File file = new File(chooseFilePath);
                    String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase();
                    if (end.equals("ppt") || end.equals("pptx")) {
                        FileModel fileModel = new FileModel();
                        fileModel.setPath(chooseFilePath);
                        displayFile(fileModel);
                    } else {
                        showMsg("请选择ppt格式文件");
                    }
                    break;
            }
        }
    }

    public static void start(Context context, View view, int id) {
        Intent intent = new Intent(context, ActX5FileDetail.class);
        intent.putExtra(Config.ArgumentKey.ARG_ZHIHU_ID, id);
        ActivityCompat.startActivity(context, intent, ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, view, "translate_view").toBundle());
    }


    public Context getContext() {
        return this;
    }

    public void showMsg(String msg) {
        SnackbarUtil.showShort(getWindow().getDecorView(), msg);
    }

    private String tbsReaderTemp = Environment.getExternalStorageDirectory() + "/TbsReaderTemp";
    TbsReaderView mTbsReaderView;

    public void displayFile(FileModel fileModel) {
        mTbsReaderView = new TbsReaderView(this, this);
        //增加下面一句解决没有TbsReaderTemp文件夹存在导致加载文件失败
        String bsReaderTemp = tbsReaderTemp;
        File bsReaderTempFile = new File(bsReaderTemp);

        if (!bsReaderTempFile.exists()) {
            Log.d("print", "文件不存在准备创建/TbsReaderTemp！！");
            boolean mkdir = bsReaderTempFile.mkdir();
            if (!mkdir) {
                Log.d("print", "创建/TbsReaderTemp失败！！！！！");
            }
            Toast.makeText(this, "文件不存在！", Toast.LENGTH_SHORT).show();
        }
        mRelativeLayout.addView(mTbsReaderView, new RelativeLayout.LayoutParams(-1, -1));
        Bundle bundle = new Bundle();
        bundle.putString("filePath", fileModel.getPath());
        bundle.putString("tempPath", tbsReaderTemp);
        boolean result = mTbsReaderView.preOpen(getFileType("word.pptx"), false);
        if (result) {
            mTbsReaderView.openFile(bundle);
        } else {

        }
    }

    private String getFileType(String paramString) {
        String str = "";
        if (TextUtils.isEmpty(paramString)) {
            Log.e("print", "paramString---->null");
            return str;
        }
        Log.d("print", "paramString:" + paramString);
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            Log.e("print", "i <= -1");
            return str;
        }

        str = paramString.substring(i + 1);
        Log.e("print", "paramString.substring(i + 1)------>" + str);
        return str;
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {
        Log.i("触摸监听：", " ");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTbsReaderView != null) {
            mTbsReaderView.onStop();
            mTbsReaderView.destroyDrawingCache();
        }
    }

    class FileModel {
        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }


}
