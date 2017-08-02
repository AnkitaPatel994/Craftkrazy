package com.ankita.craftkrazy.Model;

import java.util.ArrayList;

/**
 * Created by Asus on 10-04-2017.
 */

public class MainListModel {
    private String m_id;
    private String m_Name;
    private String m_picture;
    private ArrayList<CategoryListModel> arrayList;

    public MainListModel(String m_id, String m_Name, ArrayList<CategoryListModel> arrayList) {
        this.m_id=m_id;
        this.m_Name=m_Name;
        this.m_picture=m_picture;
        this.arrayList=arrayList;
    }

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getM_Name() {
        return m_Name;
    }

    public void setM_Name(String m_Name) {
        this.m_Name = m_Name;
    }

    public String getM_picture() {
        return m_picture;
    }

    public void setM_picture(String m_picture) {
        this.m_picture = m_picture;
    }

    public ArrayList<CategoryListModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<CategoryListModel> arrayList) {
        this.arrayList = arrayList;
    }
}
