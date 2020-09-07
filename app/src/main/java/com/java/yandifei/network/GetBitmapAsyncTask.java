package com.java.yandifei.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetBitmapAsyncTask extends AsyncTask<String, Void, Bitmap> {

    public interface PostExecBitmap {
        void handleBitmap(Bitmap avatar);
    }

    private String path;
    private PostExecBitmap onPost;

    public GetBitmapAsyncTask(String path, PostExecBitmap onPost) {
        this.path = path;
        this.onPost = onPost;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bitmap = null;
        boolean success = true;
        try {
            //把传过来的路径转成URL
            URL url = new URL(path);
            //获取连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //使用GET方法访问网络
            connection.setRequestMethod("GET");
            //超时时间为10秒
            connection.setConnectTimeout(10000);
            //获取返回码
            int code = connection.getResponseCode();
            if (code == 200) {
                InputStream inputStream = connection.getInputStream();
                //使用工厂把网络的输入流生产Bitmap
                bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
            }
        } catch (IOException e) {
            // network error
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmaps) {
        this.onPost.handleBitmap(bitmaps);
    }
}
