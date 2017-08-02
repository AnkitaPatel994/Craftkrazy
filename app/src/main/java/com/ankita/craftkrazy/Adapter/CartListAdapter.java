package com.ankita.craftkrazy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ankita.craftkrazy.Activity.CartActivity;
import com.ankita.craftkrazy.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by shyam group on 5/2/2017.
 */

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

    Context context;
    ArrayList<HashMap<String, String>> cartListProductList;
    private SQLiteDatabase database;
    public CartListAdapter(Context context, ArrayList<HashMap<String, String>> cartListProductList) {
        this.context=context;
        this.cartListProductList=cartListProductList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartlist_product,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.tvProdName.setText(cartListProductList.get(position).get("p_name"));

        final int pId = Integer.parseInt(cartListProductList.get(position).get("p_id"));

        int price = Integer.parseInt(cartListProductList.get(position).get("p_price"));

        int discount = Integer.parseInt(cartListProductList.get(position).get("p_discount"));

        int reprice = price -(price*discount/100);
        viewHolder.tvProdPrise.setText("Rs."+reprice);

        String url = cartListProductList.get(position).get("p_img");

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
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

        imageLoader.displayImage(url,viewHolder.ivProductImg, options);

        database = context.openOrCreateDatabase("mydb",MODE_PRIVATE,null);
        Cursor c = database.rawQuery("select pqty from cart",null);
        if(c.moveToFirst() && c!=null)
        {
            int Qty = c.getInt(c.getColumnIndex("pqty"));
            viewHolder.tvQty.setText(""+Qty);
        }
        database.close();

        viewHolder.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = context.openOrCreateDatabase("mydb",MODE_PRIVATE,null);
                database.delete("cart","proid = ?",new String[]{String.valueOf(pId)});
                database.close();

                Toast.makeText(context,"Close",Toast.LENGTH_SHORT).show();

                Intent i = new Intent(context,CartActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        viewHolder.ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Minus",Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Plus",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartListProductList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvProdName,tvProdPrise,tvQty;
        ImageView ivProductImg,ivClose,ivMinus,ivPlus;

        public ViewHolder(View v) {
            super(v);

            tvProdName = (TextView)v.findViewById(R.id.tvProdName);
            tvProdPrise = (TextView)v.findViewById(R.id.tvProdPrise);
            tvQty = (TextView)v.findViewById(R.id.tvQty);
            ivProductImg = (ImageView)v.findViewById(R.id.ivProductImg);
            ivClose = (ImageView)v.findViewById(R.id.ivClose);
            ivMinus = (ImageView)v.findViewById(R.id.ivMinus);
            ivPlus = (ImageView)v.findViewById(R.id.ivPlus);
        }
    }
}
