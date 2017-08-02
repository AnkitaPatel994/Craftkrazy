package com.ankita.craftkrazy.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ankita.craftkrazy.Adapter.GridListAdapter;
import com.ankita.craftkrazy.Library.BottomNavigationViewEx;
import com.ankita.craftkrazy.Common;
import com.ankita.craftkrazy.Adapter.HotDealAdapter;
import com.ankita.craftkrazy.HttpHandler;
import com.ankita.craftkrazy.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AllProductActivity extends AppCompatActivity {
    TextView txtViewName;
    RecyclerView rvHdeal;
    ProgressDialog dialog;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter hotDealAdapter;
    ImageView ivGridList, ivGridList1;
    LinearLayout llGridList;
    LinearLayout ListGridLay;
    ArrayList<HashMap<String, String>> Hdeallist = new ArrayList<>();
    ArrayList<HashMap<String, String>> list = new ArrayList<>();
    ArrayList<HashMap<String, String>> GridProductList = new ArrayList<>();
    ArrayList<HashMap<String, String>> ListProductList = new ArrayList<>();
    String url = Common.SERVICE_URL;
    RecyclerView GridProduct;
    RecyclerView.Adapter GridAdapter;
    RecyclerView ListProduct;
    RecyclerView.Adapter ListAdapter;
    LinearLayout laySort;
    LinearLayout layFilter;
    BottomSheetDialog bottomSheetDialog ;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //========================= BottomNavigationView start =================================
        BottomNavigationViewEx btv = (BottomNavigationViewEx) findViewById(R.id.navigation);

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
                item.setChecked(false);
                if (id == R.id.nav_home) {
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(i);
                } else if (id == R.id.nav_category) {
                    Intent i = new Intent(getApplicationContext(), CategoryActivity.class);
                    startActivity(i);
                } else if (id == R.id.nav_setting) {
                    Intent i = new Intent(getApplicationContext(), SettingActivity.class);
                    startActivity(i);
                } else if (id == R.id.nav_wishlist) {
                    Intent i = new Intent(getApplicationContext(), WishListActivity.class);
                    startActivity(i);
                } else if (id == R.id.nav_account) {
                    Intent i = new Intent(getApplicationContext(), AccountActivity.class);
                    startActivity(i);
                }
                return false;
            }
        });
        //========================= BottomNavigationView stop =================================
        String dealName = getIntent().getExtras().getString("Hot Deal");

        txtViewName = (TextView) findViewById(R.id.txtViewName);
        String name = getIntent().getExtras().getString("Deal of the Day");
        txtViewName.setText(name);
        String de = "Deal of the Day";
        txtViewName.setText(de);

        String catname = getIntent().getExtras().getString("mcatId");
        Toast.makeText(getApplicationContext(),catname,Toast.LENGTH_SHORT).show();
        //========================= Horizontal Listview Main Category Start =================================
        rvHdeal = (RecyclerView) findViewById(R.id.rvHdeal);
        rvHdeal.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvHdeal.setLayoutManager(layoutManager);

        GetHotDeal hotDeal = new GetHotDeal();
        hotDeal.execute();

        //========================= Horizontal Listview Main Category Stop ============================

        //========================= Imageview click Start ===========================
        ListGridLay = (LinearLayout) findViewById(R.id.ListGridLay);
        llGridList = (LinearLayout) findViewById(R.id.llGridList);
        ivGridList = (ImageView) findViewById(R.id.ivGridList);
        ivGridList1 = (ImageView) findViewById(R.id.ivGridList1);

        GridProduct = (RecyclerView) findViewById(R.id.GridProduct);
        GridProduct.setHasFixedSize(true);
        GridProduct.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        ListProduct = (RecyclerView) findViewById(R.id.ListProduct);

        ListGridLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivGridList.getVisibility() == View.VISIBLE)
                {
                    ivGridList.setVisibility(View.GONE);
                    GridProduct.setVisibility(View.GONE);
                    ivGridList1.setVisibility(View.VISIBLE);
                    ListProduct.setVisibility(View.VISIBLE);
                    ListProduct.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                    if(count==0)
                    {
                        GetListProduct listProduct = new GetListProduct();
                        listProduct.execute();
                        count++;
                    }
                }
                else if (ivGridList1.getVisibility() == View.VISIBLE)
                {
                    ivGridList1.setVisibility(View.GONE);
                    ListProduct.setVisibility(View.GONE);
                    ivGridList.setVisibility(View.VISIBLE);
                    GridProduct.setVisibility(View.VISIBLE);
                }
            }
        });
        GetGridProduct glProduct = new GetGridProduct();
        glProduct.execute();


        //========================= Imageview click Stop ===========================

        //========================= Sort List start =================================
        laySort = (LinearLayout)findViewById(R.id.laySort);

        laySort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialog = new BottomSheetDialog(AllProductActivity.this);
                View view1 = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
                bottomSheetDialog.setContentView(view1);
                bottomSheetDialog.show();

            }
        });
        //========================= Sort List stop =================================

        //========================= Filter start ================================
        layFilter = (LinearLayout)findViewById(R.id.layFilter);
        layFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),FilterActivity.class);
                startActivity(i);
            }
        });
        //========================= Filter stop =================================
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();

        int id = item.getItemId();

        if (id == R.id.nav_cart) {
            Intent i = new Intent(getApplicationContext(), CartActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_search) {
            Intent i = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    //========================= Horizontal Listview Main Category Start =================================
    class GetHotDeal extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            String response;
            HttpHandler h = new HttpHandler();
            response = h.serverConnection(url);
            if (response != null) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray hotdeal = jsonObject.getJSONArray("Main Category");

                    for (int i = 0; i < hotdeal.length() + 1; i++) {
                        HashMap<String, String> con = new HashMap<>();
                        JSONObject j = hotdeal.getJSONObject(i);

                        String m_id = j.getString("m_id");
                        String m_name = j.getString("m_name");

                        con.put("m_id", m_id);
                        con.put("m_name", m_name);

                        Hdeallist.add(con);
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

            hotDealAdapter = new HotDealAdapter(getApplicationContext(), Hdeallist);
            rvHdeal.setAdapter(hotDealAdapter);
        }
    }

    //========================= Horizontal Listview Main Category Stop =================================

    //========================= Grid Product Start =================================
    class GetGridProduct extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(AllProductActivity.this);
            dialog.setMessage("Loading Data..");
            dialog.setCancelable(true);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }

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
                        HashMap<String, String> gl = new HashMap<>();
                        JSONObject j = product.getJSONObject(i);

                        int p_id = j.getInt("p_id");
                        String p_name = j.getString("p_name");
                        int p_discount = j.getInt("p_discount");
                        int p_price = j.getInt("p_price");
                        String p_img = j.getString("p_img");
                        String deal = "Hot Deal";

                        if(deal.equals("Hot Deal"))
                        {
                            if(deal.equals(getIntent().getExtras().getString("Hot Deal")))
                            {
                                if(p_discount >= 8)
                                {
                                    gl.put("p_id", String.valueOf(p_id));
                                    gl.put("p_name", p_name);
                                    gl.put("p_price", String.valueOf(p_price));
                                    gl.put("p_discount", String.valueOf(p_discount));
                                    gl.put("p_img", p_img);

                                    GridProductList.add(gl);
                                }
                            }
                            else if(String.valueOf(p_id).equals(getIntent().getExtras().getString("mcatId")))
                            {
                                if(p_discount >= 8)
                                {
                                    gl.put("p_id", String.valueOf(p_id));
                                    gl.put("p_name", p_name);
                                    gl.put("p_price", String.valueOf(p_price));
                                    gl.put("p_discount", String.valueOf(p_discount));
                                    gl.put("p_img", p_img);


                                    GridProductList.add(gl);
                                }
                            }

                        }

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
            if(dialog.isShowing())
            {
                dialog.dismiss();
            }
            GridAdapter = new GridListAdapter(getApplicationContext(),GridProductList);
            GridProduct.setAdapter(GridAdapter);
        }
    }
    //========================= List Product Start =================================
    class GetListProduct extends AsyncTask<Void, Void, Void> {

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
                        HashMap<String, String> gl = new HashMap<>();
                        JSONObject j = product.getJSONObject(i);

                        int p_id = j.getInt("p_id");
                        String p_name = j.getString("p_name");
                        int p_discount = j.getInt("p_discount");
                        int p_price = j.getInt("p_price");
                        String p_img = j.getString("p_img");

                        if(p_discount >= 8)
                        {
                            gl.put("p_id", String.valueOf(p_id));
                            gl.put("p_name", p_name);
                            gl.put("p_price", String.valueOf(p_price));
                            gl.put("p_discount", String.valueOf(p_discount));
                            gl.put("p_img", p_img);

                            ListProductList.add(gl);
                        }
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
            if(dialog.isShowing())
            {
                dialog.dismiss();
            }
            ListAdapter = new GridListAdapter(getApplicationContext(),ListProductList);
            ListProduct.setAdapter(ListAdapter);
        }
    }
}