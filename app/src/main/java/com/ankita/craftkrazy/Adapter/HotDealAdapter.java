package com.ankita.craftkrazy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ankita.craftkrazy.Activity.AllProductActivity;
import com.ankita.craftkrazy.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shyam group on 4/11/2017.
 */
public class HotDealAdapter extends RecyclerView.Adapter<HotDealAdapter.ViewHolder> {
    Context act;
    ArrayList<HashMap<String, String>> hdeal;

    public HotDealAdapter(Context c,ArrayList<HashMap<String, String>> hdeallist) {
        act = c;
        hdeal = hdeallist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.categorybuttonview, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final String mcatName = hdeal.get(position).get("m_name");
        final String mcatId = hdeal.get(position).get("m_id");
        viewHolder.btnHDeal.setText(mcatName);

        for(int i = 0;i<position;i++)
        {
            viewHolder.btnHotd.setVisibility(View.GONE);
        }
        if(position == 0)
        {
            viewHolder.hdLabel = "Hot Deal";
            viewHolder.btnHotd.setText(viewHolder.hdLabel);
            viewHolder.btnHotd.setBackgroundColor(ContextCompat.getColor(act,R.color.colorDefault));
            viewHolder.btnHotd.setVisibility(View.VISIBLE);
        }
        viewHolder.btnHotd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),""+viewHolder.hdLabel,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(act, AllProductActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("Hot Deal",viewHolder.hdLabel);
                act.startActivity(i);
            }
        });
        viewHolder.btnHDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(act, AllProductActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("mcatId",mcatId);
                act.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return hdeal.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        Button btnHDeal,btnHotd;
        String hd;
        String hdLabel;
        public ViewHolder(View v) {
            super(v);
            btnHDeal = (Button)v.findViewById(R.id.btnHDeal);
            btnHotd = (Button)v.findViewById(R.id.btnHotd);
        }
    }
}
