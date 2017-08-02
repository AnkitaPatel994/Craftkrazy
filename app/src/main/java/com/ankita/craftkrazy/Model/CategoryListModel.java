package com.ankita.craftkrazy.Model;

import java.util.ArrayList;

/**
 * Created by Asus on 10-04-2017.
 */

public class CategoryListModel
{
    private String c_id;
    private String m_id;
    private String c_name;
    private String c_img;
    private ArrayList<SubCategoryListModel> subCatList;

    public CategoryListModel(String c_id, String m_id, String c_name, ArrayList<SubCategoryListModel> subCatList) {
        this.c_id=c_id;
        this.m_id=m_id;
        this.c_name=c_name;
        this.subCatList=subCatList;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_img() {
        return c_img;
    }

    public void setC_img(String c_img) {
        this.c_img = c_img;
    }

    public ArrayList<SubCategoryListModel> getSubCatList() {
        return subCatList;
    }

    public void setSubCatList(ArrayList<SubCategoryListModel> subCatList) {
        this.subCatList = subCatList;
    }


}
