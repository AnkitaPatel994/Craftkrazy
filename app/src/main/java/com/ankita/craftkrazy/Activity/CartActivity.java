package com.ankita.craftkrazy.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ankita.craftkrazy.Adapter.CartListAdapter;
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

public class CartActivity extends AppCompatActivity {
    Button btnCartConti,btnCartCheckout;
    TextView tvItems,tvTP,tvSubTotal,tvVAT,tvShippingfee,tvTotalP;
    RecyclerView ivListCart;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<HashMap<String, String>> CartListProductList = new ArrayList<>();
    String url = Common.SERVICE_URL;
    private SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = getApplicationContext().openOrCreateDatabase("mydb",MODE_PRIVATE,null);
        Cursor c = database.rawQuery("select proid from cart",null);
        if(c.moveToFirst() && c!=null)
        {
            setContentView(R.layout.activity_cart);

            //========================= List View start =================================
            tvItems = (TextView)findViewById(R.id.tvItems);
            tvTP = (TextView)findViewById(R.id.tvTP);
            tvSubTotal = (TextView)findViewById(R.id.tvSubTotal);
            tvVAT = (TextView)findViewById(R.id.tvVAT);
            tvShippingfee = (TextView)findViewById(R.id.tvShippingfee);
            tvTotalP = (TextView)findViewById(R.id.tvTotalP);
            btnCartCheckout = (Button) findViewById(R.id.btnCartCheckout);
            btnCartCheckout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"Continues Order",Toast.LENGTH_SHORT).show();
                }
            });

            ivListCart = (RecyclerView)findViewById(R.id.ivListCart);
            ivListCart.setHasFixedSize(true);

            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            ivListCart.setLayoutManager(layoutManager);

            ivListCart.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));

            GetCartListProduct getWishList = new GetCartListProduct();
            getWishList.execute();



            //========================= List View stop =================================
        }
        else
        {
            setContentView(R.layout.activity_empty_cart);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //========================= BottomNavigationView start =================================
        final BottomNavigationViewEx btv = (BottomNavigationViewEx)findViewById(R.id.navigation);

        btv.enableAnimation(false);
        btv.enableShiftingMode(false);
        btv.enableItemShiftingMode(false);

        for(int i=0;i<5;i++)
        {
            Menu menu = btv.getMenu();
            MenuItem menuItem = menu.getItem(i);
            menuItem.setCheckable(false);
        }

        btv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_category)
                {
                    Intent i = new Intent(getApplicationContext(),CategoryActivity.class);
                    startActivity(i);
                }
                else if (id == R.id.nav_setting)
                {
                    Intent i = new Intent(getApplicationContext(),SettingActivity.class);
                    startActivity(i);
                }
                else if (id == R.id.nav_wishlist)
                {
                    Intent i = new Intent(getApplicationContext(),WishListActivity.class);
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

        //========================= Button start =================================
        btnCartConti = (Button)findViewById(R.id.btnCartConti);
        btnCartConti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(i);
            }
        });
        //========================= Button stop =================================



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home)
            finish();

        int id = item.getItemId();

        if (id == R.id.cart_search) {
            Intent i = new Intent(getApplicationContext(),SearchActivity.class);
            startActivity(i);
        }
        else if (id == R.id.cart_wishlist) {
            Intent i = new Intent(getApplicationContext(),WishListActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
    private class GetCartListProduct extends AsyncTask<Void,Void,Void> {
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
                                Cursor c1 = database.rawQuery("select proid from cart",null);
                                if(c1.moveToFirst())
                                {
                                    do{
                                        int id =c1.getInt(c1.getColumnIndex("proid"));
                                        if(p_id == id)
                                        {
                                            gwish.put("p_id", String.valueOf(p_id));
                                            gwish.put("p_name", p_name);
                                            gwish.put("p_price", String.valueOf(p_price));
                                            gwish.put("p_discount", String.valueOf(p_discount));
                                            gwish.put("p_img", p_img);
                                            CartListProductList.add(gwish);
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
        protected void onPreExecute() {
            super.onPreExecute();
            CartListAdapter cartListAdapter = new CartListAdapter(getApplicationContext(),CartListProductList);
            ivListCart.setAdapter(cartListAdapter);
        }
    }
}
