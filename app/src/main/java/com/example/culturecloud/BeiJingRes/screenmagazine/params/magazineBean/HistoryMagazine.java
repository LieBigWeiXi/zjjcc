package com.example.culturecloud.BeiJingRes.screenmagazine.params.magazineBean;

import com.example.culturecloud.BeiJingRes.BaseParem;

/**
 * 期刊过刊接口输入参数.
 */

public class HistoryMagazine extends BaseParem {

    private int magzine_id;//杂志编号
    private String cover_format;//封面图质量

    public int getMagzine_id() {
        return magzine_id;
    }

    public void setMagzine_id(int magzine_id) {
        this.magzine_id = magzine_id;
    }

    public String getCover_format() {
        return cover_format;
    }

    public void setCover_format(String cover_format) {
        this.cover_format = cover_format;
    }

}
