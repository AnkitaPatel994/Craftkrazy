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
import com.ankita.craftkrazy.Model.SubCategoryListModel;
import com.ankita.craftkrazy.R;

import java.util.ArrayList;

/**
 * Created by shyam group on 4/15/2017.
 */
public class SubCategoryListAdapter extends BaseAdapter{
    Context c;
    LayoutInflater inflater;
    ArrayList<SubCategoryListModel> subCategory;

    public SubCategoryListAdapter(Context act, ArrayList<SubCategoryListModel> subCategoryList) {
        c = act;
        subCategory = subCategoryList;
    }

    @Override
    public int getCount() {
        return subCategory.size();
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
        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_subcategory, null);

        final TextView txtSubCategory= (TextView) v.findViewById(R.id.txtSubCategory);

        ExpandedListView listSubSubCategory=(ExpandedListView)v.findViewById(R.id.listSubSubCategory);

        SubSubCategoryListAdapter subSubadapter=new SubSubCategoryListAdapter(c, subCategory.get(position).getSubSubCatList());
        listSubSubCategory.setAdapter(subSubadapter);

        txtSubCategory.setText(subCategory.get(position).getS_name());

        final ImageView ivSplus = (ImageView)v.findViewById(R.id.ivSplus);

        final RelativeLayout rlSubSubCategory=(RelativeLayout)v.findViewById(R.id.rlSubSubCategory);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rlSubSubCategory.getVisibility() == View.VISIBLE)
                {
                    rlSubSubCategory.setVisibility(View.GONE);
                    ivSplus.setImageResource(R.drawable.ic_add_black_24dp);
                    txtSubCategory.setTextColor(ContextCompat.getColor(c,R.color.colorDefault));
                }
                else
                {
                    rlSubSubCategory.setVisibility(View.VISIBLE);
                    ivSplus.setImageResource(R.drawable.ic_remove_black_24dp);
                    txtSubCategory.setTextColor(ContextCompat.getColor(c,R.color.colorPrimary));
                }
            }
        });
        return v;
    }
}
