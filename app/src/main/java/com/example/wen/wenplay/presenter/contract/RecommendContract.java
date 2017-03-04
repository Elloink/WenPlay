package com.example.wen.wenplay.presenter.contract;

import com.example.wen.wenplay.bean.AppInfo;
import com.example.wen.wenplay.ui.BaseView;

import java.util.List;

/**
 * Created by wen on 2017/2/28.
 */

public class RecommendContract {
    //接口实现接口使用extends

    public interface RecommendView extends BaseView{

        //每个界面获取数据时都需要的方法，向上抽取到BaseView
      /*  void showLoading(); //获取到数据前提示Loading
        void dismissLoading(); //获取数据完成或出错隐藏Loading*/

        void showResult(List<AppInfo> data); //获取到数据后展示数据（初始化View）
        void showNoData();  //无数据时提示没有数据
        void showError(String msg); //获取失败提示错误信息
    }

}
