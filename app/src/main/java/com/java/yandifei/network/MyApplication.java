package com.java.yandifei.network;

import android.app.Application;

public class MyApplication extends Application {

    public String newsListTableName;
    public String readNewsListTableName;
    public String searchHistory;

    @Override
    public void onCreate() {
        newsListTableName = "newsListTableName";
        readNewsListTableName = "readNewsListTableName";
        searchHistory = "searchHistory";
        super.onCreate();
    }
}
