package com.example.culturecloud.BeiJingRes.screenmagazine.params.magazineBean;

import com.example.culturecloud.BeiJingRes.BaseParem;

/**
 * 文章列表接口输入参数.
 */

public class MagazineArticle extends BaseParem {

    private int item_id;//分刊编号
    private int platform;//平台号

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

}
