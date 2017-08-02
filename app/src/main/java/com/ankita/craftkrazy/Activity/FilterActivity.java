package com.ankita.craftkrazy.Activity;

import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ankita.craftkrazy.Fragment.ColorFragment;
import com.ankita.craftkrazy.Fragment.DiscountFragment;
import com.ankita.craftkrazy.Fragment.PriceFragment;
import com.ankita.craftkrazy.R;

public class FilterActivity extends AppCompatActivity implements ColorFragment.OnFragmentInteractionListener,DiscountFragment.OnFragmentInteractionListener,PriceFragment.OnFragmentInteractionListener,View.OnClickListener {
    LinearLayout layPrice,layColor,layDiscount;
    TextView tvPrice,tvColor,tvDiscount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        getSupportActionBar().hide();
        layPrice=(LinearLayout)findViewById(R.id.layPrice);
        layColor=(LinearLayout)findViewById(R.id.layColor);
        layDiscount=(LinearLayout)findViewById(R.id.layDiscount);

        tvPrice=(TextView)findViewById(R.id.tvPrice);
        tvColor=(TextView)findViewById(R.id.tvColor);
        tvDiscount=(TextView)findViewById(R.id.tvDiscount);

        Fragment fragment = new PriceFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.list,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        tvPrice.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));

        layPrice.setOnClickListener(this);
        layColor.setOnClickListener(this);
        layDiscount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.layPrice)
        {

            Toast.makeText(getApplicationContext(),"Price",Toast.LENGTH_SHORT).show();
            Fragment fragment = new PriceFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.list,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            tvPrice.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));
            tvColor.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorDefault));
            tvDiscount.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorDefault));
        }
        else if(v.getId() == R.id.layColor)
        {

            Toast.makeText(getApplicationContext(),"Color",Toast.LENGTH_SHORT).show();
            Fragment fragment = new ColorFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.list,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            tvColor.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));
            tvDiscount.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorDefault));
            tvPrice.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorDefault));
        }
        else if(v.getId() == R.id.layDiscount)
        {

            Toast.makeText(getApplicationContext(),"Discount",Toast.LENGTH_SHORT).show();
            Fragment fragment = new DiscountFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.list,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            tvDiscount.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));
            tvColor.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorDefault));
            tvPrice.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorDefault));
        }
    }
    @Override
    public void onFragmentInteraction(Uri uri)  {

    }
}
