package com.ankita.craftkrazy.Model;

import java.util.ArrayList;

/**
 * Created by Asus on 10-04-2017.
 */

public class mainGridModel {
    private String m_id;
    private String m_Name;
    private ArrayList<categoryGridModel> catList;

    public mainGridModel(String m_id, String m_Name, ArrayList<categoryGridModel> catList) {
        this.m_id=m_id;
        this.m_Name=m_Name;
        this.catList=catList;
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

    public ArrayList<categoryGridModel> getCatList() {
        return catList;
    }

    public void setCatList(ArrayList<categoryGridModel> catList) {
        this.catList = catList;
    }
}
