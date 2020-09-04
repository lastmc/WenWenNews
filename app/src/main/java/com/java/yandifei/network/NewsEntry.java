package com.java.yandifei.network;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class NewsEntry {
    public String title;
    public String description;

    public NewsEntry(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public static List<NewsEntry> initNewsEntryList(Resources resources) {
        List<NewsEntry> newsList = new ArrayList<NewsEntry>();
        for (int i = 0; i < 100000; ++i) {
            newsList.add(new NewsEntry("news " + i, "description " + i));
        }
        return newsList;
    }

}
