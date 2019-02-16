package com.example.culturecloud.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.culturecloud.Adapter.CulAdapter;
import com.example.culturecloud.Adapter.NewsAdapter;
import com.example.culturecloud.Bean.CulInfo;
import com.example.culturecloud.Bean.DoPostBean;
import com.example.culturecloud.Bean.NewsBean;
import com.example.culturecloud.HttpRequest.doRequest;
import com.example.culturecloud.R;
import com.example.culturecloud.StaticResources.NetworkInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends BaseActivity{

    ListView newsListView;
    WebView  newsWeb;

    NewsAdapter adapter;
    NewsBean mNewsBean;
    List<NewsBean> newsDataList = new ArrayList<>();
    List<NewsBean> newsBeans ;
    private int news_count = 0,page = 1,limit =4;
    DoPostBean doPostBean;
    doRequest  http_request;

    final String URL = NetworkInfo.ip_address+NetworkInfo.news_url;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Gson gson = new Gson();
            switch (msg.what){
                case 200:
                    String responseData = (String)msg.obj;
                    Log.d("Main", "handleMessage: "+responseData);
                    /*mNewsBean = gson.fromJson(response,NewsBean.class);
                    newsDataList.addAll(mNewsBean.getData());
                    Log.d("", "handleMessage: "+newsDataList.get(0).getCover());
                    if(newsDataList.size()!=0){
                        adapter = new NewsAdapter(NewsActivity.this,R.layout.news_list_item,newsDataList);
                        newsListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        newsWeb.loadUrl(newsDataList.get(0).getUrl());
                    }else{
                        Toast.makeText(NewsActivity.this,"没有上传数据",
                                Toast.LENGTH_SHORT).show();
                    }*/
                    try {
                        JSONObject json_news = new JSONObject(responseData);
                        int code = json_news.getInt("code");
                        if(code==0){
                            String data = json_news.getJSONArray("data").toString();
                            Log.d("unload",data);
                            newsBeans = gson.fromJson(data,new TypeToken<List<NewsBean>>(){}.getType());
                            newsDataList.addAll(newsBeans);
                            news_count = json_news.getInt("count");
                            adapter = new NewsAdapter(NewsActivity.this,R.layout.news_list_item,newsDataList);
                            adapter.addNewsBean(newsDataList);
                            newsListView.setAdapter(adapter);
                            newsListView.setSelection(newsListView.getCount()-1);
                            adapter.notifyDataSetChanged();
                            if(page==1&&newsBeans.size()>0){
                                newsWeb.loadUrl(newsDataList.get(0).getNi_url());
                            }
                        }else if(code==1){
                            Toast.makeText(NewsActivity.this,"登录信息过期，正在重新登录",Toast.LENGTH_SHORT).show();
                            Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        http_request = doRequest.getInstance(getApplicationContext());
        doPostBean  = new DoPostBean();
        newsListView = (ListView)findViewById(R.id.news_list);
        newsWeb = (WebView)findViewById(R.id.news_web);
        newsWeb.getSettings().setJavaScriptEnabled(true);
        newsWeb.setWebViewClient(new WebViewClient());
//        http_request.doPost(URL,doPostBean,handler,200);

        String url = URL+"?page="+String.valueOf(page)+"&limit="+String.valueOf(limit);

        http_request.doGet(url,200,handler);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                newsWeb.loadUrl(newsDataList.get(i).getNi_url());
                adapter.setSelectedId(i);
                adapter.notifyDataSetChanged();
            }
        });

        RefreshLayout refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new MaterialHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this).setSpinnerStyle(SpinnerStyle.Scale));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                newsDataList = new ArrayList<>();
                /*doPostBean.setPage(1);
                http_request.doPost(URL,doPostBean,handler,200);*/
                page = 1;
                String url = URL+"?page="+String.valueOf(page)+"&limit="+String.valueOf(limit);
                http_request.doGet(url,200,handler);
                adapter.notifyDataSetChanged();
                refreshlayout.finishRefresh(3000);
            }
        });

        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if(newsDataList.size()<news_count){
                    page+=1;
                   /* doPostBean.setPage(doPostBean.getPage() + 1 );
                    http_request.doPost(URL,doPostBean,handler,200);*/
                    Log.d("News", "onLoadmore: "+newsListView.getCount());
                    newsListView.setSelection(newsListView.getCount()-1);
                    newsListView.setFocusable(false);
                    String url = URL+"?page="+String.valueOf(page)+"&limit="+String.valueOf(limit);
                    http_request.doGet(url,200,handler);
                }else {
                    Toast.makeText(NewsActivity.this,"没有更多了",Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
                refreshlayout.finishLoadmore(3000);
            }
        });
    }
}
