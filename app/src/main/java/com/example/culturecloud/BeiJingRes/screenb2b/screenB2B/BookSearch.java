package com.example.culturecloud.BeiJingRes.screenb2b.screenB2B;

import com.example.culturecloud.BeiJingRes.BaseParem;

/**
 * Created by DELL on 2018/8/28.
 */

public class BookSearch extends BaseParem{
    public int platform;
    public int page_num;
    public int page_limit;
    public int keywords;

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public int getPage_num() {
        return page_num;
    }

    public void setPage_num(int page_num) {
        this.page_num = page_num;
    }

    public int getPage_limit() {
        return page_limit;
    }

    public void setPage_limit(int page_limit) {
        this.page_limit = page_limit;
    }

    public int getKeywords() {
        return keywords;
    }

    public void setKeywords(int keywords) {
        this.keywords = keywords;
    }
}
