package com.example.wen.wenplay.bean.requestbean;

import com.example.wen.wenplay.bean.BaseEntity;

/**
 * Created by wen on 2017/3/13.
 */

public class LoginRequestBean extends BaseEntity {

    private String email;

    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
