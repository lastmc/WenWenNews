package com.java.yandifei.network;

import android.app.Application;

public class MyApplication extends Application {

    public String newsListTableName;
    public String readNewsListTableName;
    public String tabsListTableName;

    @Override
    public void onCreate() {
        newsListTableName = "newsListTableName";
        readNewsListTableName = "readNewsListTableName";
        tabsListTableName = "tabsListTableName";
        super.onCreate();
    }
}
