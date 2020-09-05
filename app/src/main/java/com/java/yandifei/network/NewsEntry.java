package com.java.yandifei.network;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class NewsEntry {
    public String id;
    public String title;
    public String description;

    public NewsEntry(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public static List<NewsEntry> initNewsEntryList(Resources resources) {
        List<NewsEntry> newsList = new ArrayList<NewsEntry>();
        for (int i = 0; i < 100000; ++i) {
            newsList.add(new NewsEntry("News " + i,"news " + i, "description " + i));
        }
        return newsList;
    }

    public static List<NewsEntry> genNewsListByTag(CharSequence tag){
        return initNewsEntryList(null);
    }

}
