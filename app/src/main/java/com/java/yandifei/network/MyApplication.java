package com.java.yandifei.network;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    public String newsListTableName;
    public String readNewsListTableName;
    public String tabsListTableName;
    public String searchHistory;

    @Override
    public void onCreate() {
        newsListTableName = "newsListTableName";
        readNewsListTableName = "readNewsListTableName";
        tabsListTableName = "tabsListTableName";
        searchHistory = "searchHistory";
        super.onCreate();

        //List<KnowledgeGraph> list = new ArrayList<KnowledgeGraph>();
        //new KnowledgeGraph.GetKnowledgeGraghListAsyncTask(list, "病毒", new KnowledgeGraph.PostExec() {
        //    @Override
        //    public void onPostExec(Boolean success) {
        //        Log.d("mylog", "oh classes");
        //    }
        //}).execute();
    }
}
