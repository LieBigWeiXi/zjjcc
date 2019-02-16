package com.example.culturecloud.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.culturecloud.Adapter.CulAdapter;
import com.example.culturecloud.Adapter.PictureAdapter;
import com.example.culturecloud.Adapter.VideoAdapter;
import com.example.culturecloud.Adapter.VideoContentAdapter;
import com.example.culturecloud.Bean.TypeBean;
import com.example.culturecloud.Bean.VideoInfo;
import com.example.culturecloud.Bean.VideoTypeBean;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends BaseActivity{
    public static List<TypeBean> typeBeanList = new ArrayList<>();
    VideoAdapter videoAdapter;
    VideoContentAdapter videoContentAdapter;
    GridView videoGridView;
    List<VideoInfo> videoInfoList = new ArrayList<>();
    doRequest http_request;
    RecyclerView video_type_view;

    private int page = 1,limit = 8;
    private int type_id;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Gson gson = new Gson();
            switch (msg.what){
                case 200:
                    String responseData = (String)msg.obj;
                    try {
                        JSONObject jsonObject = new JSONObject(responseData);
                        int code = jsonObject.getInt("code");//
                        if(code==0){//获取数据成功
                            String video_type = jsonObject.getJSONArray("data").toString();
                            //解析视频类型
                            List<TypeBean>  TypeBeanList = gson.fromJson(video_type,new TypeToken<List<TypeBean>>(){}.getType());
                            typeBeanList.clear();
                            TypeBean typeBean = new TypeBean();
                            typeBean.setDt_name("全部");
                            typeBean.setId(0);
                            typeBeanList.add(typeBean);
                            typeBeanList.addAll(TypeBeanList);
                            //加载视频
                            init();
                        }else if(code==1){
                            //session过期，重启程序
                            Toast.makeText(VideoActivity.this,"登录信息过期，正在重新登录",Toast.LENGTH_SHORT).show();
                            Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(VideoActivity.this,"解析视频类型错误！",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 500://解析视频数据
                    String videoData = (String)msg.obj;
                    try {
                        JSONObject jsonObject = new JSONObject(videoData);
                        int code = jsonObject.getInt("code");
                        if(code==0){//获取数据成功
                            String video_info = jsonObject.getJSONArray("data").toString();

                            List<VideoInfo>  video = gson.fromJson(video_info,new TypeToken<List<VideoInfo>>(){}.getType());
                            videoInfoList.addAll(video);
                            videoContentAdapter.addArrayList(video);
                            videoContentAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(VideoActivity.this,"解析视频数据错误！",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {

                    }
                    break;

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        http_request = doRequest.getInstance(getApplicationContext());
        String url = NetworkInfo.ip_address+"/website/get_type/?func_id=7";
        http_request.doGet(url,200,handler);
        videoGridView = (GridView)findViewById(R.id.video_grid_view);
        videoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                VideoInfo videoInfo = videoInfoList.get(i);
                VideoInfoActivity.videoInfo = videoInfo;
                String typeName = "";
                for (TypeBean typeBean:typeBeanList){
                    if(typeBean.getId()==videoInfo.vi_type){
                        typeName = typeBean.getDt_name();
                        break;
                    }
                }
                VideoInfoActivity.video_type = typeName;
                Intent intent = new Intent(VideoActivity.this,VideoInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    public void init(){
        videoAdapter = new VideoAdapter(typeBeanList, handler, new VideoAdapter.OnClickCallBack() {
            @Override
            public void Onclick(int position) {
                TypeBean typeBean = typeBeanList.get(position);
                videoAdapter.setPositon(position);
                videoAdapter.notifyDataSetChanged();
                page = 1;
                videoInfoList = new ArrayList<>();
                videoContentAdapter.clear();
                type_id = typeBean.getId();
                loaddata(type_id);
            }
        });
        video_type_view = (RecyclerView) findViewById(R.id.rv_video_type);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        video_type_view.setLayoutManager(layoutManager);
        video_type_view.setAdapter(videoAdapter);
        videoGridView = (GridView)findViewById(R.id.video_grid_view);
        videoContentAdapter = new VideoContentAdapter(this);
        videoGridView.setAdapter(videoContentAdapter);

        String url = NetworkInfo.ip_address+"/website/get_video_android/"+"?page="
                +String.valueOf(page)+"&limit="+String.valueOf(limit);
        http_request.doGet(url,500,handler);

        RefreshLayout refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new MaterialHeader(this));

        refreshLayout.setRefreshFooter(new ClassicsFooter(this).setSpinnerStyle(SpinnerStyle.Scale));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                videoInfoList = new ArrayList<>();
                videoContentAdapter.clear();
                loaddata(type_id);
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page+=1;
                loaddata(type_id);
                refreshlayout.finishLoadmore(2000);
            }
        });
    }

    private void loaddata(int type_id){
        String url;
        if (type_id!= 0) {
            url = NetworkInfo.ip_address + NetworkInfo.video_url+"?page="
                    +String.valueOf(page)+"&limit="+String.valueOf(limit)+"&type_id=" +String.valueOf(type_id);
        }else{
            url = NetworkInfo.ip_address + NetworkInfo.video_url+"?page="
                    +String.valueOf(page)+"&limit="+String.valueOf(limit);
        }
        http_request.doGet(url,500,handler);
    }
}
