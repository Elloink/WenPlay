package com.example.wen.wenplay.data.db;

import org.litepal.crud.DataSupport;


/**
 * Created by wen on 2017/5/31.
 */

public class SearchKeyWord extends DataSupport{
    private String keyWord;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
