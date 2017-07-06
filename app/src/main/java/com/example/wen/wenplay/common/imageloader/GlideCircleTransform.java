package com.example.wen.wenplay.common.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by wen on 2017/3/18.
 */

public class GlideCircleTransform extends BitmapTransformation {

    public GlideCircleTransform(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null){
            return null;
        }

        //截取出需要转换的bitmap
        int size = Math.min(source.getWidth(),source.getHeight());
        int x = (source.getWidth() - size)/2;
        int y = (source.getHeight() - size)/2;

        Bitmap toTransform = Bitmap.createBitmap(source,x,y,size,size);

        //拿到有固定宽高并配置过的bitmap
        Bitmap result = pool.get(size,size, Bitmap.Config.ARGB_8888);
        if (result == null){
            result = Bitmap.createBitmap(size,size, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(toTransform, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        return result;

    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
