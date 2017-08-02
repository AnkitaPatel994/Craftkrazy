package com.ankita.craftkrazy.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ankita.craftkrazy.Model.MainListModel;
import com.ankita.craftkrazy.R;

import java.util.ArrayList;

/**
 * Created by shyam group on 4/15/2017.
 */
public class MainCategoryListAdapter extends BaseAdapter {

    Context c;
    LayoutInflater inflater;
    ArrayList<MainListModel> mainCategory;

    public MainCategoryListAdapter(Context context, ArrayList<MainListModel> mainCategoryList) {
        c=context;
        mainCategory = mainCategoryList;
    }

    @Override
    public int getCount() {
        return mainCategory.size();
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
        View v = inflater.inflate(R.layout.list_maincategory, null);

        final int mainId= Integer.parseInt(mainCategory.get(position).getM_id());
        final TextView txtMainCategory= (TextView) v.findViewById(R.id.txtMainCategory);

        ListView listCategory=(ListView)v.findViewById(R.id.listCategory);

        CategoryListAdapter catadapter=new CategoryListAdapter(c,mainCategory.get(position).getArrayList());
        listCategory.setAdapter(catadapter);

        txtMainCategory.setText(mainCategory.get(position).getM_Name());

        final ImageView ivMplus = (ImageView)v.findViewById(R.id.ivMplus);
        final RelativeLayout rlCategory=(RelativeLayout)v.findViewById(R.id.rlCategory);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rlCategory.getVisibility() == View.VISIBLE)
                {
                    rlCategory.setVisibility(View.GONE);
                    ivMplus.setImageResource(R.drawable.ic_add_black_24dp);
                    txtMainCategory.setTextColor(ContextCompat.getColor(c,R.color.colorDefault));
                }
                else
                {
                    rlCategory.setVisibility(View.VISIBLE);
                    ivMplus.setImageResource(R.drawable.ic_remove_black_24dp);
                    txtMainCategory.setTextColor(ContextCompat.getColor(c,R.color.colorPrimary));
                }
            }
        });

        return v;
    }
}
