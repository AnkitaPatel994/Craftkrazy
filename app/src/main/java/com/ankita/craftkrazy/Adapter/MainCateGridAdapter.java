package com.ankita.craftkrazy.Adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ankita.craftkrazy.Model.mainGridModel;
import com.ankita.craftkrazy.R;

import java.util.ArrayList;

/**
 * Created by Lenovo on 15-01-2017.
 */

public class MainCateGridAdapter extends BaseAdapter {

    Context act;
    LayoutInflater inflater;
    ArrayList<mainGridModel> mainCategory;

    public MainCateGridAdapter(Context c, ArrayList<mainGridModel> mainCategoryList) {
        mainCategory=mainCategoryList;
        act = c;
    }

    @Override
    public int getCount() {
        return mainCategory.size();

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position)  {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater)act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item, null);

        TextView txtMainCatName= (TextView) v.findViewById(R.id.txtMainCatName);
        txtMainCatName.setText(mainCategory.get(position).getM_Name());

        RecyclerView gvSubCat = (RecyclerView)v.findViewById(R.id.Grid_SubCat_Img);
        gvSubCat.setHasFixedSize(true);

        gvSubCat.setLayoutManager(new GridLayoutManager(act,3));

        CategoryGridAdapter catadapter=new CategoryGridAdapter(act,mainCategory.get(position).getCatList());
        gvSubCat.setAdapter(catadapter);

        return v;
    }
}