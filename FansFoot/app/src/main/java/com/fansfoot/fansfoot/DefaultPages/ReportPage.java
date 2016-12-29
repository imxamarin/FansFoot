package com.fansfoot.fansfoot.DefaultPages;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fansfoot.fansfoot.API.ConstServer;
import com.fansfoot.fansfoot.API.FBLike;
import com.fansfoot.fansfoot.API.ReportProblem;
import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by xamarin on 15/12/16.
 */

public class ReportPage extends Fragment {

    Context context;
    RequestQueue mRequestQueue;
    SharedPreferences sharedPreferencesBeta;
    ReportProblem reportProblem;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.report_webview,container,false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        context = this.getContext();
        sharedPreferencesBeta  = context.getSharedPreferences("FansFootPerfrence", Context.MODE_PRIVATE);
        Cache cache = new DiskBasedCache(MainActivity.getContext().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.cm_report);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        AdView mAdView = (AdView) view.findViewById(R.id.adViewReport);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        final Button submitButton = (Button) view.findViewById(R.id.submitBtn);
        final EditText editTextAlpha = (EditText) view.findViewById(R.id.ProblemEdit);
        final EditText editTextBeta = (EditText) view.findViewById(R.id.ProblemExplain);
        final EditText editTextDelta = (EditText) view.findViewById(R.id.ProblemSuggestion);


        CheckBox checkBox = (CheckBox) view.findViewById(R.id.cm_back_report);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                submitButton.setEnabled(false);
                if (CheckFields(editTextAlpha) && CheckFields(editTextBeta) && CheckFields(editTextDelta)) {

                String ModUrl = ConstServer._baseUrl +
                        ConstServer._type +
                        ConstServer.report_problemType +
                        ConstServer._ConCat +
                        ConstServer._PostUserID +
                        sharedPreferencesBeta.getString("FbFFID", "") +
                        ConstServer._ConCat +
                        ConstServer.categories +
                        ConstServer._ConCat +
                        ConstServer.problem +
                        editTextAlpha.getText().toString().trim() +
                        ConstServer._ConCat +
                        ConstServer.berifly_explain +
                        editTextBeta.getText().toString().trim() +
                        ConstServer._ConCat +
                        ConstServer.suggestion +
                        editTextDelta.getText().toString().trim();


                Log.d("URL", ModUrl);


                JsonObjectRequest _JsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                        ModUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Gson _Gson = new Gson();
                        reportProblem = _Gson.fromJson(response.toString(), ReportProblem.class);

                        if (reportProblem.getStatus() == 1) {
                            Snackbar
                                    .make(view, "Report submitted", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar
                                    .make(view, "Something Went Wrong", Snackbar.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Snackbar
                                .make(view, "Something Went Wrong", Snackbar.LENGTH_SHORT).show();



                    }
                });

                mRequestQueue.add(_JsonObjectRequest);
            }else {
                    Snackbar
                            .make(view, "Fields cannot be empty", Snackbar.LENGTH_SHORT).show();
                }
                submitButton.setEnabled(true);

            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getActivity().onBackPressed();
            }
        });




        return view;
    }

    public boolean CheckFields(EditText ed){
        String ed_text = ed.getText().toString().trim();

        if(ed_text.isEmpty() || ed_text.length() == 0 || ed_text.equals("") || ed_text == null)
        {
            return false;
        }else {
            return true;
        }

    }
}
