package com.example.culturecloud.Bean;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/7/22.
 */

public class NewsBean {
    public int id;
    public String ni_cover;
    public String ni_name;
    public String ni_type;
    public String ni_info;
    public String ni_url;

    public String getNi_name() {
        return ni_name;
    }

    public void setNi_name(String ni_name) {
        this.ni_name = ni_name;
    }

    public String getNi_url() {
        return ni_url;
    }

    public void setNi_url(String ni_url) {
        this.ni_url = ni_url;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNi_cover() {
        return ni_cover;
    }

    public void setNi_cover(String ni_cover) {
        this.ni_cover = ni_cover;
    }

    public String getNi_type() {
        return ni_type;
    }

    public void setNi_type(String ni_type) {
        this.ni_type = ni_type;
    }

    public String getNi_info() {
        return ni_info;
    }

    public void setNi_info(String ni_info) {
        this.ni_info = ni_info;
    }
//    int code;
//    int count;
//    List<News> data = new ArrayList<>();
//    public static class News{
//        int    id;
//        @Serializedni_name("ni_name")
//        String ni_name;
//        @Serializedni_name("ni_cover")
//        String cover;
//        @Serializedni_name("ni_url")
//        String url;
//        @Serializedni_name("ni_info")
//        String info;
//        int ni_type;
//        @Serializedni_name("time")
//        String release_time;
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getni_name() {
//            return ni_name;
//        }
//
//        public void setni_name(String ni_name) {
//            this.ni_name = ni_name;
//        }
//
//        public String getCover() {
//            return cover;
//        }
//
//        public void setCover(String cover) {
//            this.cover = cover;
//        }
//
//        public String getUrl() {
//            return url;
//        }
//
//        public void setUrl(String url) {
//            this.url = url;
//        }
//
//        public String getInfo() {
//            return info;
//        }
//
//        public void setInfo(String info) {
//            this.info = info;
//        }
//
//        public String getRelease_time() {
//            return release_time;
//        }
//
//        public void setRelease_time(String release_time) {
//            this.release_time = release_time;
//        }
//    }
//
//    public List<News> getData() {
//        return data;
//    }
//
//    public void setData(List<News> data) {
//        this.data = data;
//    }
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public int getCount() {
//        return count;
//    }
//
//    public void setCount(int count) {
//        this.count = count;
//    }
    
}
