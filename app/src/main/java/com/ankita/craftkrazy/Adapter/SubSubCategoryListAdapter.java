package com.ankita.craftkrazy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ankita.craftkrazy.Model.SubSubCategoryListModel;
import com.ankita.craftkrazy.R;

import java.util.ArrayList;

/**
 * Created by shyam group on 4/15/2017.
 */
public class SubSubCategoryListAdapter extends BaseAdapter{
    Context act;
    LayoutInflater inflater;
    ArrayList<SubSubCategoryListModel> subSubCategory;

    public SubSubCategoryListAdapter(Context c, ArrayList<SubSubCategoryListModel> subSubCategoryList) {
        act = c;
        subSubCategory = subSubCategoryList;
    }

    @Override
    public int getCount() {
        return subSubCategory.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater)act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_subsubcategory, null);

        TextView txtSubSubCategory= (TextView) v.findViewById(R.id.txtSubSubCategory);

        txtSubSubCategory.setText(subSubCategory.get(position).getssub_name());
        return v;
    }
}
