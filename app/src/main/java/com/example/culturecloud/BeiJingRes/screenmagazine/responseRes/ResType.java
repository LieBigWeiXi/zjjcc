package com.example.culturecloud.BeiJingRes.screenmagazine.responseRes;

import java.util.List;

/**
 * Created by DELL on 2018/8/23.
 */

public class ResType {
    private ThemeType themeType;
    private CrowdType crowdType;
    private LetterType letterType;

    private List<ThemeType> theme_class_array;
    private List<CrowdType> crowd_class_array;
    private List<LetterType> letter_class_array;

    public ThemeType newThemeType(){
        return new ThemeType();
    }
    public void newCrowdType(){
        crowdType = new CrowdType();
    }
    public void newLetterType(){
        letterType = new LetterType();
    }

    public List<ThemeType> getTheme_class_array() {
        return theme_class_array;
    }

    public void setTheme_class_array(List<ThemeType> theme_class_array) {
        this.theme_class_array = theme_class_array;
    }

    public List<CrowdType> getCrowd_class_array() {
        return crowd_class_array;
    }

    public void setCrowd_class_array(List<CrowdType> crowd_class_array) {
        this.crowd_class_array = crowd_class_array;
    }

    public List<LetterType> getLetter_class_array() {
        return letter_class_array;
    }

    public void setLetter_class_array(List<LetterType> letter_class_array) {
        this.letter_class_array = letter_class_array;
    }

    public  class ThemeType{
        int class_id;
        String class_name;

        public int getClass_id() {
            return class_id;
        }

        public void setClass_id(int class_id) {
            this.class_id = class_id;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }
    };
    public class CrowdType{
        int class_id;
        String class_name;

        public int getClass_id() {
            return class_id;
        }

        public void setClass_id(int class_id) {
            this.class_id = class_id;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }
    };
    public class LetterType{
        String head_letter;

        public String getHeader_letter() {
            return head_letter;
        }

        public void setHeader_letter(String head_letter) {
            this.head_letter = head_letter;
        }
    };
}
