package com.ankita.craftkrazy.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ankita.craftkrazy.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by shyam group on 4/21/2017.
 */

public class GridListAdapter extends RecyclerView.Adapter<GridListAdapter.ViewHolder> {

    Context context;
    ArrayList<HashMap<String, String>> GridListProductList;
    private SQLiteDatabase database;

    public GridListAdapter(Context context, ArrayList<HashMap<String, String>> GridListProductList) {

        this.context = context;
        this.GridListProductList=GridListProductList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridlist_product, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        database = context.openOrCreateDatabase("mydb",MODE_PRIVATE,null);
        String create_wish = "CREATE TABLE IF NOT EXISTS wish(id INTEGER PRIMARY KEY AUTOINCREMENT,pid INTEGER);";
        database.execSQL(create_wish);

        String create_cart = "CREATE TABLE IF NOT EXISTS cart(id INTEGER PRIMARY KEY AUTOINCREMENT,proid INTEGER,pqty INTEGER);";
        database.execSQL(create_cart);
        database.close();

        final int pId = Integer.parseInt(GridListProductList.get(position).get("p_id"));
        final int qty = 1;

        viewHolder.tvProduct.setText(GridListProductList.get(position).get("p_name"));

        int price = Integer.parseInt(GridListProductList.get(position).get("p_price"));
        viewHolder.tvPrice.setText("Rs."+price);

        int discount = Integer.parseInt(GridListProductList.get(position).get("p_discount"));
        viewHolder.tvDiscount.setText(discount+"% off");

        int reprice = price -(price*discount/100);
        viewHolder.tvReprice.setText("Rs."+reprice);

        String url = GridListProductList.get(position).get("p_img");

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

        imageLoader.displayImage(url, viewHolder.ivProduct, options);

        database = context.openOrCreateDatabase("mydb",MODE_PRIVATE,null);
        Cursor c = database.rawQuery("select pid from wish where pid =?",new String[]{String.valueOf(pId)});
        if(c.moveToFirst() && c!=null)
        {
            if(pId == c.getInt(c.getColumnIndex("pid")))
            {
                viewHolder.ivWish.setVisibility(View.GONE);
                viewHolder.ivWishFill.setVisibility(View.VISIBLE);
            }
            else
            {
                viewHolder.ivWish.setVisibility(View.VISIBLE);
                viewHolder.ivWishFill.setVisibility(View.GONE);
            }
        }
        database.close();
        database = context.openOrCreateDatabase("mydb",MODE_PRIVATE,null);
        Cursor car = database.rawQuery("select proid from cart where proid =?",new String[]{String.valueOf(pId)});
        if(car.moveToFirst() && car!=null)
        {
            if(pId == car.getInt(car.getColumnIndex("proid")))
            {
                viewHolder.ivCart.setVisibility(View.GONE);
                viewHolder.ivCartFill.setVisibility(View.VISIBLE);
            }
            else
            {
                viewHolder.ivCart.setVisibility(View.VISIBLE);
                viewHolder.ivCartFill.setVisibility(View.GONE);
            }
        }
        database.close();
        viewHolder.layWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(viewHolder.ivWish.getVisibility() == View.VISIBLE)
                {
                    viewHolder.ivWish.setVisibility(View.GONE);
                    viewHolder.ivCartFill.setVisibility(View.GONE);
                    viewHolder.ivCart.setVisibility(View.VISIBLE);
                    viewHolder.ivWishFill.setVisibility(View.VISIBLE);

                    database = context.openOrCreateDatabase("mydb",MODE_PRIVATE,null);
                    ContentValues wish = new ContentValues();
                    wish.put("pid",pId);
                    database.insert("wish",null,wish);
                    database.close();

                    database = context.openOrCreateDatabase("mydb",MODE_PRIVATE,null);
                    database.delete("cart","proid = ?",new String[]{String.valueOf(pId)});
                    database.close();

                    Toast.makeText(context,"Item added to WishList",Toast.LENGTH_SHORT).show();
                }
                else if(viewHolder.ivWishFill.getVisibility() == View.VISIBLE)
                {
                    viewHolder.ivWish.setVisibility(View.VISIBLE);
                    viewHolder.ivWishFill.setVisibility(View.GONE);

                    database = context.openOrCreateDatabase("mydb",MODE_PRIVATE,null);
                    database.delete("wish","pid = ?",new String[]{String.valueOf(pId)});
                    database.close();

                    Toast.makeText(context,"Item Removed From WishList",Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewHolder.layCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(viewHolder.ivCart.getVisibility() == View.VISIBLE) {
                    viewHolder.ivCart.setVisibility(View.GONE);
                    viewHolder.ivWishFill.setVisibility(View.GONE);
                    viewHolder.ivWish.setVisibility(View.GONE);
                    viewHolder.ivWish1.setVisibility(View.VISIBLE);
                    viewHolder.ivCartFill.setVisibility(View.VISIBLE);

                    database = context.openOrCreateDatabase("mydb", MODE_PRIVATE, null);
                    ContentValues cart = new ContentValues();
                    cart.put("proid", pId);
                    cart.put("pqty", qty);

                    database.insert("cart", null, cart);
                    database.close();

                    database = context.openOrCreateDatabase("mydb", MODE_PRIVATE, null);
                    database.delete("wish", "pid = ?", new String[]{String.valueOf(pId)});
                    database.close();

                    Toast.makeText(context, "Item added to Shopping Cart", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return GridListProductList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivProduct,ivWish,ivWish1,ivWishFill,ivCart,ivCartFill;
        public TextView tvProduct,tvReprice,tvPrice,tvDiscount;
        RelativeLayout layWish,layCart;

        public ViewHolder(View v) {
            super(v);
            layWish = (RelativeLayout)v.findViewById(R.id.layWish);
            layCart = (RelativeLayout)v.findViewById(R.id.layCart);
            ivProduct = (ImageView) v.findViewById(R.id.ivProduct);
            ivWish = (ImageView) v.findViewById(R.id.ivWish);
            ivWish1 = (ImageView) v.findViewById(R.id.ivWish1);
            ivCart = (ImageView) v.findViewById(R.id.ivCart);
            ivWishFill = (ImageView) v.findViewById(R.id.ivWishFill);
            ivCartFill = (ImageView) v.findViewById(R.id.ivCartFill);
            tvProduct = (TextView) v.findViewById(R.id.tvProduct);
            tvReprice = (TextView) v.findViewById(R.id.tvReprice);
            tvPrice = (TextView) v.findViewById(R.id.tvPrice);
            tvPrice.setPaintFlags(tvPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            tvDiscount = (TextView) v.findViewById(R.id.tvDiscount);
        }
    }
}
