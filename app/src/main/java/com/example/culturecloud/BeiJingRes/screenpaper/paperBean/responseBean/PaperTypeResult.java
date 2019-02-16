package com.example.culturecloud.BeiJingRes.screenpaper.paperBean.responseBean;

import java.util.List;

/**
 * Created by DELL on 2018/8/28.
 */

public class PaperTypeResult {
    public List<CycleClass>cycle_class_array;
    public List<RegionClass>region_class_array;
    public List<BusinessClass>business_class_array;
    public List<TimeList>time_list_array;

    public List<CycleClass> getCycle_class_array() {
        return cycle_class_array;
    }

    public void setCycle_class_array(List<CycleClass> cycle_class_array) {
        this.cycle_class_array = cycle_class_array;
    }

    public List<RegionClass> getRegion_class_array() {
        return region_class_array;
    }

    public void setRegion_class_array(List<RegionClass> region_class_array) {
        this.region_class_array = region_class_array;
    }

    public List<BusinessClass> getBusiness_class_array() {
        return business_class_array;
    }

    public void setBusiness_class_array(List<BusinessClass> business_class_array) {
        this.business_class_array = business_class_array;
    }

    public List<TimeList> getTime_list_array() {
        return time_list_array;
    }

    public void setTime_list_array(List<TimeList> time_list_array) {
        this.time_list_array = time_list_array;
    }

    public class CycleClass{
       public String class_id;
       public String class_name;
       public String app_id;
    }
    public class RegionClass{
        public String class_id;
        public String class_name;
        public String app_id;
    }
    public class BusinessClass{
        public String class_id;
        public String class_name;
        public String app_id;
    }
    public class TimeList{
        public int yeat;
        public int month;
    }
}
