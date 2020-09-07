package com.java.yandifei.network;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.IOException;
import java.io.Serializable;
import java.net.UnknownHostException;
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

        @Override
        @NonNull
        public String toString() {
            return "Profile(bio: " + bio + ", edu: " + edu +
                    ", position: " + position + ", work: " + work;
        }
    }

    public String id;
    public String name;
    public String name_zh;
    public String avatar;   // url of avatar
    public Profile profile;
    public boolean is_passedaway;
    public static String scholarUrl = "https://innovaapi.aminer.cn/predictor/api/v1/valhalla/highlight/get_ncov_expers_list?v=2";

    @Override
    @NonNull
    public String toString() {
        return "Scholar(id: " + id + ", name: " + name + ", name_zh: " + name_zh +
                ", avatar: " + avatar + ", profile: " + profile +
                ", is_passedaway: " + is_passedaway;
    }

    public interface PostExec {
        void onPostExec(boolean success);
    }

    public String nameString(){
        String nameStr = name_zh;
        if(nameStr == null || nameStr.length() == 0) nameStr = name;
        else nameStr += " " + name;
        return nameStr;
    }

    // to use this async task:
    // List<Scholar> scholarList = new ArrayList<Scholar>();
    // new Scholar.GetScholarListAsyncTask(scholarList, new Scholar.PostExec()).execute()
    public static class GetScholarListAsyncTask extends AsyncTask<String, Void, Boolean> {

        public List<Scholar> scholarList;
        public PostExec postExec;

        public GetScholarListAsyncTask(List<Scholar> scholarList, PostExec postExec) {
            this.scholarList = scholarList;
            this.postExec = postExec;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            boolean success = true;
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
                success = false;
            } catch (IOException e) {
                Log.d("Scholar: ", e.toString());
                success = false;
            } finally {
                if (response != null) response.body().close();
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            this.postExec.onPostExec(success);
        }
    }
}
