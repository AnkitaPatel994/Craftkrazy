package com.ankita.craftkrazy.Model;

/**
 * Created by Asus on 10-04-2017.
 */

public class categoryGridModel
{
    private String c_id;
    private String m_id;
    private String c_name;
    private String c_img;


    public categoryGridModel(String c_id, String m_id, String c_name, String c_img) {
        this.c_id=c_id;
        this.m_id=m_id;
        this.c_name=c_name;
        this.c_img=c_img;
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

}
