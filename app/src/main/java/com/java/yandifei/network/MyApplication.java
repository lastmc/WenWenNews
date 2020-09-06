package com.java.yandifei.network;

import android.app.Application;

public class MyApplication extends Application {

    public String newsListTableName;
    public String readNewsListTableName;

    @Override
    public void onCreate() {
        newsListTableName = "newsListTableName";
        readNewsListTableName = "readNewsListTableName";
        super.onCreate();
    }
}
