package com.ankita.craftkrazy.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.ankita.craftkrazy.Adapter.GridListAdapter;
import com.ankita.craftkrazy.Adapter.WishListAdapter;
import com.ankita.craftkrazy.Common;
import com.ankita.craftkrazy.HttpHandler;
import com.ankita.craftkrazy.Library.BottomNavigationViewEx;
import com.ankita.craftkrazy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class WishListActivity extends AppCompatActivity {
    Button btnWishConti;
    GridView ivListWish;
    ArrayList<HashMap<String, String>> WishListProductList = new ArrayList<>();
    String url = Common.SERVICE_URL;
    //int id;
    private SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = getApplicationContext().openOrCreateDatabase("mydb",MODE_PRIVATE,null);
        Cursor c = database.rawQuery("select pid from wish",null);
        if(c.moveToFirst() && c!=null)
        {
            setContentView(R.layout.activity_wish_list);
            ivListWish = (GridView)findViewById(R.id.ivListWish);
            GetWishListProduct getWishList = new GetWishListProduct();
            getWishList.execute();
        }
        else
        {
            setContentView(R.layout.activity_empty_wishlist);
            //========================= Button Continue Shopping start =================================
            btnWishConti = (Button)findViewById(R.id.btnWishConti);
            btnWishConti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(i);
                }
            });
            //========================= Button Continue Shopping stop =================================
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //========================= BottomNavigationView start =================================
        BottomNavigationViewEx btv = (BottomNavigationViewEx)findViewById(R.id.navigation);

        btv.enableAnimation(false);
        btv.enableShiftingMode(false);
        btv.enableItemShiftingMode(false);

        Menu menu = btv.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        btv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home)
                {
                    Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(i);
                }
                else if (id == R.id.nav_category)
                {
                    Intent i = new Intent(getApplicationContext(),CategoryActivity.class);
                    startActivity(i);
                }
                else if (id == R.id.nav_setting)
                {
                    Intent i = new Intent(getApplicationContext(),SettingActivity.class);
                    startActivity(i);
                }
                else if (id == R.id.nav_account)
                {
                    Intent i = new Intent(getApplicationContext(),AccountActivity.class);
                    startActivity(i);
                }
                return false;
            }
        });
        //========================= BottomNavigationView stop =================================

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_cart) {
            Intent i = new Intent(getApplicationContext(),CartActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_search) {
            Intent i = new Intent(getApplicationContext(),SearchActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private class GetWishListProduct extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            String response;
            HttpHandler h = new HttpHandler();
            response = h.serverConnection(url);
            if (response != null) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray product = jsonObject.getJSONArray("Product");
                    for (int i = 0; i < product.length(); i++)

                    {
                        final HashMap<String, String> gwish = new HashMap<>();
                        JSONObject j = product.getJSONObject(i);

                        final int p_id = j.getInt("p_id");
                        final String p_name = j.getString("p_name");
                        final int p_discount = j.getInt("p_discount");
                        final int p_price = j.getInt("p_price");
                        final String p_img = j.getString("p_img");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    database = getApplicationContext().openOrCreateDatabase("mydb",MODE_PRIVATE,null);
                                    Cursor c1 = database.rawQuery("select pid from wish",null);
                                    if(c1.moveToFirst())
                                    {
                                        do{
                                            int id =c1.getInt(c1.getColumnIndex("pid"));
                                            if(p_id == id)
                                            {
                                                gwish.put("p_id", String.valueOf(p_id));
                                                gwish.put("p_name", p_name);
                                                gwish.put("p_price", String.valueOf(p_price));
                                                gwish.put("p_discount", String.valueOf(p_discount));
                                                gwish.put("p_img", p_img);
                                                WishListProductList.add(gwish);
                                            }
                                        }while (c1.moveToNext());


                                    }
                                }
                            });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Server Connection Not Found..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            WishListAdapter wishListAdapter = new WishListAdapter(getApplicationContext(),WishListProductList);
            ivListWish.setAdapter(wishListAdapter);
        }
    }
}
