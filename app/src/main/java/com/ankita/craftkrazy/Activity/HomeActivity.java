package com.ankita.craftkrazy.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ankita.craftkrazy.Adapter.MainCategoryListAdapter;
import com.ankita.craftkrazy.Library.BottomNavigationViewEx;
import com.ankita.craftkrazy.Common;
import com.ankita.craftkrazy.Adapter.DpAdapter;
import com.ankita.craftkrazy.Adapter.HLVAdapter;
import com.ankita.craftkrazy.HttpHandler;
import com.ankita.craftkrazy.Adapter.NpAdapter;
import com.ankita.craftkrazy.Model.CategoryListModel;
import com.ankita.craftkrazy.Model.MainListModel;
import com.ankita.craftkrazy.Model.SubCategoryListModel;
import com.ankita.craftkrazy.Model.SubSubCategoryListModel;
import com.ankita.craftkrazy.R;
import com.ankita.craftkrazy.Adapter.RvpAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ScrollView scroll;
    SliderLayout sliderLayout;
    ArrayList<HashMap<String,String>> ImgList=new ArrayList<>();
    String url= Common.SERVICE_URL;
    HashMap<String,String > con;
    JSONArray slider;
    JSONObject jsonObject;
    TextView txtSign,txtSearch;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ArrayList<HashMap<String,String>> mCatList=new ArrayList<>();

    RecyclerView DpView;
    RecyclerView.LayoutManager DplayoutManager;
    RecyclerView.Adapter Dpadapter;
    ArrayList<HashMap<String,String>> DProductList=new ArrayList<>();

    RecyclerView NpView;
    RecyclerView.LayoutManager NplayoutManager;
    RecyclerView.Adapter Npadapter;
    ArrayList<HashMap<String,String>> NProductList=new ArrayList<>();

    RecyclerView RvpView;
    RecyclerView.LayoutManager RvplayoutManager;
    RecyclerView.Adapter RvpAdapter;
    ArrayList<HashMap<String,String>> RvProductList=new ArrayList<>();

    ListView listMainCategory;
    ArrayList<MainListModel> mainCategoryList=new ArrayList<>();

    TextView txtTorder,txtShareApp,txtRateApp,txtNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //========================= NavigationView click start =================================

        txtSign = (TextView)findViewById(R.id.txt_SignReg);
        txtSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SigninActivity.class);
                startActivity(i);
            }
        });
        //========================= NavigationView click start =================================

        //========================= Search start =================================

        txtSearch = (TextView)findViewById(R.id.txtSearch);
        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(i);
            }
        });
        //========================= Search start =================================
        //========================= BottomNavigationView start =================================

        final BottomNavigationViewEx btv = (BottomNavigationViewEx)findViewById(R.id.navigation);

        btv.enableAnimation(false);
        btv.enableShiftingMode(false);
        btv.enableItemShiftingMode(false);

        Menu menu = btv.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

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

        //========================= Slider Layout start =================================

        sliderLayout = (SliderLayout)findViewById(R.id.slider);

        GetImg g=new GetImg();
        g.execute();
        //========================= Slider Layout stop =================================

        //========================= Horizontal Listview Main Category Start =================================
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        GetmCategory mCategory=new GetmCategory();
        mCategory.execute();

        //========================= Horizontal Listview Main Category Stop =================================

        //========================= Horizontal Listview Deal Product Start =================================
        DpView = (RecyclerView)findViewById(R.id.DealProduct_view);
        DpView.setHasFixedSize(true);

        DplayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        DpView.setLayoutManager(DplayoutManager);

        GetDealProduct Dproduct=new GetDealProduct();
        Dproduct.execute();

        //========================= Horizontal Listview Deal Product Stop =================================

        //========================= Horizontal Listview New Arrival Product Start =================================
        NpView = (RecyclerView)findViewById(R.id.NewProduct_view);
        NpView.setHasFixedSize(true);

        NplayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        NpView.setLayoutManager(NplayoutManager);

        GetNewProduct Nproduct=new GetNewProduct();
        Nproduct.execute();

        //========================= Horizontal Listview New Arrival  Product Stop =================================

        //========================= Horizontal Listview Recently view Product Start =================================
        RvpView = (RecyclerView)findViewById(R.id.Recently_view);
        RvpView.setHasFixedSize(true);

        RvplayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RvpView.setLayoutManager(RvplayoutManager);

        GetRecProduct Rproduct=new GetRecProduct();
        Rproduct.execute();

        //========================= Horizontal Listview Recently view Product Stop =================================
        //========================= NavigationView Expandeble List Start =====================
        listMainCategory = (ListView)findViewById(R.id.listMainCat);
        GetMainCategoryList mainCat=new GetMainCategoryList();
        mainCat.execute();
        //========================= NavigationView Expandeble List Stop =====================
        txtTorder = (TextView)findViewById(R.id.txtTorder);
        txtShareApp = (TextView)findViewById(R.id.txtShareApp);
        txtRateApp = (TextView)findViewById(R.id.txtRateApp);
        txtNotification = (TextView)findViewById(R.id.txtNotification);

        txtTorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Track Orader",Toast.LENGTH_SHORT).show();
            }
        });
        txtShareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Share App",Toast.LENGTH_SHORT).show();
            }
        });
        txtRateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Rate App",Toast.LENGTH_SHORT).show();
            }
        });
        txtNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Notification",Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_cart) {
            Intent i = new Intent(getApplicationContext(),CartActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_track) {
            // Handle the camera action
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_rate) {

        } else if (id == R.id.nav_notification) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //========================= Slider Layout start =================================
    class GetImg extends AsyncTask<Void,Void,Void> implements BaseSliderView.OnSliderClickListener {
        @Override
        protected Void doInBackground(Void... params) {
            String response;
            HttpHandler h=new HttpHandler();
            response= h.serverConnection(url);
            if(response!=null)
            {
                try {
                    jsonObject=new JSONObject(response);
                    slider=jsonObject.getJSONArray("Slider");
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
            for (int i=0;i<slider.length();i++)
            {
                con = new HashMap<>();

                JSONObject j = null;
                try {
                    j = slider.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String img = null;
                int approval = 0;
                try {
                    img = j.getString("img");
                    approval=j.getInt("apporval");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(approval == 1)
                {
                    con.put(String.valueOf(i),img);
                    ImgList.add(con);
                }
                for (int i1 = i; i1 <=i; i1++) {
                    for (String name : con.keySet()) {
                        DefaultSliderView textSliderView = new DefaultSliderView(HomeActivity.this);
                        // initialize a SliderLayout
                        textSliderView
                                .image(con.get(String.valueOf(i1)))
                                .setScaleType(BaseSliderView.ScaleType.Fit)
                                .setOnSliderClickListener(this);

                        sliderLayout.addSlider(textSliderView);
                    }
                }
            }
            sliderLayout.setCustomAnimation(new DescriptionAnimation());
            sliderLayout.setDuration(5000);
        }

        @Override
        public void onSliderClick(BaseSliderView slider) {
            sliderLayout.startAutoCycle();
        }

    }
    //========================= Slider Layout stop =================================

    //========================= Horizontal Listview Main Category Start =================================
    class GetmCategory extends AsyncTask<Void,Void,Void>
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
                    JSONArray slider=jsonObject.getJSONArray("Main Category");
                    for (int i=0;i<slider.length();i++)

                    {
                        HashMap<String,String > con = new HashMap<>();
                        JSONObject j=slider.getJSONObject(i);

                        String name=j.getString("m_name");
                        String img=j.getString("m_img");

                        con.put("name",name);
                        //con.put("img",Common.SERVICE_IMG_URL +img);
                        con.put("img",img);

                        mCatList.add(con);
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

            adapter=new HLVAdapter(HomeActivity.this,mCatList);
            recyclerView.setAdapter(adapter);
        }
    }
    //========================= Horizontal Listview Main Category Stop =================================

    //========================= Horizontal Listview Deal Product Start =================================
    class GetDealProduct extends AsyncTask<Void,Void,Void>
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
                    JSONArray product=jsonObject.getJSONArray("Product");
                    for (int i=0;i<product.length();i++)

                    {
                        HashMap<String,String > dp = new HashMap<>();
                        JSONObject j=product.getJSONObject(i);

                        String p_name=j.getString("p_name");
                        int p_discount=j.getInt("p_discount");
                        int p_price=j.getInt("p_price");
                        String p_img=j.getString("p_img");
                        int dealapproval=j.getInt("dealapproval");

                        if(dealapproval == 1)
                        {
                            dp.put("p_name",p_name);
                            dp.put("p_price", String.valueOf(p_price));
                            dp.put("p_discount", String.valueOf(p_discount));
                            dp.put("dealapproval", String.valueOf(dealapproval));
                            //dp.put("p_img",Common.SERVICE_IMG_URL +p_img);
                            dp.put("p_img",p_img);

                            DProductList.add(dp);
                        }

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

            Dpadapter=new DpAdapter(HomeActivity.this,DProductList);
            DpView.setAdapter(Dpadapter);
        }
    }
    //========================= Horizontal Listview Deal Product Stop =================================

    //========================= Horizontal Listview New Arrival Product Start =================================
   class GetNewProduct extends AsyncTask<Void,Void,Void>
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
                    JSONArray product=jsonObject.getJSONArray("Product");
                    for (int i=0;i<product.length();i++)

                    {
                        HashMap<String,String > np = new HashMap<>();
                        JSONObject j=product.getJSONObject(i);

                        String p_name=j.getString("p_name");
                        int p_discount=j.getInt("p_discount");
                        int p_price=j.getInt("p_price");
                        String p_img=j.getString("p_img");

                        int newapproval=j.getInt("newapproval");

                        if(newapproval == 1)
                        {
                            np.put("p_name", p_name);
                            np.put("p_price", String.valueOf(p_price));
                            np.put("p_discount", String.valueOf(p_discount));
                            np.put("newapproval", String.valueOf(newapproval));
                            //np.put("p_img", Common.SERVICE_IMG_URL + p_img);
                            np.put("p_img",p_img);

                            NProductList.add(np);
                        }
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

            Npadapter=new NpAdapter(HomeActivity.this,NProductList);
            NpView.setAdapter(Npadapter);
        }
    }
    //========================= Horizontal Listview New Arrival Product Stop =================================

    //========================= Horizontal Listview Recently View Product Start =================================

    class GetRecProduct extends AsyncTask<Void,Void,Void>
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
                    JSONArray product=jsonObject.getJSONArray("Product");
                    for (int i=0;i<product.length();i++)

                    {
                        HashMap<String,String > rp = new HashMap<>();
                        JSONObject j=product.getJSONObject(i);

                        String p_name=j.getString("p_name");
                        int p_discount=j.getInt("p_discount");
                        int p_price=j.getInt("p_price");
                        String p_img=j.getString("p_img");

                        int recentlyapproval=j.getInt("recentlyapproval");

                        if(recentlyapproval == 1)
                        {
                            rp.put("p_name", p_name);
                            rp.put("p_price", String.valueOf(p_price));
                            rp.put("p_discount", String.valueOf(p_discount));
                            rp.put("recentlyapproval", String.valueOf(recentlyapproval));
                            rp.put("p_img",p_img);

                            RvProductList.add(rp);
                        }
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

            RvpAdapter=new RvpAdapter(HomeActivity.this,RvProductList);
            RvpView.setAdapter(RvpAdapter);
        }
    }

    //========================= Horizontal Listview Recently View Product Stop =================================
    //========================= NavigationView Expandeble List Start =====================
    private class GetMainCategoryList extends AsyncTask<Void,Void,Void>
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
                    JSONArray maincatArray=jsonObject.getJSONArray("Main Category");

                    for (int im=0;im<maincatArray.length();im++)
                    {

                        JSONObject jm=maincatArray.getJSONObject(im);
                        String m_id=jm.getString("m_id");
                        String m_name=jm.getString("m_name");

                        JSONArray catArray=jm.getJSONArray("Category");
                        ArrayList<CategoryListModel> catList=new ArrayList<>();

                        for (int ic=0;ic<catArray.length();ic++)
                        {
                            JSONObject jc=catArray.getJSONObject(ic);
                            String C_Name=jc.getString("c_name");
                            String C_Id=jc.getString("c_id");
                            String M_Id=jc.getString("m_id");

                            JSONArray subCatArray=jc.getJSONArray("Sub Category");
                            ArrayList<SubCategoryListModel> subCatList=new ArrayList<>();
                            for(int is=0;is<subCatArray.length();is++)
                            {
                                JSONObject js=subCatArray.getJSONObject(is);
                                String S_ID=js.getString("s_id");
                                String C_ID=js.getString("c_id");
                                String S_NAME=js.getString("s_name");

                                JSONArray subsubCatArray=js.getJSONArray("Sub To Sub Category");
                                ArrayList<SubSubCategoryListModel> subSubCatList=new ArrayList<>();
                                for(int iss=0;iss<subsubCatArray.length();iss++)
                                {
                                    JSONObject jss=subsubCatArray.getJSONObject(iss);
                                    String SSUB_ID=jss.getString("ssub_id");
                                    String S_Id=jss.getString("s_id");
                                    String SSUB_NAME=jss.getString("ssub_name");

                                    SubSubCategoryListModel subSubCategoryModel=new SubSubCategoryListModel(SSUB_ID,S_Id,SSUB_NAME);
                                    subSubCatList.add(subSubCategoryModel);
                                }
                                SubCategoryListModel subCategoryModel=new SubCategoryListModel(S_ID,C_ID,S_NAME,subSubCatList);
                                subCatList.add(subCategoryModel);
                            }
                            CategoryListModel categoryModel=new CategoryListModel(C_Id,M_Id,C_Name,subCatList);
                            catList.add(categoryModel);
                        }
                        MainListModel m=new MainListModel(m_id,m_name,catList);
                        mainCategoryList.add(m);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            MainCategoryListAdapter mcadapter=new MainCategoryListAdapter(getApplicationContext(),mainCategoryList);
            listMainCategory.setAdapter(mcadapter);
        }
    }
    //========================= NavigationView Expandeble List Stop =====================
}
