package com.example.wen.wenplay.presenter.contract;


import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.bean.IndexBean;
import com.example.wen.wenplay.bean.PageBean;
import com.example.wen.wenplay.ui.BaseView;


/**
 * 契约类
 * Created by wen on 2017/2/28.
 */

public class AppInfoContract {
    //接口实现接口使用extends

    public interface RecommendView extends BaseView{
        void showResult(IndexBean data); //获取到数据后展示数据（初始化View）
    }

    public interface AppInfoView extends BaseView{
        void showResult(PageBean<AppInfo> data);
        void onLoadMoreComplete();
    }

}
