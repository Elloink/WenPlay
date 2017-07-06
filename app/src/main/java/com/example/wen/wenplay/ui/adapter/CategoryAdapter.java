package com.example.wen.wenplay.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wen.wenplay.R;
import com.example.wen.wenplay.bean.Category;
import com.example.wen.wenplay.common.Constant;
import com.example.wen.wenplay.common.imageloader.ImageLoader;

/**
 * Created by wen on 2017/3/18.
 */

public class CategoryAdapter extends BaseQuickAdapter<Category,BaseViewHolder> {


    public CategoryAdapter() {

        super(R.layout.template_category);

    }

    @Override
    protected void convert(BaseViewHolder helper, Category category) {
        helper.setText(R.id.text_name,category.getName());

        ImageLoader.load(Constant.BASE_IMG_URL+category.getIcon(), (ImageView) helper.getView(R.id.img_icon));
    }
}
