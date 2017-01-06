package com.fansfoot.fansfoot.DefaultPages;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.facebook.login.widget.ProfilePictureView;
import com.fansfoot.fansfoot.API.ConstServer;
import com.fansfoot.fansfoot.API.FacebookStatus;
import com.fansfoot.fansfoot.API.FansFootLogin;
import com.fansfoot.fansfoot.API.Profiler;
import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by xamarin on 05/12/16.
 */

public class ProfilePage extends Fragment {

    Context context;
    ProfilePictureView profilePictureView;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;

    EditText ProfileNameEdtext;
    EditText ProfileCityEdtext;
    EditText ProfileCountryEdtext;
    EditText ProfileBirthDayEdtext;
    SharedPreferences sharedPreferencesBeta;
    SharedPreferences.Editor editorBeta;
    TextView points;
    TextView likes;
    TextView posts;
    RequestQueue mRequestQueue;
    Profiler profiler;
    ProgressDialog progress;
    String name="",country="",city="",birthday = "";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.profile_fragment,null,false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        context = getActivity();
        progress = new ProgressDialog(getActivity());
        progress.setMessage("Updating the Profile");
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(false);
        sharedPreferencesBeta =getActivity().getSharedPreferences("FansFootPerfrence", Context.MODE_PRIVATE);
        Cache cache = new DiskBasedCache(MainActivity.getContext().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.Profiletoolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        final CheckBox editbtn = (CheckBox) view.findViewById(R.id.cm_ProfileToolBar_Edit);
        final CheckBox logoutbtn = (CheckBox) view.findViewById(R.id.cm_ProfileToolBar_Logout);
         ProfileNameEdtext = (EditText) view.findViewById(R.id.ProfileNameEditView);
         ProfileCityEdtext = (EditText) view.findViewById(R.id.ProfileCityEditView);
        ProfileCountryEdtext = (EditText) view.findViewById(R.id.ProfileCountryEditView);
        ProfileBirthDayEdtext = (EditText) view.findViewById(R.id.ProfileBirthdayEditView);
        profilePictureView = (ProfilePictureView) view.findViewById(R.id.imageView);
        points = (TextView) view.findViewById(R.id.Pro_points);
        likes = (TextView) view.findViewById(R.id.Pro_comments);
        posts = (TextView) view.findViewById(R.id.Pro_Post);

        if(FacebookStatus.CheckFbLogin()){
            profilePictureView.setProfileId(FacebookStatus.FBUserID());
             name = sharedPreferencesBeta.getString("iName","");
             city = sharedPreferencesBeta.getString("iCity","");
             country= sharedPreferencesBeta.getString("iCountry","");
             birthday = sharedPreferencesBeta.getString("iBirthday","");
            ProfileNameEdtext.setText(name);
            ProfileCityEdtext.setText(city);
            ProfileCountryEdtext.setText(country);
            ProfileBirthDayEdtext.setText(birthday);


        }else {
            String name = sharedPreferencesBeta.getString("FbFFName","");
            String locationCity = sharedPreferencesBeta.getString("eCity","");
            String locationCountry = sharedPreferencesBeta.getString("eCountry","");
            String birthday = sharedPreferencesBeta.getString("eBirthday","");
            ProfileNameEdtext.setText(name);
            ProfileCityEdtext.setText(locationCity);
            ProfileCountryEdtext.setText(locationCountry);
            ProfileBirthDayEdtext.setText(birthday);
        }

        CallThisMethodToGetUpdatesFromServer();



        logoutbtn.setEnabled(true);
        logoutbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                logoutbtn.setEnabled(false);
                if (FacebookStatus.CheckFbLogin()){
                    FacebookStatus.disconnectFromFacebook();
                    editorBeta = sharedPreferencesBeta.edit();
                    editorBeta.clear();
                    editorBeta.commit();
                    Toast.makeText(context, "USER LOGOUT", Toast.LENGTH_SHORT).show();
                    DoThisOperation();
                }else{
                    editorBeta = sharedPreferencesBeta.edit();
                    editorBeta.clear();
                    editorBeta.commit();
                    Toast.makeText(context, "USER LOGOUT", Toast.LENGTH_SHORT).show();
                    DoThisOperation();
                }
            }
        });






        editbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                progress.show();

                String ModUrl = ConstServer._MainbaseUrl+
                        ConstServer._type+
                        ConstServer.my_profile+
                        ConstServer._ConCat+
                        ConstServer.my_profile_USERID+sharedPreferencesBeta.getString("FbFFID","5294")+
                        ConstServer.username+ProfileNameEdtext.getText();


                JsonObjectRequest _JsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                        ModUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson _Gson = new Gson();
                        profiler = _Gson.fromJson(response.toString(),Profiler.class);
                        if(profiler.getStatus()==1){
                            profiler.setName(sharedPreferencesBeta.getString("FbFFName",ProfileNameEdtext.getText().toString()));
                            editorBeta = sharedPreferencesBeta.edit();
                            editorBeta.putString("FbFFName",ProfileNameEdtext.getText().toString());
                            editorBeta.putString("eCity",ProfileCityEdtext.getText().toString());
                            editorBeta.putString("eCountry",ProfileCountryEdtext.getText().toString());
                            editorBeta.putString("eBirthday",ProfileBirthDayEdtext.getText().toString());
                            editorBeta.commit();
                        }
                        progress.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                    }
                });
                mRequestQueue.add(_JsonObjectRequest);
            }
        });

        AdView mAdView = (AdView) view.findViewById(R.id.adViewProfile);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        return  view;
    }



    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.setShowSoftInputOnFocus(false);
        }
      //  editText.setBackgroundColor(Color.TRANSPARENT);
    }


    private void enableEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
        editText.clearFocus();
        editText.requestFocus();
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setTextIsSelectable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.setShowSoftInputOnFocus(true);
        }
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        //  editText.setBackgroundColor(Color.TRANSPARENT);
    }


    public void DoThisOperation(){
        FragmentTransaction fragmentTransaction;
        FragmentManager manager = MainActivity.getBaseFragmentManager();
        manager.popBackStackImmediate();
        fragmentTransaction = manager.beginTransaction();
        LoginPage profilePage = new LoginPage();
        manager.popBackStackImmediate();
        fragmentTransaction.replace(R.id.frag,profilePage);
        fragmentTransaction.commit();
    }
    public void CallThisMethodToGetUpdatesFromServer(){


        String ModUrl = ConstServer._MainbaseUrl+
                ConstServer._type+
                ConstServer.my_profile+
                ConstServer._ConCat+
                ConstServer.my_profile_USERID+sharedPreferencesBeta.getString("FbFFID","5294");

        JsonObjectRequest _JsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                ModUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson _Gson = new Gson();
                profiler = _Gson.fromJson(response.toString(),Profiler.class);
                if(profiler.getStatus()==1){
                    points.setText(profiler.getLike().toString());
                    likes.setText(profiler.getComments().toString());
                    posts.setText(profiler.getPost().toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.add(_JsonObjectRequest);


    }


    public void CallThisMethodToGetUpdatesFromServerBeta(){


        String ModUrl = ConstServer._MainbaseUrl+
                ConstServer._type+
                ConstServer.my_profile+
                ConstServer._ConCat+
                ConstServer.my_profile_USERID+sharedPreferencesBeta.getString("FbFFID","5294");

        JsonObjectRequest _JsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                ModUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson _Gson = new Gson();
                profiler = _Gson.fromJson(response.toString(),Profiler.class);
                if(profiler.getStatus()==1){
                points.setText(profiler.getLike().toString());
                likes.setText(profiler.getComments().toString());
                posts.setText(profiler.getPost().toString());
                ProfileNameEdtext.setText(profiler.getName().toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.add(_JsonObjectRequest);


    }


}