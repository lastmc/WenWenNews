package com.java.yandifei.network;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.IOException;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Scholar implements Serializable {

    public static class Profile implements Serializable {
        public String bio;
        public String edu;
        public String position;
        public String work;
    }

    public String id;
    public String name;
    public String name_zh;
    public String avatar;   // url of avatar
    public Profile profile;
    public boolean is_passedaway;
    public static String scholarUrl = "https://innovaapi.aminer.cn/predictor/api/v1/valhalla/highlight/get_ncov_expers_list?v=2";


    public static void getScholarList(List<Scholar> scholarList) {
        OkHttpClient client = new OkHttpClient();
        String url = scholarUrl;
        final Request request = new Request.Builder().get().url(url).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.code() == 200) {
                final String result = response.body().string();
                JSONArray dataArray = ((Map<String, JSONArray>) JSON.parse(result)).get("data");
                for (int i = 0; i < dataArray.size(); ++i) {
                    Scholar scholar = JSON.parseObject(dataArray.getString(i), Scholar.class);
                    scholarList.add(scholar);
                }
            }
        } catch (UnknownHostException e) {
            // no network access
            Log.d("Scholar: ", e.toString());
        } catch (IOException e) {
            Log.d("Scholar: ", e.toString());
        } finally {
            if (response != null) response.body().close();
        }
    }

}
