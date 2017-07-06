package com.example.wen.wenplay.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ActionProvider;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wen.wenplay.R;

/**
 * Created by wen on 2017/5/24.
 */

public class ToolbarActionProvider extends ActionProvider {

    private Context mContext;

    private ImageView mIcon;

    private TextView mTextBadge;

    private View.OnClickListener mOnClickListener;

    /**
     * Creates a new instance.
     *
     * @param context Context for accessing resources.
     */
    public ToolbarActionProvider(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public View onCreateActionView() {

        View view = View.inflate(mContext, R.layout.toolbar_action_provider,null);

        int size = mContext.getResources().getDimensionPixelSize(android.support.design.R.dimen.abc_action_bar_default_height_material);

        ViewGroup.LayoutParams layoutParams =  new ViewGroup.LayoutParams(size,size);

        view.setLayoutParams(layoutParams);

        mIcon = (ImageView) view.findViewById(R.id.img_icon);

        mTextBadge = (TextView) view.findViewById(R.id.txt_badge);

        view.setOnClickListener(new OnBadgeClickListener());

        return view;
    }

    public void  setIcon(Drawable drawable){

        this.mIcon.setImageDrawable(drawable);

    }


    public void setIcon(@DrawableRes int res){


        this.mIcon.setImageResource(res);
    }


    // 设置显示的文字。
    public void setText(CharSequence i) {
        showBadge();
        mTextBadge.setText(i);
    }


    public int  getBadgeNum(){

        try {
            return Integer.parseInt(mTextBadge.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void hideBadge(){
        mTextBadge.setVisibility(View.GONE);
    }

    public void showBadge(){
        mTextBadge.setVisibility(View.VISIBLE);
    }


    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }



    public class OnBadgeClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (mOnClickListener != null){
                mOnClickListener.onClick(v);
            }
        }
    }



}
