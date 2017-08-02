package com.ankita.craftkrazy.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ankita.craftkrazy.Activity.HomeActivity;
import com.ankita.craftkrazy.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
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
}
