package com.example.wen.wenplay.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wen.wenplay.R;
import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.common.Constant;
import com.example.wen.wenplay.common.imageloader.ImageLoader;
import com.example.wen.wenplay.common.util.ACache;
import com.example.wen.wenplay.common.util.AppUtils;
import com.example.wen.wenplay.ui.widget.DownloadButtonController;
import com.example.wen.wenplay.ui.widget.DownloadProgressButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.io.File;
import java.util.Formatter;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadEvent;
import zlc.season.rxdownload2.entity.DownloadFlag;
import zlc.season.rxdownload2.entity.DownloadRecord;


/**
 * Created by wen on 2017/3/15.
 */

public class AppInfoAdapter extends BaseQuickAdapter<AppInfo,BaseViewHolder> {

    String baseImgUrl ="http://file.market.xiaomi.com/mfc/thumbnail/png/w150q80/";

    private Builder mBuilder;
    private DownloadButtonController mDownloadButtonController;

    public AppInfoAdapter(Builder builder) {
        super(builder.layoutId);
        this.mBuilder = builder;

        mDownloadButtonController = new DownloadButtonController(builder.mRxDownload);
        openLoadAnimation();
    }

    public static Builder Builder(){
        return new Builder();
    }

    @Override
    protected void convert(BaseViewHolder helper, final AppInfo item) {

        ImageLoader.load(Constant.BASE_IMG_URL+item.getIcon(), (ImageView) helper.getView(R.id.img_app_icon));
        helper.setText(R.id.txt_app_name,item.getDisplayName());



        if (isNeedToUpdate(item)){
            item.setUpdate(true);
        }else {
            item.setUpdate(false);
        }





        if (mBuilder.isUpdate){//需要更新

            ExpandableTextView changelog = helper.getView(R.id.view_changelog);
            changelog.setText(item.getChangeLog());
            TextView textViewSize = helper.getView(R.id.txt_apk_size);
            if(textViewSize !=null){
                String s = android.text.format.Formatter.formatFileSize(mContext, item.getApkSize());

                // textViewSize.setText((item.getApkSize() / 1014 / 1024) +"Mb");
                textViewSize.setText("v"+item.getVersionCode() +" "+ s);
            }

           // item.setUpdate(true);


        }else {

          //  item.setUpdate(false);

            TextView textViewCategoryName = helper.getView(R.id.txt_category);
            if(textViewCategoryName !=null) {
                textViewCategoryName.setVisibility(mBuilder.isShowCategoryName ? View.VISIBLE : View.GONE);
                textViewCategoryName.setText(item.getLevel1CategoryName());
            }

            TextView textViewBrief = helper.getView(R.id.txt_brief);
            if(textViewCategoryName !=null) {
                textViewBrief.setVisibility(mBuilder.isShowBrief ? View.VISIBLE : View.GONE);
                textViewBrief.setText(item.getBriefShow());
            }


            TextView textViewSize = helper.getView(R.id.txt_apk_size);

            if(textViewSize !=null){
                String s = android.text.format.Formatter.formatFileSize(mContext, item.getApkSize());

                // textViewSize.setText((item.getApkSize() / 1014 / 1024) +"Mb");
                textViewSize.setText(s);
            }


        }

        TextView txtViewPosition = helper.getView(R.id.txt_position);
        if(txtViewPosition !=null) {
            txtViewPosition.setVisibility(mBuilder.isShowPosition ? View.VISIBLE : View.GONE);
            txtViewPosition.setText((item.getPosition() + 1) + " .");
        }






        //添加點擊事件
        helper.addOnClickListener(R.id.btn_download);

        //進行強制類型轉換，否則出錯
        View viewBtn  = helper.getView(R.id.btn_download);

        if (viewBtn instanceof  DownloadProgressButton){

            final DownloadProgressButton btn = (DownloadProgressButton) viewBtn;

            mDownloadButtonController.handClick(btn,item);


        }


    }

    private boolean isFileExist(Context context,AppInfo appInfo) {
        String downloadDir = ACache.get(context).getAsString(Constant.APK_DOWNLOAD_DIR);

        //下載地址約束
        String path = downloadDir + File.separator + appInfo.getReleaseKeyHash()+".apk";

        File file = new File(path);

        return file.exists();
    }


    public boolean isNeedToUpdate(AppInfo item){

        int i = 0;
        String appInfoString = ACache.get(mContext).getAsString(Constant.UPDATE_APPINFO_KEY);

        if (! TextUtils.isEmpty(appInfoString)) {
            Gson gson = new Gson();

            List<AppInfo> appInfoList =  gson.fromJson(appInfoString,new TypeToken<List<AppInfo>>(){}.getType());

            for (AppInfo appInfo:appInfoList){
                if (appInfo.getVersionCode() == item.getVersionCode()){
                    i++;
                }
            }

        }


        return i == 0?false:true;

    }


    public static class  Builder{

        private boolean isShowPosition;
        private boolean isShowCategoryName;
        private boolean isShowBrief;
        private boolean isUpdate;
        private RxDownload mRxDownload;
        private int layoutId = R.layout.item_appinfo;

        public Builder showPosition(boolean b){
            this.isShowPosition =b;
            return this;
        }


        public Builder showCategoryName(boolean b){
            this.isShowCategoryName =b;
            return this;
        }


        public Builder showBrief(boolean b){
            this.isShowBrief =b;
            return this;
        }
        public Builder isUpdate(boolean b){
            this.isUpdate =b;
            return this;
        }

        public Builder layout(int layoutId){
           this.layoutId = layoutId;
            return this;
        }

        public Builder rxDownload(RxDownload rxDownload){
            this.mRxDownload = rxDownload;
            return this;
        }

        public AppInfoAdapter build(){
            return  new AppInfoAdapter(this);
        }
    }

}

