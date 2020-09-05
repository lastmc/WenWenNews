package com.java.yandifei.network;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.java.yandifei.ui.news.NewsItemRecyclerViewAdapter;
import com.alibaba.fastjson.JSON;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NewsEntry implements Serializable {

    public String id;
    public String title;
    public String time;
    public String source;
    public String content;
    public String type;
    public List<Author> authors = new ArrayList<Author>();
    private static String newsEntryUrl = "https://covid-dashboard.aminer.cn/api/events/list";


    public NewsEntry() {
    }

    @Override
    public String toString() {
        return "NewsEntry(id: " + id + ", title: " + title + ", time: " + time+
                ", source: " + source + ", content: " + content + ", type: " + type +
                ", authors: " + authors + ")";
    }

    public static void getNewsList(CharSequence tag, int page, final List<NewsEntry> newsList) {
        OkHttpClient client = new OkHttpClient();
        String url = newsEntryUrl + "?type=" + tag + "&page=" + page +
                "&size=" + NewsItemRecyclerViewAdapter.entryNumPerPage;
        final Request request = new Request.Builder().get().url(url).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.code() == 200) {
                final String result = response.body().string();
                JSONArray dataArray = ((Map<String, JSONArray>)JSON.parse(result)).get("data");
                for (int i = 0; i < dataArray.size(); ++i) {
                    NewsEntry entry = JSON.parseObject(dataArray.getString(i), NewsEntry.class);
                    if (entry.source.equals("")) {
                        if (entry.authors.size() == 0) entry.source = "Unknown";
                        else {
                            for (Author author : entry.authors) entry.source += author.name + ", ";
                            entry.source = entry.source.substring(0, entry.source.length()-3);
                        }
                    }
                    // System.out.println(entry);
                    newsList.add(entry);
                }
            }
        } catch (IOException e) {
            System.out.println("mylog: " + e);
        } finally {
            if (response != null) response.body().close();
        }
    }
}
