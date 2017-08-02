package com.ankita.craftkrazy.Model;

/**
 * Created by Asus on 10-04-2017.
 */

public class SubSubCategoryListModel
{
    private String ssub_id;
    private String s_id;
    private String ssub_name;

    public SubSubCategoryListModel(String ssub_id, String s_id, String ssub_name) {
        this.ssub_id=ssub_id;
        this.s_id=s_id;
        this.ssub_name=ssub_name;
    }

    public String getssub_id() {
        return ssub_id;
    }

    public void setssub_id(String ssub_id) {
        this.ssub_id = ssub_id;
    }

    public String gets_id() {
        return s_id;
    }

    public void sets_id(String s_id) {
        this.s_id = s_id;
    }

    public String getssub_name() {
        return ssub_name;
    }

    public void setssub_name(String ssub_name) {
        this.ssub_name = ssub_name;
    }


}
