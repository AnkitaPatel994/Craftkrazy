package com.ankita.craftkrazy.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ankita.craftkrazy.Library.BottomNavigationViewEx;
import com.ankita.craftkrazy.R;

public class AccountActivity extends AppCompatActivity {
    TextView txtAccountInfo,txtOrdHistory,txtWishlist,txtBankDetail,txtChangepwd,txtSignout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //========================= BottomNavigationView start =================================
        BottomNavigationViewEx btv = (BottomNavigationViewEx)findViewById(R.id.navigation);

        btv.enableAnimation(false);
        btv.enableShiftingMode(false);
        btv.enableItemShiftingMode(false);

        Menu menu = btv.getMenu();
        MenuItem menuItem = menu.getItem(4);
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
                else if (id == R.id.nav_wishlist)
                {
                    Intent i = new Intent(getApplicationContext(),WishListActivity.class);
                    startActivity(i);
                }

                return false;
            }
        });
        //========================= BottomNavigationView stop =================================
        //========================= My Account Start =================================
        txtAccountInfo=(TextView)findViewById(R.id.txtAccountInfo);
        txtOrdHistory=(TextView)findViewById(R.id.txtOrdHistory);
        txtWishlist=(TextView)findViewById(R.id.txtWishlist);
        txtBankDetail=(TextView)findViewById(R.id.txtBankDetail);
        txtChangepwd=(TextView)findViewById(R.id.txtChangepwd);
        txtSignout=(TextView)findViewById(R.id.txtSignout);
        txtAccountInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ShippingActivity.class);
                startActivity(i);
               // Toast.makeText(getApplicationContext(),"Account & Information",Toast.LENGTH_SHORT).show();
            }
        });
        txtOrdHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Order History",Toast.LENGTH_SHORT).show();
            }
        });
        txtBankDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Bank Detail",Toast.LENGTH_SHORT).show();
            }
        });
        txtChangepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ChangePwdActivity.class);
                startActivity(i);
                //Toast.makeText(getApplicationContext(),"Change Password",Toast.LENGTH_SHORT).show();
            }
        });
        txtWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"WishList",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),WishListActivity.class);
                startActivity(i);
            }
        });
        txtSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Sign Out",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(i);
            }
        });
        //========================= My Account Stop =================================
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
}
