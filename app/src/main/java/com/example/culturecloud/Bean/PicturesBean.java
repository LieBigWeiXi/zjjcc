package com.example.culturecloud.Bean;

import java.io.Serializable;

/**
 * Created by DELL on 2018/7/13.
 */

public class PicturesBean implements Serializable{
    private int id;
    private String ci_name;
    private String ci_info;
    private String ci_old;
    private String ci_new;
    private String ci_keys;
    private String ci_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getCi_old() {
        return ci_old;
    }

    public void setCi_old(String ci_old) {
        this.ci_old = ci_old;
    }

    public String getCi_new() {
        return ci_new;
    }

    public void setCi_new(String ci_new) {
        this.ci_new = ci_new;
    }

    public String getCi_keys() {
        return ci_keys;
    }

    public void setCi_keys(String ci_keys) {
        this.ci_keys = ci_keys;
    }

    public String getCi_type() {
        return ci_type;
    }

    public void setCi_type(String ci_type) {
        this.ci_type = ci_type;
    }
}
