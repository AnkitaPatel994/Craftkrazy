package com.ankita.craftkrazy.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ankita.craftkrazy.Library.BottomNavigationViewEx;
import com.ankita.craftkrazy.R;

public class ShippingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //========================= BottomNavigationView start =================================
        BottomNavigationViewEx btv = (BottomNavigationViewEx)findViewById(R.id.navigation);

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
                item.setChecked(true);
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

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
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
}
