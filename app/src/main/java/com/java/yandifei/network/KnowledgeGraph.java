package com.java.yandifei.network;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.SectionIndexer;

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

public class KnowledgeGraph implements Serializable {

    public static class Relation implements Serializable{

        public String relation;
        public String url;
        public String label;
        public boolean forward;

        @Override
        @NonNull
        public String toString() {
            return "Relation(relation: " + relation + ", url: " + url +
                    ", label: " + label + ", forward: " + forward + ")";
        }
    }

    public static class Covid implements Serializable{


        public Map<String, String> properties;
        public List<Relation> relations;

        @Override
        @NonNull
        public String toString() {
            return "Covid(properties: " + properties + ", relations: " + relations + ")";
        }
    }
    public static class AbstractInfo implements Serializable {


        public String enwiki;
        public String baidu;
        public String zhwiki;
        public Covid covid;

        public String getDescription() {
            if (!enwiki.equals("")) return enwiki;
            if (!baidu.equals("")) return baidu;
            if (!zhwiki.equals("")) return zhwiki;
            return "";
        }

        @Override
        @NonNull
        public String toString() {
            return "AbstractInfo(description: " + getDescription() +
                    ", covid: " + covid + ")";
        }
    }

    public float hot;
    public String label;
    public String img;
    public AbstractInfo abstractInfo;
    public static String knowledgeGraphUrl = "https://innovaapi.aminer.cn/covid/api/v1/pneumonia/entityquery?entity=";

    @Override
    @NonNull
    public String toString() {
        return "KnowledgeGraph(hot: " + hot + ", label: " + label +
                ", img: " + img + ", abstract info: " + abstractInfo + ")";
    }

    public String getBrief() {
        return "Hot Value : " + hot;
    }

    public interface PostExec {
        void onPostExec(Boolean success);
    }

    public static class GetKnowledgeGraghListAsyncTask extends AsyncTask<String, Void, Boolean> {

        private List<KnowledgeGraph> knowledgeGraphList;
        private PostExec postExec;
        private String key;

        public GetKnowledgeGraghListAsyncTask(List<KnowledgeGraph> knowledgeGraphList,
                                              String key, PostExec postExec) {
            this.knowledgeGraphList = knowledgeGraphList;
            this.key = key;
            this.postExec = postExec;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            boolean success = true;
            OkHttpClient client = new OkHttpClient();
            String url = knowledgeGraphUrl + key;
            final Request request = new Request.Builder().get().url(url).build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
                if (response.code() == 200) {
                    final String result = response.body().string();
                    JSONArray dataArray = ((Map<String, JSONArray>) JSON.parse(result)).get("data");
                    for (int i = 0; i < dataArray.size(); ++i) {
                        KnowledgeGraph knowledgeGraph =
                                JSON.parseObject(dataArray.getString(i), KnowledgeGraph.class);
                        knowledgeGraphList.add(knowledgeGraph);
                        Log.d("mylog", knowledgeGraph.toString());
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
