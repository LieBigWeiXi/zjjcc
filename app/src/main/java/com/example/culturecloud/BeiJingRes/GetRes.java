package com.example.culturecloud.BeiJingRes;

import android.util.Log;

/**
 * 得到分类下资源列表
 */


public class GetRes {

    public void get_res(String act, BaseParem bp, final CallBack callBack){

        String jsonParam = HttpRequest.GsonString(bp);
        String request_param = "act="+act+"&param="+jsonParam;
        HttpRequest httpRequest = new HttpRequest();

        httpRequest.doGet(request_param, new HttpRequest.NetWorkCallBack() {
            @Override
            public void callback(String recData) {
                callBack.callback(recData);
            }
        });
    }

    public interface CallBack{
        public void callback(String recData);
    }
}
