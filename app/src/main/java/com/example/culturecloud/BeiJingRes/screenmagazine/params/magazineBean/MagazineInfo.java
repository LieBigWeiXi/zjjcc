package com.example.culturecloud.BeiJingRes.screenmagazine.params.magazineBean;

import com.example.culturecloud.BeiJingRes.BaseParem;

/**
 * 期刊详情接口输入参数
 */

public class MagazineInfo extends BaseParem {

   private int item_id;//分刊id
   private String cover_format;//封面图质量

   public int getItem_id() {
       return item_id;
   }

   public void setItem_id(int item_id) {
       this.item_id = item_id;
   }

   public String getCover_format() {
       return cover_format;
   }

   public void setCover_format(String cover_format) {
        this.cover_format = cover_format;
    }

}
