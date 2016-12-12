package com.fansfoot.fansfoot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

/**
 * Created by kafir on 09-Dec-16.
 */

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    Context context;
    ImageView _splashImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        _splashImage = (ImageView) findViewById(R.id.splashImg);
        _splashImage.setImageResource(R.drawable.splash_01);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                _splashImage.setImageResource(R.drawable.splash_01_copy);
                /* Create an Intent that will start the Menu-Activity. */


            }
        }, SPLASH_TIME_OUT);

    }

}
