package com.oklib.widget.imageloader.glide;

/**
 * Copyright (C) 2015 Wasabeef
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

public class CircleBorderTransformation extends BitmapTransformation {
    private int borderWidth;
    private int borderColor;

    public CircleBorderTransformation(Context context, int borderWidth) {
        super(context);
        this.borderWidth = borderWidth;
        borderColor = Color.WHITE;
    }

    public CircleBorderTransformation(Context context, int borderWidth, int borderColor) {
        super(context);
        this.borderWidth = borderWidth;
        this.borderColor = borderColor;
    }

    public CircleBorderTransformation(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) {
            return null;
        }
        int borderRadius = 3;
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        // TODO this could be acquired from the pool too
        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
        if (squared != source) {
            source.recycle();
        }
        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        // Prepare the background
        if (borderWidth > 0) {
            Paint paintBg = new Paint();
            paintBg.setColor(borderColor);
            paintBg.setAntiAlias(true);
            // Draw the background circle
            canvas.drawCircle(r, r, r, paintBg);
        }
        // Draw the image smaller than the background so a little border will be seen
        canvas.drawCircle(r, r, r - borderRadius, paint);
        squared.recycle();
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
