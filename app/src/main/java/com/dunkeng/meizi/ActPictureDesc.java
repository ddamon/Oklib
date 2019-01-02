package com.dunkeng.meizi;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dunkeng.R;
import com.dunkeng.common.Config;
import com.dunkeng.common.DunkengFileUtil;
import com.dunkeng.meizi.model.PictureBean;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.github.chrisbanes.photoview.PhotoViewAttacher.*;

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
        PictureBean data = (PictureBean) bundle.getSerializable(Config.ArgumentKey.ARG_PICTURE_BEAN);
        Glide.with(this).load(data.url).into(imgPicture);
        imgPicture.setDrawingCacheEnabled(true);
        imgPicture.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(ActPictureDesc.this)
                        .setMessage("保存图片 \n " + Config.APP_CARD_PATH_PICTURE)
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
        imgPicture.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {

            }
        });
    }

    public static void start(Context context, View view, PictureBean pictureBean) {
        Intent intent = new Intent(context, ActPictureDesc.class);
        intent.putExtra(Config.ArgumentKey.ARG_PICTURE_BEAN, pictureBean);
        ActivityCompat.startActivity(context, intent, ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, view, "translate_view").toBundle());
    }
}
