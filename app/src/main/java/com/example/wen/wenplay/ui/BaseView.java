package com.example.wen.wenplay.ui;

/**
 * 负责整合view的公共方法
 * Created by wen on 2017/2/28.
 */

public interface BaseView {
    void showLoading(); //获取到数据前提示Loading
    void dismissLoading(); //获取数据完成或出错隐藏Loading
}
