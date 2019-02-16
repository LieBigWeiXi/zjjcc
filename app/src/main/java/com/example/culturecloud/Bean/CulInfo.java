package com.example.culturecloud.Bean;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by DELL on 2018/7/11.
 */

public class CulInfo {
    public int code;//错误码
    public int count;//数据总量
    public String msg;//错误信息
    public List<culData> data;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<culData> getData() {
        return data;
    }

    public void setData(List<culData> data) {
        this.data = data;
    }


    public class culData{
        public String ci_name;//文化名人姓名
        public String ci_info;//简介
        public String ci_photo;//图片
        public String getCi_name() {
            return ci_name;
        }

        public void setCi_name(String ci_name) {
            this.ci_name = ci_name;
        }

        public String getCi_info() {
            return ci_info;
        }

        public void setCi_info(String ci_info) {
            this.ci_info = ci_info;
        }

        public String getCi_photo() {
            return ci_photo;
        }

        public void setCi_photo(String ci_photo) {
            this.ci_photo = ci_photo;
        }
    }
}
