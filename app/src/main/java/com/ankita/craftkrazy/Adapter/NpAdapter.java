package com.ankita.craftkrazy.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ankita.craftkrazy.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shyam group on 3/27/2017.
 */
public class NpAdapter extends RecyclerView.Adapter<NpAdapter.ViewHolder> {

    Context act;
    ArrayList<HashMap<String,String>> NProduct;

    public NpAdapter(Context context, ArrayList<HashMap<String, String>> dProductList) {

        act=context;
        NProduct=dProductList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.newproduct, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.txtNPname.setText(NProduct.get(position).get("p_name"));

        int price = Integer.parseInt(NProduct.get(position).get("p_price"));
        viewHolder.txtNPprice.setText("Rs."+price);
        int discount = Integer.parseInt(NProduct.get(position).get("p_discount"));
        viewHolder.txtNPDiscount.setText(""+discount+"% off");
        if(discount == 0)
        {
            viewHolder.txtNPprice.setVisibility(View.GONE);
            viewHolder.txtNPDiscount.setVisibility(View.GONE);
        }
        int reprice = price -(price*discount/100);
        viewHolder.txtNPReprice.setText("Rs."+reprice);

        String url = NProduct.get(position).get("p_img");

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


        imageLoader.displayImage(url, viewHolder.ImgNew, options);

    }

    @Override
    public int getItemCount() {
        return NProduct.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ImgNew;
        public TextView txtNPname,txtNPReprice,txtNPprice,txtNPDiscount;

        public ViewHolder(View v) {
            super(v);
            ImgNew = (ImageView) v.findViewById(R.id.ImgNew);
            txtNPname = (TextView) v.findViewById(R.id.txtNPname);
            txtNPReprice = (TextView) v.findViewById(R.id.txtNPReprice);
            txtNPprice = (TextView) v.findViewById(R.id.txtNPprice);
            txtNPprice.setPaintFlags(txtNPprice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            txtNPDiscount = (TextView) v.findViewById(R.id.txtNPDiscount);
        }
    }
}
