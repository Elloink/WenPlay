package com.example.wen.wenplay.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wen.wenplay.R;

import java.util.List;

/**
 * Created by wen on 2017/6/1.
 */

public class SuggestionAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public SuggestionAdapter() {
        super(R.layout.item_suggestion);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_suggestion,item);
    }
}
