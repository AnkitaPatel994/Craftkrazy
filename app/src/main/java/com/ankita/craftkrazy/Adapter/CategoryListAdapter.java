package com.ankita.craftkrazy.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ankita.craftkrazy.Library.ExpandedListView;
import com.ankita.craftkrazy.Model.CategoryListModel;
import com.ankita.craftkrazy.R;

import java.util.ArrayList;


/**
 * Created by shyam group on 4/15/2017.
 */
public class CategoryListAdapter extends BaseAdapter {

    Context act;
    LayoutInflater inflater;
    ArrayList<CategoryListModel> category;

    public CategoryListAdapter(Context c, ArrayList<CategoryListModel> categoryList) {
        act = c;
        category = categoryList;
    }

    @Override
    public int getCount() {
        return category.size();
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
        View v = inflater.inflate(R.layout.list_category, null);
        final TextView txtCategory= (TextView) v.findViewById(R.id.txtCategory);

        ExpandedListView listSubCategory=(ExpandedListView)v.findViewById(R.id.listSubCategory);

        SubCategoryListAdapter subadapter=new SubCategoryListAdapter(act, category.get(position).getSubCatList());
        listSubCategory.setAdapter(subadapter);

        txtCategory.setText(category.get(position).getC_name());

        final int mainId= Integer.parseInt(category.get(position).getM_id());

        final ImageView ivCplus = (ImageView)v.findViewById(R.id.ivCplus);

        final RelativeLayout rlSubCategory=(RelativeLayout)v.findViewById(R.id.rlSubCategory);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rlSubCategory.getVisibility() == View.VISIBLE)
                {
                    rlSubCategory.setVisibility(View.GONE);
                    ivCplus.setImageResource(R.drawable.ic_add_black_24dp);
                    txtCategory.setTextColor(ContextCompat.getColor(act,R.color.colorDefault));
                }
                else
                {
                    rlSubCategory.setVisibility(View.VISIBLE);
                    ivCplus.setImageResource(R.drawable.ic_remove_black_24dp);
                    txtCategory.setTextColor(ContextCompat.getColor(act,R.color.colorPrimary));
                }
            }
        });

        return v;
    }
}
