package com.example.wen.wenplay.bean.requestbean;

/**
 * Created by wen on 2017/5/24.
 */

public class AppUpdateBean {
    private String packageName;

    private String versionCode;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }
}
