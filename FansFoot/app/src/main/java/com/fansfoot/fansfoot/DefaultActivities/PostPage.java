package com.fansfoot.fansfoot.DefaultActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fansfoot.fansfoot.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;

/**
 * Created by xamarin on 21/12/16.
 */

public class PostPage extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    WebView webView;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.posttoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        CheckBox back = (CheckBox)findViewById(R.id.cm_PostToolBar_search);
        back.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                onBackPressed();
            }
        });

        AdView mAdView = (AdView) findViewById(R.id.adViewPost);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        textView = (TextView) findViewById(R.id.PostTilteID);
        webView = (WebView) findViewById(R.id.commentView);
        imageView = (ImageView) findViewById(R.id.PostMainImage);
        Intent intent = getIntent();
        String TitleImg = intent.getStringExtra("ImageTitle");
        String TitlePic = intent.getStringExtra("ImageURL");
        String ImageFBURL = intent.getStringExtra("ImageFBURL");
        int height = intent.getIntExtra("height",600);
        int width = intent.getIntExtra("width",500);
        Log.d("Checkthat", " "+height+width);
        progressDialog = ProgressDialog.show(PostPage.this, "", "Please wait, your request is being processed...", true);
        if(!ImageFBURL.isEmpty()){
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);

            webView.setWebViewClient(new WebViewClient() {


                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon)
                {
                    progressDialog.show();
                }


                @Override
                public void onPageFinished(WebView view, String url) {
                    progressDialog.dismiss();

                    String webUrl = webView.getUrl();

                }
            });
            webView.loadUrl(ImageFBURL);
        }
        if(textView != null){
            textView.setText(TitleImg);
            Picasso.with(getApplicationContext())
                    .load(TitlePic)
                    .resize(width,height)
                    .centerCrop()
                    .placeholder(R.drawable.post_img)
                    .into(imageView);
        }




    }
}
