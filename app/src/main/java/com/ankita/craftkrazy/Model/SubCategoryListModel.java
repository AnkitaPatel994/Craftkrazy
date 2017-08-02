package com.ankita.craftkrazy.Model;

import java.util.ArrayList;

/**
 * Created by Asus on 10-04-2017.
 */

public class SubCategoryListModel
{
    private String s_id;
    private String c_id;
    private String s_name;
    ArrayList<SubSubCategoryListModel> subSubCatList;

    public SubCategoryListModel(String s_id, String c_id, String s_name, ArrayList<SubSubCategoryListModel> subSubCatList)
    {
        this.s_id=s_id;
        this.c_id=c_id;
        this.s_name=s_name;
        this.subSubCatList=subSubCatList;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getc_id() {
        return c_id;
    }

    public void setc_id(String c_id) {
        this.c_id = c_id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public ArrayList<SubSubCategoryListModel> getSubSubCatList(){
        return subSubCatList;
    }
    public void setSubSubCatList(ArrayList<SubSubCategoryListModel> subSubCatList){
        this.subSubCatList=subSubCatList;
    }
}
