package com.example.wen.wenplay.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wen.wenplay.R;
import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.common.imageloader.ImageLoader;

import java.util.List;

/**
 * Created by wen on 2017/3/15.
 */

public class HotAppInfoAdapter extends BaseQuickAdapter<AppInfo,BaseViewHolder> {

    String baseImgUrl ="http://file.market.xiaomi.com/mfc/thumbnail/png/w150q80/";

    public HotAppInfoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, AppInfo item) {
        ImageLoader.load(baseImgUrl+item.getIcon(), (ImageView) helper.getView(R.id.img_app_icon));
        helper.setText(R.id.txt_app_name,item.getDisplayName())
                .setText(R.id.txt_category,item.getLevel1CategoryName())
                .setText(R.id.txt_brief,item.getBriefShow());
    }
}
