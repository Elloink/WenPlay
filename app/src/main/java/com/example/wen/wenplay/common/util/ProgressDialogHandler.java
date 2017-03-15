package com.example.wen.wenplay.common.util;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;

import com.example.wen.wenplay.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by wen on 2017/3/11.
 */

public class ProgressDialogHandler extends Handler {
    private static final int SHOW_PROGRESS_DIALOG = 1;
    private static final int DISMISS_PROGRESS_DIALOG = 0;

    private SweetAlertDialog mSweetAlertDialog;
    private Context mContext;
    private boolean cancelable; //是否显示取消关闭按钮
    private OnProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(Context context) {
        //默认不取消
        this(context,false,null);
    }

    public ProgressDialogHandler(Context context, boolean cancelable, OnProgressCancelListener progressCancelListener) {
        super();
        mContext = context;
        this.cancelable = cancelable;
        mProgressCancelListener = progressCancelListener;

        initSweetProgressDialog();
    }

    private void initSweetProgressDialog(){

        if (mSweetAlertDialog == null){

            mSweetAlertDialog = new SweetAlertDialog(mContext,SweetAlertDialog.PROGRESS_TYPE);
            mSweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            mSweetAlertDialog.setTitleText(mContext.getResources().getString(R.string.loading));
        }

        if (cancelable){//如果progressDialog可取消
            mSweetAlertDialog.setCancelText(mContext.getResources().getString(R.string.close));
            mSweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.cancel();
                    if (mProgressCancelListener != null){
                        mProgressCancelListener.onCancelProgress();
                    }
                }
            });
        }

    }

    public void showProgressDialog(){

        if(mSweetAlertDialog != null && !mSweetAlertDialog.isShowing()){
            mSweetAlertDialog.show();
        }
    }

    public void dismissProgressDialog(){
        if (mSweetAlertDialog != null) {
            mSweetAlertDialog.dismiss();
            mSweetAlertDialog = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                showProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }

    public interface OnProgressCancelListener{

        void onCancelProgress();
    }
}
