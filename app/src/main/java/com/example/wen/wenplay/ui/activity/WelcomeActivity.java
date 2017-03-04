package com.example.wen.wenplay.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.eftimoff.androipathview.PathView;
import com.example.wen.wenplay.R;
import com.example.wen.wenplay.common.Constant;
import com.example.wen.wenplay.common.util.ACache;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.pathView)
    PathView mPathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        mPathView.getPathAnimator().delay(100).duration(5000)
                .listenerEnd(new PathView.AnimatorBuilder.ListenerEnd() {
                    @Override
                    public void onAnimationEnd() {
                        jump();
                    }
                })
                .interpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    private void jump() {

        String isShowGuide = ACache.get(this).getAsString(Constant.IS_SHOW_GUIDE);
        Intent intent;

        if (null == isShowGuide){ //第一次进入引导界面
            intent = new Intent(this,GuideActivity.class);
        }else {
            intent = new Intent(this,MainActivity.class);
        }

        startActivity(intent);
        this.finish();
    }
}
