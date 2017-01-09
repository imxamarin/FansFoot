package com.fansfoot.fansfoot.DefaultActivities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;

public class SplashScreen extends AppCompatActivity {
    private static int IMAGE_TIME_OUT = 2000;

    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final ImageView img = (ImageView) findViewById(R.id.SplashScreenImage);



// Replacing the First Splash image

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                img.setBackgroundResource(R.drawable.splash2);
            }
        }, IMAGE_TIME_OUT);

// Starting the Main Activity

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
    }

