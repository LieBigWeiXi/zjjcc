package com.example.culturecloud.Activity;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.culturecloud.Bean.VideoInfo;
import com.example.culturecloud.R;
import com.example.culturecloud.StaticResources.NetworkInfo;

import static com.example.culturecloud.Activity.VideoInfoActivity.videoInfo;


public class VideoPlayActivity extends BaseActivity {
    private VideoView mVideoView;
    JZVideoPlayerStandard jzVideoPlayerStandard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        VideoInfo videoInfo = (VideoInfo) getIntent().getSerializableExtra("video");
        String url ="";
        if (videoInfo.vi_file.startsWith("http")){
            url = videoInfo.vi_file;
        }else{
            url = NetworkInfo.ip_address+videoInfo.vi_file;
        }

        jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.videoplayer);
        jzVideoPlayerStandard.setUp(url, JZVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN,videoInfo.vi_name );
        jzVideoPlayerStandard.tinyBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        jzVideoPlayerStandard.titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        jzVideoPlayerStandard.fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        jzVideoPlayerStandard.backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();
            }
        });
        Glide.with(VideoPlayActivity.this)
                .load(NetworkInfo.static_address+videoInfo.vi_cover)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        jzVideoPlayerStandard.thumbImageView.setImageBitmap(bitmap);
                    }
                }); //加载新照片

    }
    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
       /* if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();*/
       finish();
        //jzVideoPlayerStandard.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }


}

