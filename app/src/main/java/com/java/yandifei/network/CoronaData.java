package com.java.yandifei.network;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CoronaData implements Serializable {

    public static class OneDayData {

        public static String field_confirmed = "confirmed";
        public static String field_suspected = "suspected";
        public static String field_cured = "cured";
        public static String field_dead = "dead";

        public int confirmed;
        public int suspected;
        public int cured;
        public int dead;
        public int severe;
        public int risk;
        public int inc24;

        @Override
        @NonNull
        public String toString() {
            return "OneDayData(confirmed: " + confirmed + ", suspected: " + suspected +
                    ", cured: " + cured + ", dead: " + dead + ", severe:" + severe +
                    ", risk: " + risk + "inc24: " + inc24 + ")";
        }

        public int getData(String field) {
            if (field.equals(field_confirmed)) return confirmed;
            else if (field.equals(field_suspected)) return suspected;
            else if (field.equals(field_cured)) return cured;
            else if (field.equals(field_dead)) return dead;
            return 0;
        }

        public static OneDayData fromJSONArray(JSONArray list) {
            OneDayData data = new OneDayData();
            data.confirmed = list.getIntValue(0);
            data.suspected = list.getIntValue(1);
            data.cured = list.getIntValue(2);
            data.dead = list.getIntValue(3);
            data.severe = list.getIntValue(4);
            data.risk = list.getIntValue(5);
            data.inc24 = list.getIntValue(6);
            return data;
        }
    }

    public String district;
    public String begin;
    public List<OneDayData> dataOfDays;
    public static String coronaDataUrl = "https://covid-dashboard.aminer.cn/api/dist/epidemic.json";

    @Override
    @NonNull
    public String toString() {
        return "CoronaData(district: " + district + ", begin: " + begin +
                ", dataOfDays: " + dataOfDays + ")";
    }

    public static CoronaData fromJson(String district, JSONObject attributes) {
        CoronaData coronaData = new CoronaData();
        coronaData.district = district;
        coronaData.begin = attributes.getString("begin");
        coronaData.dataOfDays = new ArrayList<OneDayData>();
        for (Object oneDay : attributes.getJSONArray("data")) {
            coronaData.dataOfDays.add(OneDayData.fromJSONArray(
                    (JSONArray)oneDay
            ));
        }
        return coronaData;
    }

    public List<String> getFields() {
        List<String> fields = new ArrayList<String>();
        fields.add(OneDayData.field_confirmed); fields.add(OneDayData.field_suspected);
        fields.add(OneDayData.field_cured); fields.add(OneDayData.field_dead);
        return fields;
    }

    public int fieldToColor(String field) {
        if (field.equals(OneDayData.field_confirmed)) return Color.rgb(255, 153, 51);
        else if (field.equals(OneDayData.field_suspected)) return Color.rgb(153, 51, 255);
        else if (field.equals(OneDayData.field_cured)) return Color.rgb(0, 153, 0);
        else if (field.equals(OneDayData.field_dead)) return Color.rgb(255, 0, 0);
        return Color.rgb(0, 0, 0);
    }

    public interface PostExec {
        void onPostExec(boolean success);
    }

    public static class GetCoronaDataAsyncTask extends AsyncTask<String, Void, Boolean> {

        public List<CoronaData> coronaDataList;
        public PostExec postExec;

        public GetCoronaDataAsyncTask(List<CoronaData> coronaDataList, PostExec postExec) {
            this.coronaDataList = coronaDataList;
            this.postExec = postExec;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            boolean success = true;
            OkHttpClient client = new OkHttpClient();
            String url = coronaDataUrl;
            final Request request = new Request.Builder().get().url(url).build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
                if (response.code() == 200) {
                    final String result = response.body().string();
                    Map<String, JSONObject> allData = (Map<String, JSONObject>) JSON.parse(result);
                    for (String key : allData.keySet()) {
                        coronaDataList.add(CoronaData.fromJson(key, allData.get(key)));
                    }
                }
            } catch (UnknownHostException e) {
                // no network access
                success = false;
            } catch (IOException e) {
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
