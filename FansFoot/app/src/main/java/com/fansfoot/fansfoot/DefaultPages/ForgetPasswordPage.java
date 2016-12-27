package com.fansfoot.fansfoot.DefaultPages;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.facebook.CallbackManager;
import com.fansfoot.fansfoot.API.ConstServer;
import com.fansfoot.fansfoot.API.FansFootLogin;
import com.fansfoot.fansfoot.API.ForgetPasswd;
import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xamarin on 16/12/16.
 */

public class ForgetPasswordPage extends Fragment {


    Button ForgetpaswdEmailBtn;
    EditText EmailEdTxt;
    ForgetPasswd forgetPasswd;
    RequestQueue mRequestQueue;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.forget_password,container,false);
        Cache cache = new DiskBasedCache(MainActivity.getContext().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.forgetPasswordToolbar);
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        final CheckBox chkbox = (CheckBox) view.findViewById(R.id.cm_forget_back);
        EmailEdTxt = (EditText) view.findViewById(R.id.ForgetpassEmailEdtxt);
        ForgetpaswdEmailBtn = (Button) view.findViewById(R.id.ForgetpassBtn);
        AdView mAdView = (AdView) view.findViewById(R.id.adViewForgetPasswd);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        chkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getActivity().onBackPressed();
            }
        });

        ForgetpaswdEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ForgetpaswdEmailBtn.setEnabled(false);
                if(CheckFields(EmailEdTxt)){
                    if(CheckEmail(EmailEdTxt)) {

                        String ModUrl = ConstServer._MainbaseUrl+
                                ConstServer._type+
                                ConstServer.Forget_Password+
                                ConstServer._ConCat+
                                ConstServer.UserEmail+
                                EmailEdTxt.getText().toString().trim();

                        JsonObjectRequest _JsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                                ModUrl, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("CheckJson",response.toString());
                                Gson _Gson = new Gson();
                                forgetPasswd = _Gson.fromJson(response.toString(),ForgetPasswd.class);
                                if(forgetPasswd.getStatus()==1){
                                    Snackbar.make(view, "Password Sent", Snackbar.LENGTH_SHORT).show();
                                    FragmentTransaction fragmentTransaction;
                                    FragmentManager manager = MainActivity.getBaseFragmentManager();
                                    fragmentTransaction = manager.beginTransaction();
                                    LoginPage Homepage = new LoginPage();
                                    fragmentTransaction.replace(R.id.frag, Homepage);
                                    fragmentTransaction.commit();

                                }else{
                                    Snackbar.make(view,"This email is not registered with us",Snackbar.LENGTH_SHORT).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Snackbar.make(view,"Something Went Wrong Try Again",Snackbar.LENGTH_SHORT).show();
                            }
                        });
                        mRequestQueue.add(_JsonObjectRequest);

                    }else {
                        Snackbar.make(view,"Enter a valid Email ID",Snackbar.LENGTH_SHORT).show();
                    }
                }else {
                    Snackbar.make(view,"Fields Cannot Be Empty",Snackbar.LENGTH_SHORT).show();
                }
                ForgetpaswdEmailBtn.setEnabled(true);

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
    public boolean CheckEmail(EditText editText){
        String ed_text = editText.getText().toString().trim();
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ed_text);
        Log.d("Validation",matcher.matches()+"");
        if(!matcher.matches()){
            editText.setTextColor(Color.RED);
        }else {
            editText.setTextColor(Color.BLACK);
        }
        return matcher.matches();

    }
}
