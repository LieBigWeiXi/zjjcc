package com.example.culturecloud.Bean;

import java.util.ArrayList;

/**
 * Created by DELL on 2018/7/15.
 */

public class VideoTypeBean {
    public String status;
    public VideoList list;

    public class VideoList{
        public ArrayList<VideoInfo> Dance;
        public ArrayList<VideoInfo> Sing;
        public ArrayList<VideoInfo> Other;
        public ArrayList<VideoInfo> Stage;
    }
}
