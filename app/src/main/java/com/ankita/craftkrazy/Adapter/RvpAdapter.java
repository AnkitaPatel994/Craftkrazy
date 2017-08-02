package com.ankita.craftkrazy.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
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
public class RvpAdapter extends RecyclerView.Adapter<RvpAdapter.ViewHolder> {

    Context act;
    ArrayList<HashMap<String,String>> RvProduct;

    public RvpAdapter(Context context, ArrayList<HashMap<String, String>> RvProductList) {

        act=context;
        RvProduct=RvProductList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recentproduct, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.txtRViewPname.setText( RvProduct.get(position).get("p_name"));

        int price = Integer.parseInt( RvProduct.get(position).get("p_price"));
        viewHolder.txtRViewPprice.setText("Rs."+price);
        int discount = Integer.parseInt( RvProduct.get(position).get("p_discount"));
        viewHolder.txtRViewPDiscount.setText(""+discount+"% off");
        int reprice = price -(price*discount/100);
        viewHolder.txtRViewPReprice.setText("Rs."+reprice);

        String url =  RvProduct.get(position).get("p_img");

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


        imageLoader.displayImage(url, viewHolder.ImgRView, options);

    }

    @Override
    public int getItemCount() {
        return  RvProduct.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ImgRView;
        public TextView txtRViewPname,txtRViewPReprice,txtRViewPprice,txtRViewPDiscount;

        public ViewHolder(View v) {
            super(v);
            ImgRView = (ImageView) v.findViewById(R.id.ImgRView);
            txtRViewPname= (TextView) v.findViewById(R.id.txtRViewPname);
            txtRViewPReprice= (TextView) v.findViewById(R.id.txtRViewPReprice);
            txtRViewPprice = (TextView) v.findViewById(R.id.txtRViewPprice);
            txtRViewPprice.setPaintFlags(txtRViewPprice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            txtRViewPDiscount = (TextView) v.findViewById(R.id.txtRViewPDiscount);
        }
    }
}
