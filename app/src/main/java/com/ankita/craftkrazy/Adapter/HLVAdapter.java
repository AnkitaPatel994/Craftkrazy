package com.ankita.craftkrazy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ankita.craftkrazy.Activity.CategoryActivity;
import com.ankita.craftkrazy.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class HLVAdapter extends RecyclerView.Adapter<HLVAdapter.ViewHolder> {

    Context act;
    View v;
    ArrayList<HashMap<String, String>> mCategory;

    public HLVAdapter(Context mainActivity, ArrayList<HashMap<String, String>> mCatList) {
        mCategory = mCatList;
        act = mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_hori_img, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final String mainCatname= mCategory.get(i).get("mc_name");
        viewHolder.txtName.setText(mainCatname);

        String url = mCategory.get(i).get("mc_image");

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                act)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);

        ImageLoader imageLoader = ImageLoader.getInstance();
        int fallback = 0;
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(fallback)
                .showImageOnFail(fallback)
                .showImageOnLoading(fallback).build();


        imageLoader.displayImage(url, viewHolder.ivImg, options);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(act, CategoryActivity.class);
                i.putExtra("mainCatname",mainCatname);
                act.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategory.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView ivImg;
        public TextView txtName;
        public ViewHolder(View v) {
            super(v);
            ivImg = (CircleImageView) v.findViewById(R.id.ivImg);
            txtName = (TextView) v.findViewById(R.id.txtName);
        }
    }
}