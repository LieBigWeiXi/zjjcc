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
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.culturecloud.Adapter.CulAdapter;
import com.example.culturecloud.Bean.CulInfo;
import com.example.culturecloud.HttpRequest.doRequest;
import com.example.culturecloud.R;
import com.example.culturecloud.StaticResources.NetworkInfo;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//文化名人
public class CulPersonActivity extends BaseActivity{
    doRequest request ;
    public List<CulInfo.culData> culDataList =  new ArrayList<>();
    private int culinfo_count = 0,page =1,limit = 10;
    ImageView cul_imgView;
    //TextView cul_introText;
    WebView cul_introWeb;

    RecyclerView cul_list;
    CulAdapter adapter;

    private int mScreenWidth;
    private static final float MIN_SCALE = .6f;
    private static final float MAX_SCALE = 0.9f;
    private int mMinWidth;
    private int mMaxWidth;
    int height;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CulInfo.culData cul_person;
            String html_text;
            Gson gson = new Gson();
            CulInfo culInfo;
            switch (msg.what){
                case 200:
                    String cul_date = (String)msg.obj;

                    //Log.d("cul", "handleMessage: "+cul_date);
                    try {
                        JSONObject json_cul = new JSONObject(cul_date);
                        int code = json_cul.getInt("code");
                        if(code==0){
                            culInfo = gson.fromJson(cul_date,CulInfo.class);
                            culDataList.addAll(culInfo.getData());
                            culinfo_count = culInfo.getCount();
                            adapter.addCulDataList(culInfo.getData());
                            adapter.notifyDataSetChanged();
                            if(culInfo.getData().size()>=0){
                                cul_person = culDataList.get(0);
                                Picasso.with(CulPersonActivity.this)
                                        .load(NetworkInfo.ip_address+cul_person.getCi_photo())
                                        .into(cul_imgView);
                                html_text ="<div style=\"color:#FFFFFF;margin:10px;font-size:20px;text-align:justify;\">"+cul_person.ci_info+"</div>";
                                cul_introWeb.loadData(html_text,"text/html; charset=UTF-8", null);
                                //Log.d("handler", "handleMessage: "+cul_person.getCi_info());
                            }else{
                                Toast.makeText(CulPersonActivity.this,"无数据",Toast.LENGTH_SHORT).show();
                            }
                        }else if(code==1){
                            Toast.makeText(CulPersonActivity.this,"登录信息过期，正在重新登录",Toast.LENGTH_SHORT).show();
                            Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                break;
                case 201:
                    String add_date = (String)msg.obj;
                    culInfo = gson.fromJson(add_date,CulInfo.class);
                    culinfo_count =culInfo.getCount();
                    culDataList.addAll(culInfo.getData());
                    adapter.addCulDataList(culInfo.getData());
                    adapter.notifyDataSetChanged();
                    break;
                case 300:
                    int postion = msg.arg1;
                    cul_person = culDataList.get(postion);
                    Picasso.with(CulPersonActivity.this)
                            .load(NetworkInfo.ip_address+cul_person.getCi_photo())
                            .into(cul_imgView);
                    html_text ="<div style=\"color:#FFFFFF;margin:10px;font-size:20px;" +
                            "text-align:justify\">"+cul_person.ci_info+"</div>";
                    cul_introWeb.loadData(html_text,"text/html; charset=UTF-8", null);
                    cul_introWeb.loadData(html_text,"text/html; charset=UTF-8", null);
                    Log.d("handler", "handleMessage: "+cul_person.getCi_info());
                    break;
                default:
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cul_person);
        cul_imgView = (ImageView)findViewById(R.id.culture_img);
        cul_list = (RecyclerView)findViewById(R.id.cul_list);
        cul_introWeb = (WebView)findViewById(R.id.culture_introduce);
        cul_introWeb.getSettings().setDefaultTextEncodingName("UTF-8");
        cul_introWeb.setBackgroundColor(Color.argb(0,100,100,100));
        request = doRequest.getInstance(getApplicationContext());
        //请求文化名人数据
        request.doGet(NetworkInfo.ip_address+NetworkInfo.culture_person_url,200,handler);

        cul_list = (RecyclerView)findViewById(R.id.cul_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        cul_list.setLayoutManager(layoutManager);
        adapter = new CulAdapter(this,handler);
        cul_list.setAdapter(adapter);

        mScreenWidth = getResources().getDisplayMetrics().widthPixels - dp2px(500);
        mMinWidth = (int) (mScreenWidth * 1f/7);
        mMaxWidth = mScreenWidth - 6 * mMinWidth;
        cul_list.addOnScrollListener(mOnScrollListener);
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            Log.d("tag",String.valueOf(dy));
            super.onScrolled(recyclerView,dx,dy);
            onScrollChanged(recyclerView);
        }
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            //Log.e("infoo","newState:"+newState+"---"+recyclerView.computeHorizontalScrollExtent()+"----"+recyclerView.computeHorizontalScrollOffset()+"--"+recyclerView.computeHorizontalScrollRange());
            int se=recyclerView.computeHorizontalScrollExtent();
            int so=recyclerView.computeHorizontalScrollOffset();
            int sr=recyclerView.computeHorizontalScrollRange();
            Log.d("tag",String.valueOf(se + so >= sr));
            if (newState == 0 && se + so >= sr) {
                if(culDataList.size()<=culinfo_count){
                    Log.d("tag","ok");
                    loadData();
                }
            }
        }


        private void onScrollChanged(RecyclerView recyclerView){
            final int childCount = recyclerView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                RelativeLayout child = (RelativeLayout) recyclerView.getChildAt(i);//获取选项
                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();   //获取选项参数
                lp.rightMargin = 0;      //右边距
                lp.height = 220;        //高度
                int left = child.getLeft();   //距离左边的边距
                int right = mScreenWidth - child.getRight();   //距离右边的边距
                final float percent = left < 0 || right < 0 ? 0 : Math.min(left, right) * 1f / Math.max(left, right);
                float scaleFactor = MIN_SCALE + Math.abs(percent) * (MAX_SCALE - MIN_SCALE);
                int width = (int) (mMinWidth + Math.abs(percent) * (mMaxWidth - mMinWidth));
                lp.width = width;
                child.setLayoutParams(lp);
                child.setScaleY(scaleFactor);
                child.setScaleX(scaleFactor);
            }

        }
    };
    private void loadData(){
        page += 1;
        String url = NetworkInfo.ip_address+NetworkInfo.culture_person_url+"?page="
                +String.valueOf(page)+"&limit="+String.valueOf(limit);
        Log.d("tag",url);
        request.doGet(url,201,handler);
    }
    public int dp2px(float dipValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
