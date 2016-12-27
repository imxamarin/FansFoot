package com.fansfoot.fansfoot.DefaultActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fansfoot.fansfoot.R;

/**
 * Created by xamarin on 21/12/16.
 */

public class CommentPage extends AppCompatActivity {
    ProgressDialog progressDialog;
    WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_page);
        webView = (WebView) findViewById(R.id.commentID);
        progressDialog = ProgressDialog.show(CommentPage.this, "", "Please wait, your request is being processed...", true);
        Intent intent = getIntent();
        String ImageFBURL = intent.getStringExtra("ImageFBURL");
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
    }
}
