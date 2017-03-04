package com.example.wen.wenplay.ui.fragment;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wen.wenplay.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wen on 2017/3/4.
 */

public class GuideFragment extends Fragment {

    private static final String IMG_ID = "IMG_ID";
    private static final String TEXT_ID = "TEXT_ID";
    private static final String COLOR_ID = "COLOR_ID";

    @BindView(R.id.iv_guide_image)
    ImageView mGuideImage;
    @BindView(R.id.tv_guide_text)
    TextView mGuideText;
    @BindView(R.id.root_view)
    LinearLayout mRootView;

    private View mView;

    public static GuideFragment newInstance(@DrawableRes int guideImageResId, @StringRes int guideTextResId, @ColorRes int backgroundColorResId) {

        Bundle args = new Bundle();

        args.putInt(IMG_ID, guideImageResId);
        args.putInt(TEXT_ID, guideTextResId);
        args.putInt(COLOR_ID, backgroundColorResId);

        GuideFragment fragment = new GuideFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_guide, container, false);
        ButterKnife.bind(this, mView);

        bindData();

        return mView;
    }

    private void bindData() {

        Bundle args = getArguments();

        int imageId = args.getInt(IMG_ID);
        int textId = args.getInt(TEXT_ID);
        int colorId = args.getInt(COLOR_ID);

        mGuideImage.setImageResource(imageId);
        mGuideText.setText(textId);
        mRootView.setBackgroundColor(getResources().getColor(colorId));
    }
}
