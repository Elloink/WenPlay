package com.example.wen.wenplay.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wen.wenplay.R;
import com.example.wen.wenplay.common.Constant;
import com.example.wen.wenplay.common.imageloader.ImageLoader;
import com.example.wen.wenplay.ui.widget.DownloadButtonController;
import com.example.wen.wenplay.ui.widget.DownloadProgressButton;

import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 * Created by wen on 2017/5/20.
 */

public class DownloadFragmentAdapter extends BaseQuickAdapter<DownloadRecord,BaseViewHolder> {

    private DownloadButtonController downloadButtonController;

    public DownloadFragmentAdapter(RxDownload rxDownload) {
        super(R.layout.fragment_download);
        downloadButtonController = new DownloadButtonController(rxDownload);
    }

    @Override
    protected void convert(BaseViewHolder helper, DownloadRecord item) {

        //下载按钮
        View btn = helper.getView(R.id.btn_download);
        DownloadProgressButton downloadButton;
        //必须加上
        helper.addOnClickListener(R.id.btn_download);

        if (btn instanceof DownloadProgressButton){

            downloadButton = (DownloadProgressButton) btn;

            downloadButtonController.handClick(downloadButton,item);

        }

        String icon = item.getExtra2();
        String appName = item.getExtra3();

        //icon
        ImageView appIcon = helper.getView(R.id.img_app_icon);

        ImageLoader.load(Constant.BASE_IMG_URL + icon,appIcon);
        helper.setText(R.id.txt_app_name,appName);

    }
}
