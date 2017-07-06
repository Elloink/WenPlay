package com.example.wen.wenplay.bean;

import com.google.gson.Gson;

/**
 * Created by wen on 2017/3/16.
 */

public class User extends BaseEntity {

    private String id;
    private String email;
    private String logo_url;
    private String username;
    private String mobi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobi() {
        return mobi;
    }

    public void setMobi(String mobi) {
        this.mobi = mobi;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
