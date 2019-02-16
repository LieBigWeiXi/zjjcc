package com.example.culturecloud.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.culturecloud.Bean.VideoInfo;
import com.example.culturecloud.MyTools.PicTool;
import com.example.culturecloud.R;
import com.example.culturecloud.StaticResources.NetworkInfo;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;


public class VideoInfoActivity extends BaseActivity {

    TextView tv_actor,tv_type,tv_date,tv_director,tv_type2;
    WebView video_info;
    ImageView imageView,bt_play;
    public static VideoInfo videoInfo = new VideoInfo();
    public static String video_type = " ";

    @Override
    protected void onResume() {
        super.onResume();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.video_info);


        imageView =(ImageView)findViewById(R.id.videoplayer);

        Glide.with(VideoInfoActivity.this)
                .load(NetworkInfo.static_address+videoInfo.vi_cover)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        imageView.setImageBitmap(bitmap);
                    }
                }); //加载新照片
        findViewById(R.id.vod_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url ="";
                if (videoInfo.vi_file.startsWith("http")){
                    url = videoInfo.vi_file;
                }else{
                    url = NetworkInfo.ip_address+videoInfo.vi_file;
                }
                Intent intent = new Intent(VideoInfoActivity.this,VideoPlayActivity.class);
                intent.putExtra("video",videoInfo);
                startActivity(intent);
            }
        });

        tv_actor = (TextView)findViewById(R.id.tv_video_actor);
        tv_type = (TextView)findViewById(R.id.tv_video_type);
        tv_date = (TextView)findViewById(R.id.tv_video_date);
        tv_director =(TextView)findViewById(R.id.tv_video_director);
        tv_type2 = (TextView)findViewById(R.id.tv_video_type2);

        tv_actor.setText(videoInfo.vi_actor);//视频演员
        tv_actor.setSelected(true);//文字充满，滚动显示
        tv_type.setText(videoInfo.vi_name);//视频名称

        tv_date.setText(videoInfo.vi_date);//视频日期
        Log.d("video_date", "onCreate: "+videoInfo.vi_date);
        tv_director.setText(videoInfo.vi_director); //视频演出单位
        tv_type2.setText(video_type);//视频类型
        video_info = (WebView)findViewById(R.id.wv_video_info);
        String html_txt;
        html_txt = "<div style=\"color:#000000;margin:20px;font-size:10px\">"+videoInfo.vi_info+"</div>";
        Log.d("videoInfo", "onCreate: "+videoInfo.vi_info);
        video_info.loadData(html_txt,"text/html; charset=UTF-8",null);
    }


}