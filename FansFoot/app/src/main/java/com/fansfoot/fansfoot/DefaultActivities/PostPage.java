package com.fansfoot.fansfoot.DefaultActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fansfoot.fansfoot.R;

/**
 * Created by xamarin on 21/12/16.
 */

public class PostPage extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_page);
        textView = (TextView) findViewById(R.id.PostTilteID);
        imageView = (ImageView) findViewById(R.id.PostMainImage);
        Intent intent = getIntent();
        String TitleImg = intent.getStringExtra("ImageTitle");
        String TitlePic = intent.getStringExtra("ImageURL");
        if(textView != null){
            textView.setText(TitleImg);
            Glide
                    .with(getApplicationContext())
                    .load(TitlePic)
                    .fitCenter()
                    .placeholder(R.drawable.post_img)
                    .crossFade()
                    .into(imageView);
        }

    }
}
