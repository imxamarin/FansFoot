package com.fansfoot.fansfoot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by kafir on 09-Dec-16.
 */

public class SplashActivity extends AppCompatActivity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        context = this;
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
