package com.ankita.craftkrazy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ankita.craftkrazy.Activity.AllProductActivity;
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
public class DpAdapter extends RecyclerView.Adapter<DpAdapter.ViewHolder> {

    Context act;
    ArrayList<HashMap<String,String>> DProduct;

    public DpAdapter(Context context, ArrayList<HashMap<String, String>> dProductList) {

        act=context;
        DProduct=dProductList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dealproduct, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        final String deal = "Deal of the Day";
        final String hotDeal = "Hot Deal";

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(act,"Deal of the Day",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(act,AllProductActivity.class);
                i.putExtra("Deal of the Day",deal);
                i.putExtra("Hot Deal",hotDeal);
                act.startActivity(i);

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.txtDPname.setText(DProduct.get(position).get("p_name"));

        int price = Integer.parseInt(DProduct.get(position).get("p_price"));
        viewHolder.txtDPprice.setText("Rs."+price);
        int discount = Integer.parseInt(DProduct.get(position).get("p_discount"));
        viewHolder.txtDPDiscount.setText(""+discount+"% off");
        int reprice = price -(price*discount/100);
        viewHolder.txtDPReprice.setText("Rs."+reprice);

        String url = DProduct.get(position).get("p_img");

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(act)
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

        imageLoader.displayImage(url, viewHolder.ImgDeal, options);

    }

    @Override
    public int getItemCount() {
        return DProduct.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ImgDeal;
        public TextView txtDPname,txtDPReprice,txtDPprice,txtDPDiscount;

        public ViewHolder(View v) {
            super(v);
            ImgDeal = (ImageView) v.findViewById(R.id.ImgDeal);
            txtDPname = (TextView) v.findViewById(R.id.txtDPname);
            txtDPReprice = (TextView) v.findViewById(R.id.txtDPReprice);
            txtDPprice = (TextView) v.findViewById(R.id.txtDPprice);
            txtDPprice.setPaintFlags(txtDPprice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            txtDPDiscount = (TextView) v.findViewById(R.id.txtDPDiscount);
        }
    }
}
