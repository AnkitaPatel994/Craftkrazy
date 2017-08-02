package com.ankita.craftkrazy.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ankita.craftkrazy.Activity.WishListActivity;
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
 * Created by shyam group on 5/1/2017.
 */

public class WishListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> wishListProductList;
    private SQLiteDatabase database;

    public WishListAdapter(Context context, ArrayList<HashMap<String, String>> wishListProductList) {
        this.context=context;
        this.wishListProductList=wishListProductList;
    }

    @Override
    public int getCount() {
        return wishListProductList.size();
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
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.wishlist_product, null);

        ImageView ivWishProduct = (ImageView)v.findViewById(R.id.ivWishProduct);
        ImageView ivWishCart= (ImageView)v.findViewById(R.id.ivWishCart);
        ImageView ivDelete= (ImageView)v.findViewById(R.id.ivDelete);
        TextView tvWishProduct = (TextView)v.findViewById(R.id.tvWishProduct);
        TextView tvWishReprice = (TextView)v.findViewById(R.id.tvWishReprice);
        TextView tvWishPrice = (TextView)v.findViewById(R.id.tvWishPrice);
        TextView tvWishDiscount = (TextView)v.findViewById(R.id.tvWishDiscount);

        final int pId = Integer.parseInt(wishListProductList.get(position).get("p_id"));
        final int qty = 1;

        tvWishProduct.setText(wishListProductList.get(position).get("p_name"));

        int price = Integer.parseInt(wishListProductList.get(position).get("p_price"));
        tvWishPrice.setText("Rs."+price);

        int discount = Integer.parseInt(wishListProductList.get(position).get("p_discount"));
        tvWishDiscount.setText(discount+"% off");

        if(discount == 0)
        {
            tvWishPrice.setVisibility(View.GONE);
            tvWishDiscount.setVisibility(View.GONE);
        }

        int reprice = price -(price*discount/100);
        tvWishReprice.setText("Rs."+reprice);

        String url = wishListProductList.get(position).get("p_img");

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

        imageLoader.displayImage(url,ivWishProduct, options);

        ivWishCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = context.openOrCreateDatabase("mydb",MODE_PRIVATE,null);

                ContentValues cart = new ContentValues();
                cart.put("proid",pId);
                cart.put("pqty",qty);

                database.insert("cart",null,cart);

                database.delete("wish","pid = ?",new String[]{String.valueOf(pId)});
                database.close();
                Intent i = new Intent(context, WishListActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                Toast.makeText(context,"Add to Cart",Toast.LENGTH_SHORT).show();
            }
        });
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = context.openOrCreateDatabase("mydb",MODE_PRIVATE,null);
                database.delete("wish","pid = ?",new String[]{String.valueOf(pId)});
                database.close();
                Intent i = new Intent(context, WishListActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                Toast.makeText(context,"Delete Form Wishlist",Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}
