package com.example.wen.wenplay.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wen.wenplay.R;
import com.example.wen.wenplay.bean.Subject;
import com.example.wen.wenplay.common.Constant;
import com.example.wen.wenplay.common.imageloader.ImageLoader;
import com.example.wen.wenplay.common.util.DensityUtil;
import com.example.wen.wenplay.common.util.DeviceUtils;

/**
 * Created by wen on 2017/5/26.
 */

public class SubjectAdapter extends BaseQuickAdapter<Subject,BaseViewHolder> {
    public SubjectAdapter() {
        super(R.layout.template_image_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, Subject item) {
        ImageView imageView = helper.getView(R.id.imageview);

        int screenW = DensityUtil.getScreenW(imageView.getContext());

        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) imageView.getLayoutParams();

        layoutParams.width = screenW/2 - 5;
        layoutParams.height = 600;

        imageView.setLayoutParams(layoutParams);

        String url = Constant.BASE_IMG_URL+item.getMticon();
        ImageLoader.load(url,imageView);

    }
}
