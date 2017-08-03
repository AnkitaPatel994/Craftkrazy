package com.ankita.craftkrazy.Activity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.ankita.craftkrazy.Activity.HomeActivity;
import com.ankita.craftkrazy.R;

public class MainActivity extends AppCompatActivity {
    LinearLayout layout;
    public static String Main_URL= "http://10.0.3.2/CraftKrazyWebService/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        layout = (LinearLayout)findViewById(R.id.layout);

        ConnectivityManager cm = (ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni!=null && ni.isConnected())
        {
            Thread background = new Thread()
            {
                public void run()
                {
                    try {
                        sleep(5*1000);
                        Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(i);
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            background.start();
        }
        else
        {
            Snackbar.make(layout,"Network Unavailable",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }
    }
}
