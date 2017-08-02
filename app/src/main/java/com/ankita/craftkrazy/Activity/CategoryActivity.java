package com.ankita.craftkrazy.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.ankita.craftkrazy.Adapter.MainCateGridAdapter;
import com.ankita.craftkrazy.Common;
import com.ankita.craftkrazy.HttpHandler;
import com.ankita.craftkrazy.Library.BottomNavigationViewEx;
import com.ankita.craftkrazy.Model.categoryGridModel;
import com.ankita.craftkrazy.R;
import com.ankita.craftkrazy.Model.mainGridModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    Context c;
    ListView ListMainSubCategory;
    ArrayList<mainGridModel> mainCategoryList=new ArrayList<>();
    String url= Common.SERVICE_URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //========================= BottomNavigationView start =================================
        BottomNavigationViewEx btv = (BottomNavigationViewEx)findViewById(R.id.navigation);

        btv.enableAnimation(false);
        btv.enableShiftingMode(false);
        btv.enableItemShiftingMode(false);

        Menu menu = btv.getMenu();
        MenuItem menuItem = menu.getItem(1);
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

        //========================= Main Category start ================================

        ListMainSubCategory=(ListView)findViewById(R.id.listMainSubCat);
        GetmCat mCat=new GetmCat();
        mCat.execute();

        //========================= Main Category stop =================================

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

    private class GetmCat extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            String response;
            HttpHandler h=new HttpHandler();
            response= h.serverConnection(url);
            if(response!=null)
            {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray maincategory=jsonObject.getJSONArray("Main Category");
                    for (int im=0;im<maincategory.length();im++)
                    {

                        JSONObject jm=maincategory.getJSONObject(im);
                        String m_id=jm.getString("m_id");
                        String m_name=jm.getString("m_name");

                        JSONArray catArray=jm.getJSONArray("Category");
                        ArrayList<categoryGridModel> catList=new ArrayList<>();

                        for (int ic=0;ic<catArray.length();ic++)
                        {
                            JSONObject jc = catArray.getJSONObject(ic);
                            String C_Name = jc.getString("c_name");
                            String C_Id = jc.getString("c_id");
                            String M_Id = jc.getString("m_id");
                            String C_IMG = jc.getString("c_img");

                            categoryGridModel categoryGridModel =new categoryGridModel(C_Id,M_Id,C_Name,C_IMG);
                            catList.add(categoryGridModel);
                        }
                        mainGridModel m=new mainGridModel(m_id,m_name,catList);
                        mainCategoryList.add(m);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Server Connection Not Found..",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            MainCateGridAdapter adapter=new MainCateGridAdapter(getApplicationContext(),mainCategoryList);
            ListMainSubCategory.setAdapter(adapter);
        }
    }
}
