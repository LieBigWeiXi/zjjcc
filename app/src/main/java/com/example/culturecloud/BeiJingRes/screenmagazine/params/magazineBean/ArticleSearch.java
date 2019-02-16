package com.example.culturecloud.BeiJingRes.screenmagazine.params.magazineBean;

import com.example.culturecloud.BeiJingRes.BaseParem;

/**
 * 文章搜索接口.
 */

public class ArticleSearch extends BaseParem {

    private String page_num;
    private String page_limit;
    private int keywords;

    public String getPage_num() {
        return page_num;
    }

    public void setPage_num(String page_num) {
        this.page_num = page_num;
    }

    public String getPage_limit() {
        return page_limit;
    }

    public void setPage_limit(String page_limit) {
        this.page_limit = page_limit;
    }

    public int getKeywords() {
        return keywords;
    }

    public void setKeywords(int keywords) {
        this.keywords = keywords;
    }

}
