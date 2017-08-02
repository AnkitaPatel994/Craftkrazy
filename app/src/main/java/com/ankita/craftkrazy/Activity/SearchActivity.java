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

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
