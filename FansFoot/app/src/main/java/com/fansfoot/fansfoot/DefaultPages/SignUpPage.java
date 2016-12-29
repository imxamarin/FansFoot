package com.fansfoot.fansfoot.DefaultPages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.fansfoot.fansfoot.API.ConstServer;
import com.fansfoot.fansfoot.API.FacebookFansfoot;
import com.fansfoot.fansfoot.API.FansFootLogin;
import com.fansfoot.fansfoot.API.FansFootSignUp;
import com.fansfoot.fansfoot.DefaultActivities.YoutubePlayerActivity;
import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xamarin on 16/12/16.
 */

public class SignUpPage extends Fragment {

    Button SignUpRegBtn;
    EditText  NameEdTxt,EmailEdTxt,PasswdEdTxt,ConnPasswdTxt;
    LoginButton  FbBtn;

    CallbackManager callbackManager;
    SharedPreferences sharedPreferencesBeta;
    SharedPreferences.Editor editorBeta;
    FacebookFansfoot facebookFansfoot;
    RequestQueue mRequestQueue;
    FansFootSignUp fansFootSignUp;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.sign_up_page,container,false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        sharedPreferencesBeta =getActivity().getSharedPreferences("FansFootPerfrence", Context.MODE_PRIVATE);
        Cache cache = new DiskBasedCache(MainActivity.getContext().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.SignUpToolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        CheckBox checkBox = (CheckBox) view.findViewById(R.id.cm_back_signUp);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getActivity().onBackPressed();
            }
        });

        AdView mAdView = (AdView) view.findViewById(R.id.adViewSignUp);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        FbBtn = (LoginButton) view.findViewById(R.id.SignUpFbBtn);
        NameEdTxt = (EditText) view.findViewById(R.id.SignUpNameEditText);
        EmailEdTxt = (EditText) view.findViewById(R.id.SignUpEmailEditText);
        PasswdEdTxt = (EditText) view.findViewById(R.id.SignUpPasswordEditText);
        ConnPasswdTxt = (EditText) view.findViewById(R.id.SignUpConnPasswdEditText);
        SignUpRegBtn = (Button) view.findViewById(R.id.SignUpRegBtn);
        SignUpRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                SignUpRegBtn.setEnabled(false);

                if(CheckFields(NameEdTxt)&& CheckFields(EmailEdTxt) && CheckFields(PasswdEdTxt) && CheckFields(ConnPasswdTxt)){
                    if(CheckEmail(EmailEdTxt) ) {
                        if(ConnPassword(PasswdEdTxt,ConnPasswdTxt)) {
                            if (CheckPassword(PasswdEdTxt,ConnPasswdTxt)){
                                String ModUrl = ConstServer._MainbaseUrl+
                                        ConstServer._type+
                                        ConstServer.post_signUp+
                                        ConstServer._ConCat+
                                        ConstServer.Register_type+
                                        ConstServer.Sign_up_RegType+
                                        ConstServer._ConCat+
                                        ConstServer.Facebook_UserName+
                                        EmailEdTxt.getText().toString().trim()+
                                        ConstServer._ConCat+
                                        ConstServer.UserEmail+
                                        EmailEdTxt.getText().toString().trim()+
                                        ConstServer._ConCat+
                                        ConstServer.UserPassword+
                                        PasswdEdTxt.getText().toString().trim()+
                                        ConstServer._ConCat+
                                        ConstServer._deviceToken+sharedPreferencesBeta.getString("UUID","C10105484848")+
                                        ConstServer._ConCat+
                                        ConstServer._device_type;


                                JsonObjectRequest _JsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                                        ModUrl, null, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        Gson _Gson = new Gson();
                                        fansFootSignUp = _Gson.fromJson(response.toString(),FansFootSignUp.class);

                                        SignUpRegBtn.setEnabled(true);
                                        if(fansFootSignUp.getStatus()==1){
                                            editorBeta = sharedPreferencesBeta.edit();
                                            editorBeta.putString("FbFFID", fansFootSignUp.getUserId().toString());
                                            editorBeta.putString("FbFFMSG", fansFootSignUp.getMessage());
                                            editorBeta.putString("FbFFName",NameEdTxt.getText().toString().trim());
                                            editorBeta.putString("iName",NameEdTxt.getText().toString().trim());
                                            editorBeta.putString("iEmail",NameEdTxt.getText().toString().trim());
                                            editorBeta.commit();

                                            Snackbar.make(view,"Login Successful",Snackbar.LENGTH_SHORT).show();
                                            FragmentTransaction fragmentTransaction;
                                            FragmentManager manager = MainActivity.getBaseFragmentManager();
                                            fragmentTransaction = manager.beginTransaction();
                                            ProfilePage profilePage = new ProfilePage();
                                            fragmentTransaction.replace(R.id.frag, profilePage);
                                            fragmentTransaction.commit();
                                        }else{
                                            Snackbar.make(view,"User with this mail id already exists",Snackbar.LENGTH_SHORT).show();
                                            EmailEdTxt.setTextColor(Color.RED);

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
                                Snackbar.make(view,"Password cannot be less than 8 characters",Snackbar.LENGTH_SHORT).show();
                            }
                        }else {
                            Snackbar.make(view,"Password don't match",Snackbar.LENGTH_SHORT).show();
                        }
                    }else {
                        Snackbar.make(view,"Enter a valid Email ID",Snackbar.LENGTH_SHORT).show();
                    }
                    }else {
                    Snackbar.make(view,"Fields Cannot Be Empty",Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        FbBtn.setReadPermissions(Arrays.asList("user_location", "email", "user_birthday","user_hometown","user_about_me"));
        FbBtn.setFragment(this);
        FbBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if(loginResult.getAccessToken().getUserId()!= null){
                    GraphRequest request = GraphRequest.newMeRequest(
                            AccessToken.getCurrentAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {

                                    try {
                                        editorBeta = sharedPreferencesBeta.edit();




                                        if(object.has("name")){
                                            if(!object.isNull("name")) {
                                                if(object.getString("name") != null){
                                                    editorBeta.putString("iName",object.getString("name"));
                                                }
                                            }}


                                        if(object.has("email")){
                                            if(!object.isNull("email")) {
                                                if(object.getString("email") != null){
                                                    editorBeta.putString("iEmail",object.getString("email"));
                                                }
                                            }}



                                        if(object.has("id")){
                                            if(!object.isNull("id")) {
                                                if(object.getString("id") != null){
                                                    editorBeta.putString("iUID",object.getString("id"));
                                                }
                                            }
                                        }


                                        if(object.has("picture")){
                                            if(!object.isNull("picture")){
                                                if(object.getJSONObject("picture").getJSONObject("data").getString("url") != null){
                                                    editorBeta.putString("iFbpicture", object.getJSONObject("picture").getJSONObject("data").getString("url"));
                                                }
                                            }}



                                        if(object.has("location")){

                                            if(!object.isNull("location")){
                                                if(object.getJSONObject("location").getString("name") != null){
                                                    String location  = object.getJSONObject("location").getString("name");
                                                    String[] s = location.split(",");
                                                    String locationCity = s[0];
                                                    String locationCountry = s[1];
                                                    editorBeta.putString("iCity", locationCity);
                                                    editorBeta.putString("iCountry", locationCountry);
                                                }
                                            }
                                        }

                                        if(object.has("birthday")){

                                            if(!object.isNull("birthday")){
                                                if(object.getString("birthday") != null){
                                                    editorBeta.putString("iBirthday",object.getString("birthday"));
                                                }

                                            }
                                        }





                                        editorBeta.commit();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    DoTheCallToFB();
                                    DoThisOperation();
                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,name,email,gender,birthday,hometown,location,picture");
                    request.setParameters(parameters);
                    request.executeAsync();
                }

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

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

        if(!matcher.matches()){
            editText.setTextColor(Color.RED);
        }else {
            editText.setTextColor(Color.BLACK);
        }
        return matcher.matches();

    }


    public boolean CheckPassword(EditText edittext,EditText editTexter){
        String ed_text = edittext.getText().toString().trim();
        if(ed_text.length()>7 && ed_text.length()<15){
            edittext.setTextColor(Color.BLACK);
            editTexter.setTextColor(Color.BLACK);
            return true;
        }else {
            edittext.setTextColor(Color.RED);
            editTexter.setTextColor(Color.RED);
            return false;
        }

    }


    public boolean ConnPassword(EditText alpha,EditText beta){
        String ed_Alpha = alpha.getText().toString().trim();
        String ed_Beta = beta.getText().toString().trim();
        if(ed_Alpha.equals(ed_Beta)){
            alpha.setTextColor(Color.BLACK);
            beta.setTextColor(Color.BLACK);
            return true;
        }else {
            alpha.setTextColor(Color.RED);
            beta.setTextColor(Color.RED);
            return false;
        }
    }

    private void DoTheCallToFB() {
        String ModUrl = ConstServer._MainbaseUrl+
                ConstServer._type+
                ConstServer.post_signUp+
                ConstServer._ConCat+
                ConstServer.Register_type+
                ConstServer.Register_Type_Facebook+
                ConstServer._ConCat+
                ConstServer.Facebook_ID+
                sharedPreferencesBeta.getString("iUID","339322503127553")+
                ConstServer._ConCat+
                ConstServer.Facebook_UserName+
                sharedPreferencesBeta.getString("iName","Raj")+
                ConstServer._ConCat+
                ConstServer.Facebook_EmailID+sharedPreferencesBeta.getString("iEmail","amit.verma@trigma.in")+
                ConstServer._ConCat+
                ConstServer.Facebook_profilePic+sharedPreferencesBeta.getString("iFbpicture","https://scontent.xx.fbcdn.net/v/t1.0-1/p50x50/15590483_339385113121292_4884085331937605536_n.jpg?oh=7fc509aa2ff6e22729678893bc82c158&oe=58F5283F")+
                ConstServer._ConCat+
                ConstServer._deviceToken+sharedPreferencesBeta.getString("UUID","C10105484848")+
                ConstServer._ConCat+
                ConstServer._device_type;


        JsonObjectRequest _JsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                ModUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson _Gson = new Gson();
                facebookFansfoot =  _Gson.fromJson(response.toString(), FacebookFansfoot.class);

                editorBeta = sharedPreferencesBeta.edit();
                editorBeta.putString("FbFFID", facebookFansfoot.getUserId());
                editorBeta.putString("FbFFMSG", facebookFansfoot.getMessage());
                editorBeta.putInt("FbFFSTATUS", facebookFansfoot.getStatus());
                editorBeta.putString("FbFFName",sharedPreferencesBeta.getString("iName","Raj"));
                editorBeta.commit();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(_JsonObjectRequest);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void DoThisOperation(){
        FragmentTransaction fragmentTransaction;
        FragmentManager manager = MainActivity.getBaseFragmentManager();
        manager.popBackStackImmediate();
        fragmentTransaction = manager.beginTransaction();
        ProfilePage profilePage = new ProfilePage();
        manager.popBackStackImmediate();
        fragmentTransaction.replace(R.id.frag,profilePage);
        fragmentTransaction.commit();
    }


}
