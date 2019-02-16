package com.example.culturecloud.Bean;

import java.util.Date;

/**
 * 机器信息
 */

public class MechanismBean {
    public int id;
    public String name;
    public String code;//机器码
    public String key;//机器密钥
    public String register_time;//注册时间
    public int years;//有效使用年限
    public String pano_url;
    public String superior;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public String getPano_url() {
        return pano_url;
    }

    public void setPano_url(String pano_url) {
        this.pano_url = pano_url;
    }

    public String getSuperior() {
        return superior;
    }

    public void setSuperior(String superior) {
        this.superior = superior;
    }
}
