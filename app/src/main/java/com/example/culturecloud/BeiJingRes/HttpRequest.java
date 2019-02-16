package com.example.culturecloud.BeiJingRes;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.culturecloud.StaticResources.NetworkInfo;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by DELL on 2018/8/23.
 */

public class HttpRequest {
    private OkHttpClient mOkHttpClient;
    private String url = "http://api.183read.cc/open_api/rest2.php?";
    public void doGet(final String param, final NetWorkCallBack callBack){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder().url(url+param).build();
                Log.d("paper", "run: "+url+param);
                try {
                    mOkHttpClient = new OkHttpClient();
                    Response response = mOkHttpClient.newCall(request).execute();
                    String responseData = response.body().string();
                    callBack.callback(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public interface NetWorkCallBack{
        public void callback(String recData);
    }

    public static String GsonString(Object object) {
        Gson gson = new Gson();
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

}
