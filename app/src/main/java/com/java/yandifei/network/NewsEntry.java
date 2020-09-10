package com.java.yandifei.network;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.SharedPreferences;
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
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NewsEntry implements Serializable {

    public String _id;
    public String title;
    public String time;
    public String source;
    public String content;
    public String type;
    public String theme;    // for news cluster, number
    public String tag;      // for news cluster, tag
    public List<Author> authors = new ArrayList<Author>();
    private static FragmentActivity activity;
    private static MyApplication globalVariables;
    private static String newsEntryUrl = "https://covid-dashboard.aminer.cn/api/events/list";


    public NewsEntry() {
    }

    @Override
    public String toString() {
        return "NewsEntry(_id: " + _id + ", title: " + title + ", time: " + time+
                ", source: " + source + ", content: " + content + ", type: " + type +
                ", authors: " + authors + ", theme: " + theme + ")";
    }

    public static void setActivity(FragmentActivity activity) {
        NewsEntry.activity = activity;
        NewsEntry.globalVariables = (MyApplication)activity.getApplication();
    }

    public static void saveNewsEntry(NewsEntry news) {
        SharedPreferences storage = activity.getSharedPreferences(
                globalVariables.newsListTableName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = storage.edit();
        editor.putString(news._id, JSON.toJSONString(news));
        editor.commit();
    }

    public static void saveReadNewsState(NewsEntry news) {
        SharedPreferences storage = activity.getSharedPreferences(
                globalVariables.readNewsListTableName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = storage.edit();
        editor.putBoolean(news._id, true);
        editor.commit();

    }

    public static boolean newsIsSaved(String newsId) {
        SharedPreferences storage = activity.getSharedPreferences(
                globalVariables.newsListTableName, Context.MODE_PRIVATE);
        return storage.contains(newsId);
    }

    public static boolean newsIsRead(String newsId) {
        SharedPreferences storage = activity.getSharedPreferences(
                globalVariables.readNewsListTableName, Context.MODE_PRIVATE);
        return storage.contains(newsId);
    }

    public static List<NewsEntry> getAllSavedNews(List<NewsEntry> newsEntryList) {
        MyApplication globalVariables = (MyApplication)activity.getApplication();
        SharedPreferences storage = activity.getSharedPreferences(
                globalVariables.newsListTableName, Context.MODE_PRIVATE);
        for (String data : (Collection<String>)storage.getAll().values()) {
            newsEntryList.add(JSON.parseObject(data, NewsEntry.class));
        }
        return newsEntryList;
    }

    public static void getNewsList(CharSequence tag, int page, int entryNumPerPage, final List<NewsEntry> newsList) {
        OkHttpClient client = new OkHttpClient();
        String url = newsEntryUrl + "?type=" + tag + "&page=" + page +
                "&size=" + entryNumPerPage;
        final Request request = new Request.Builder().get().url(url).build();
        Response response = null;
        try {
            MyApplication globalVariables = (MyApplication) activity.getApplication();
            response = client.newCall(request).execute();
            if (response.code() == 200) {
                final String result = response.body().string();
                JSONArray dataArray = ((Map<String, JSONArray>) JSON.parse(result)).get("data");
                for (int i = 0; i < dataArray.size(); ++i) {
                    NewsEntry entry = JSON.parseObject(dataArray.getString(i), NewsEntry.class);
                    if (entry.source.equals("")) {
                        if (entry.authors.size() == 0) entry.source = "Unknown";
                        else {
                            for (Author author : entry.authors) entry.source += author.name + ", ";
                            entry.source = entry.source.substring(0, entry.source.length() - 3);
                        }
                    }
                    newsList.add(entry);
                    if (!newsIsSaved(entry._id))
                        saveNewsEntry(entry);
                }
            }
        } catch (UnknownHostException e) {
            // no network access
            if (newsList.size() == 0) {
                getAllSavedNews(newsList);
                newsList.sort(new Comparator<NewsEntry>() {
                    @Override
                    public int compare(NewsEntry o1, NewsEntry o2) {
                        String[] times1 = o1.time.split(" ");
                        String[] times2 = o2.time.split(" ");
                        if (times1.length == 1 || times2.length == 1) {
                            return times2[0].replace("/", "-").compareTo(
                                    times1[0].replace("/", "-"));
                        }
                        return o2.time.compareTo(o1.time);
                    }
                });
            }
        } catch (IOException e) {
            System.out.println("mylog: " + e);
        } finally {
            if (response != null) response.body().close();
        }
    }
}
