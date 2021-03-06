package com.fansfoot.fansfoot.DefaultPages;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.fansfoot.fansfoot.R;

import static com.fansfoot.fansfoot.R.id.view;

/**
 * Created by xamarin on 15/12/16.
 */

public class FbLikePage extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fb_webview_like,container,false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.cm_FbLike);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        CheckBox checkBox = (CheckBox) view.findViewById(R.id.cm_back_fbShare);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getActivity().onBackPressed();
            }
        });

        final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "Please wait, your request is being processed...", true);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.setProgressStyle(R.style.AppCompatAlertDialogStyle);

        final WebView webView =(WebView) view.findViewById(R.id.fbWebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                pd.show();
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                pd.dismiss();

                String webUrl = webView.getUrl();

            }
    });


    webView.loadUrl("http://m.fb.me/Fansfoot");


        return view;
    }
}
