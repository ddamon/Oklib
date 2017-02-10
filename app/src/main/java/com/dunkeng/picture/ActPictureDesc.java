package com.dunkeng.picture;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

import com.bumptech.glide.Glide;
import com.dunkeng.R;
import com.dunkeng.common.Config;
import com.dunkeng.common.DunkengFileUtil;
import com.dunkeng.picture.model.PictureBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoView;

/**
 *
 */
public class ActPictureDesc extends AppCompatActivity {
    @BindView(R.id.image)
    PhotoView imgPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pic);
        ButterKnife.bind(this);
        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        final PictureBean data = (PictureBean) bundle.getSerializable(Config.ArgumentKey.ARG_PICTURE_BEAN);
        final DisplayMetrics dm = getResources().getDisplayMetrics();
        Glide.with(this).load(data.url).into(imgPicture);
        imgPicture.setDrawingCacheEnabled(true);
        imgPicture.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(ActPictureDesc.this)
                        .setMessage("保存图片")
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface anInterface, int i) {
                                anInterface.dismiss();
                            }
                        })
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface anInterface, int i) {

                                anInterface.dismiss();
                                DunkengFileUtil.saveImage(imgPicture, ActPictureDesc.this);
                            }
                        }).show();
                return true;
            }
        });

    }

    public static void start(Context context, View view, PictureBean pictureBean) {
        Intent intent = new Intent(context, ActPictureDesc.class);
        intent.putExtra(Config.ArgumentKey.ARG_PICTURE_BEAN, pictureBean);
        ActivityCompat.startActivity(context, intent, ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, view, "translate_view").toBundle());
    }

    @OnClick(R.id.image)
    public void onClick() {
        finish();
    }
}
