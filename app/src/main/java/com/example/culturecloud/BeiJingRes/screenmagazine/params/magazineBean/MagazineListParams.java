package com.example.culturecloud.BeiJingRes.screenmagazine.params.magazineBean;

import com.example.culturecloud.BeiJingRes.BaseParem;

/**
 * 分类下资源列表接口输入参数
 */

public class MagazineListParams extends BaseParem {

    private int page_num;//当前页码
    private int page_limit;//分页资源数量
    private String head_letter;//传入字母
    private String cover_format;//封面图质量
    private String theme_class_id;//传入主题分类
    private String crowd_class_id;//传入人群分类


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

    public String getHead_letter() {
        return head_letter;
    }

    public void setHead_letter(String head_letter) {
        this.head_letter = head_letter;
    }

    public String getCover_format() {
        return cover_format;
    }

    public void setCover_format(String cover_format) {
        this.cover_format = cover_format;
    }

    public String getTheme_class_id() {
        return theme_class_id;
    }

    public void setTheme_class_id(String theme_class_id) {
        this.theme_class_id = theme_class_id;
    }

    public String getCrowd_class_id() {
        return crowd_class_id;
    }

    public void setCrowd_class_id(String crowd_class_id) {
        this.crowd_class_id = crowd_class_id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
