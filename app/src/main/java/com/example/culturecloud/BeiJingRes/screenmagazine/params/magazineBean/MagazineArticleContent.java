package com.example.culturecloud.BeiJingRes.screenmagazine.params.magazineBean;

import com.example.culturecloud.BeiJingRes.BaseParem;

/**
 * 期刊文章详情接口输入参数.
 */

public class MagazineArticleContent extends BaseParem {

    private int article_id;//文章id
    private int platform;//平台号

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }


}
