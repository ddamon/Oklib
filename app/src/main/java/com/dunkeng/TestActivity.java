package com.dunkeng;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oklib.base.CoreBaseActivity;
import com.oklib.widget.dialog.ConfirmDialog;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class TestActivity extends CoreBaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initUI(Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolBar(toolbar, "关于");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long t1 = System.currentTimeMillis();
                textView.setText("读取文件中...");
                String jsonStr = readAssetsTxt(mContext, "Age0Sex1.txt");
                long t2 = System.currentTimeMillis();
                textView.setText(textView.getText().toString() + "\n" + "读取完毕,耗时：" + (t2 - t1));

                textView.setText(textView.getText().toString() + "\n" + "转化map...");
                Map<String, Double> map = new Gson().fromJson(jsonStr, new TypeToken<HashMap<String, Double>>() {
                }.getType());
                long t3 = System.currentTimeMillis();

                textView.setText(textView.getText().toString() + "\n" + "转化map完成，总数：" + map.size() + "/t 耗时：" + (t3 - t2));

                long t4 = System.currentTimeMillis();
                double d = map.get("0_1_91_5");
                textView.setText(textView.getText().toString() + "\n" + "查找 key = 0_1_91_5：value = " + d + "/t 耗时：" + (t4 - t3));

            }
        });

        ConfirmDialog confirmDialog = new ConfirmDialog(mContext);
        confirmDialog.setCancelable(false);
        confirmDialog.setMessage("message").setPositiveButton("知道了", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog.dismiss();
//                finish();
            }
        }).show();
    }

    /**
     * 读取assets下的txt文件，返回utf-8 String
     *
     * @param context
     * @param fileName 不包括后缀
     * @return
     */
    public static String readAssetsTxt(Context context, String fileName) {
        try {
            //Return an AssetManager instance for your application's package
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            String text = new String(buffer, "utf-8");
            // Finally stick the string into the text view.
            return text;
        } catch (IOException e) {
            // Should never happen!
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        return "读取错误，请检查文件名";
    }
}
