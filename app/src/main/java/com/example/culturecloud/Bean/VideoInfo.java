package com.example.culturecloud.Bean;

import java.io.Serializable;

/**
 * Created by DELL on 2018/7/15.
 */

public class VideoInfo implements Serializable {
    public int id;
    public String vi_name;
    public String vi_cover;
    public String vi_date;
    public String vi_director;
    public String vi_actor;
    public String vi_info;
    public int vi_type;//视频类型编号
    public String vi_file;
}
