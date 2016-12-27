package com.fansfoot.fansfoot.DefaultActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fansfoot.fansfoot.R;

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
        textView = (TextView) findViewById(R.id.PostTilteID);
        webView = (WebView) findViewById(R.id.commentView);
        imageView = (ImageView) findViewById(R.id.PostMainImage);
        Intent intent = getIntent();
        String TitleImg = intent.getStringExtra("ImageTitle");
        String TitlePic = intent.getStringExtra("ImageURL");
        String ImageFBURL = intent.getStringExtra("ImageFBURL");
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
