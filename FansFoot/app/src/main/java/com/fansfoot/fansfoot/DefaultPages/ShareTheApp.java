package com.fansfoot.fansfoot.DefaultPages;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fansfoot.fansfoot.R;

/**
 * Created by xamarin on 15/12/16.
 */

public class ShareTheApp extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.share_webview_page,container,false);
        final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "Please wait, your request is being processed...", true);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        final WebView webView =(WebView) view.findViewById(R.id.ShareWebView);
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

        webView.loadUrl("http://m.fb.me");


        return view;
    }
}
