package com.example.wen.wenplay.common.imageloader;

import android.graphics.Bitmap;

/**
 * Created by wen on 2017/3/14.
 */

public interface BitmapLoadingListener {

    void onSuccess(Bitmap b);

    void onError();
}

