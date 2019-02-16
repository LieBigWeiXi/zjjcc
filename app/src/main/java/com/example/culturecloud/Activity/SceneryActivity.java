package com.example.culturecloud.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.culturecloud.Adapter.PictureAdapter;
import com.example.culturecloud.Adapter.ScenAdapter;
import com.example.culturecloud.Bean.PicturesBean;
import com.example.culturecloud.Bean.TypeBean;
import com.example.culturecloud.HttpRequest.doRequest;
import com.example.culturecloud.MyTools.PicTool;
import com.example.culturecloud.R;
import com.example.culturecloud.StaticResources.NetworkInfo;
import com.example.culturecloud.Views.ShowImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//名胜古迹
public class SceneryActivity extends BaseActivity {
    private List<TypeBean> typeBeanList = new ArrayList<>();
    private List<PicturesBean> picturesBeanList = new ArrayList<>();
    private int type_id,picture_load_count;
    GridView oldpic_gridview;
    PictureAdapter pictureAdapter;
    ListView scenery_listView;
    ScenAdapter scenAdapter;
    doRequest http_request;
    int page = 1,limit = 6;//页码，页面数据大小
    private int picture_count = 0;
    public static Bitmap old_pic,new_pic;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Gson gson = new Gson();
            switch (msg.what){
                case 400://处理返回的名胜古迹的类型数据
                    String responseData = (String)msg.obj;
                    try {
                        JSONObject jsonObject = new JSONObject(responseData);
                        int code = jsonObject.getInt("code");
                        if(code==0){//获取数据成功
                            String scenery_type = jsonObject.getJSONArray("data").toString();
                            picture_count =jsonObject.getInt("count");
                            List<TypeBean>  sceneryTypeBeanList = gson.fromJson(scenery_type,new TypeToken<List<TypeBean>>(){}.getType());
                            typeBeanList.addAll(sceneryTypeBeanList);
                            Log.d("SceneryData", "handleMessage: "+String.valueOf(typeBeanList.size()));
                            init();
                        }else if(code==1){//session过期，重启程序
                            Toast.makeText(SceneryActivity.this,"登录信息过期，正在重新登录",Toast.LENGTH_SHORT).show();
                            Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }
                        else {//获取本地数据

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 500://加载右侧图片
                    String picturesData = (String)msg.obj;
                Log.d("tag",picturesData);
                    try {
                        JSONObject jsonObject = new JSONObject(picturesData);
                        int code = jsonObject.getInt("code");
                        if(code==0){//获取数据成功
                            String scenery_type = jsonObject.getJSONArray("data").toString();
                            List<PicturesBean>  picturesBeans = gson.fromJson(scenery_type,new TypeToken<List<PicturesBean>>(){}.getType());
                            picturesBeanList.addAll(picturesBeans);
                            pictureAdapter.addArrayList(picturesBeans);
                            pictureAdapter.notifyDataSetChanged();
                        }else if(code==1){//session过期，重启程序
                            Toast.makeText(SceneryActivity.this,"登录信息过期，正在重新登录",Toast.LENGTH_SHORT).show();
                            Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }
                        else{
                            //新添加从本地读取数据代码
                            try{
                                FileInputStream fileInputStream = getApplicationContext().openFileInput("E:\\CultureCloud\\scenery.csv");
                                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
                                char[] input = new char[fileInputStream.available()];
                                inputStreamReader.read(input);
                                inputStreamReader.close();
                                fileInputStream.close();
                                String info = new String(input);
                                Toast.makeText(SceneryActivity.this,info,Toast.LENGTH_LONG).show();
                            }catch(Exception e){
                                Toast.makeText(SceneryActivity.this,"读取失败",Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }
    };
    public void init(){
        scenAdapter = new ScenAdapter(this,R.layout.scenery_item,typeBeanList);
        scenery_listView = (ListView)findViewById(R.id.scenery_listview);
        scenery_listView.setAdapter(scenAdapter);

        oldpic_gridview = (GridView)findViewById(R.id.oldpic_gridview);
        pictureAdapter = new PictureAdapter(this);
        oldpic_gridview.setAdapter(pictureAdapter);

        scenery_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TypeBean typeBean = typeBeanList.get(i);
                scenAdapter.setSelected_id(typeBean.getId());
                scenAdapter.notifyDataSetInvalidated();
                page = 1;
                picturesBeanList = new ArrayList<>();
                pictureAdapter.clear();
                type_id = typeBean.getId();
                loaddata(type_id);

            }
        });
        String url = NetworkInfo.ip_address + NetworkInfo.scenery_url+"?page="
                +String.valueOf(page)+"&limit="+String.valueOf(limit);
        http_request.doGet(url,500,handler);
    }


    private void loaddata(int type_id){
        String url;
        if (type_id!= 0) {
            url = NetworkInfo.ip_address + NetworkInfo.scenery_url+"?page="
                    +String.valueOf(page)+"&limit="+String.valueOf(limit)+"&type_id=" +String.valueOf(type_id);
        }else{
            url = NetworkInfo.ip_address + NetworkInfo.scenery_url+"?page="
                    +String.valueOf(page)+"&limit="+String.valueOf(limit);
        }
        http_request.doGet(url,500,handler);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenery);
        http_request = doRequest.getInstance(getApplicationContext());
        TypeBean all_scemery = new TypeBean();
        all_scemery.setDt_name("全部");
        all_scemery.setId(0);
        typeBeanList.add(all_scemery);
        http_request.doGet(NetworkInfo.ip_address+"/website/get_type/?func_id=3",400,handler);
        RefreshLayout refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new MaterialHeader(this));

        refreshLayout.setRefreshFooter(new ClassicsFooter(this).setSpinnerStyle(SpinnerStyle.Scale));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                picturesBeanList = new ArrayList<>();
                pictureAdapter.clear();
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

        oldpic_gridview = (GridView)findViewById(R.id.oldpic_gridview);
        oldpic_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PicturesBean picture = picturesBeanList.get(i);
                Intent intent =new Intent(SceneryActivity.this,SceneryLoadActivity.class);
                intent.putExtra("picture",picture);
                startActivity(intent);

            }
        });

    }
}
