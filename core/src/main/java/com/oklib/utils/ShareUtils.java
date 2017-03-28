package com.oklib.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by Damon.Han on 2017/3/28 0028.
 *
 * @author Damon
 */

public class ShareUtils {
    private Context context;

    public ShareUtils(Context context) {
        this.context = context;
    }

    public void shareText(String text) {
        Intent intent1 = new Intent(Intent.ACTION_SEND);
        intent1.putExtra(Intent.EXTRA_TEXT, text);
        intent1.setType("text/plain");
        context.startActivity(Intent.createChooser(intent1, "share"));
    }

    public void shareImageFile(File file) {
        Intent intent2 = new Intent(Intent.ACTION_SEND);
        Uri uri = Uri.fromFile(file);
        intent2.putExtra(Intent.EXTRA_STREAM, uri);
        intent2.setType("image/*");
        context.startActivity(Intent.createChooser(intent2, "share"));
    }

}
