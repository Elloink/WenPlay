package com.example.wen.wenplay.ui.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by wen on 2017/5/26.
 */

public class SubjectDividerItemDecoration extends RecyclerView.ItemDecoration {
    private float mDividerHeight;
    private float mDividerWidth;
    private Paint mPaint;

    public SubjectDividerItemDecoration(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
        super.onDraw(c, parent);

        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++){
            View child = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(child);



            if (index == 0 || index % 2 == 0){
                //第一列右侧绘制垂直分割线
                float dividerTop = child.getTop();
                float dividerLeft = child.getLeft()+child.getWidth();
                float dividerBottom = child.getBottom();
                float dividerRight = child.getRight() + mDividerWidth;
                c.drawRect(dividerLeft,dividerTop,dividerRight,dividerBottom,mPaint);
            }

            if (index==0 || index == 1){
                //第一个和第二个上门不需要水平分割线
                continue;
            }else {
                //绘制水平分割线
                float dividerTop = child.getTop()-mDividerHeight;
                float dividerLeft = child.getLeft();
                float dividerBottom = child.getBottom();
                float dividerRight = dividerLeft + child.getWidth();

                c.drawRect(dividerLeft,dividerTop,dividerRight,dividerBottom,mPaint);
            }

        }


    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        //第一和第二个Item不需要再上门回执分割线
        if (parent.getChildAdapterPosition(view) != 0 || parent.getChildAdapterPosition(view) != 1 ){
            outRect.top = 4;
            mDividerHeight = 4;
        }
        //第一列右边需要分割线
        if (parent.getChildAdapterPosition(view)%2 == 0){
            outRect.right = 4;
            mDividerWidth = 4;
        }

    }
}
