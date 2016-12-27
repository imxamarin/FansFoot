package com.fansfoot.fansfoot.DefaultPages;

import android.app.Activity;
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
    public interface Callback {
        public void onRadioButtonClicked(View radioButton);
    }

    Context context;
    ProfilePictureView profilePictureView;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;
    String[] userDetail = {
            "Name",
            "City",
            "Country",
            "Birthday"
    };

    String[] userValues = {
            "Rohit",
            "Chandigarh",
            "India",
            "30 Feb"
    };
    EditText ProfileNameEdtext;
    EditText ProfileCityEdtext;
    EditText ProfileCountryEdtext;
    EditText ProfileBirthDayEdtext;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferencesBeta;
    SharedPreferences.Editor editorBeta;
    TextView points;
    TextView likes;
    TextView posts;
    RequestQueue mRequestQueue;
    Profiler profiler;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.profile_fragment,null,false);
        context = getActivity();
        sharedPreferences = getActivity().getSharedPreferences("FacebookPrefrence",Context.MODE_PRIVATE);
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
        final Button logoutbtn = (Button) view.findViewById(R.id.cm_ProfileToolBar_Logout);
         ProfileNameEdtext = (EditText) view.findViewById(R.id.ProfileNameEditView);
         ProfileCityEdtext = (EditText) view.findViewById(R.id.ProfileCityEditView);
        ProfileCountryEdtext = (EditText) view.findViewById(R.id.ProfileCountryEditView);
        ProfileBirthDayEdtext = (EditText) view.findViewById(R.id.ProfileBirthdayEditView);
        profilePictureView = (ProfilePictureView) view.findViewById(R.id.imageView);
        points = (TextView) view.findViewById(R.id.Pro_points);
        likes = (TextView) view.findViewById(R.id.Pro_comments);
        posts = (TextView) view.findViewById(R.id.Pro_Post);
        CallThisMethodToGetUpdatesFromServer();
        if(FacebookStatus.CheckFbLogin()){
            profilePictureView.setProfileId(FacebookStatus.FBUserID());
        }

        if(FacebookStatus.CheckFbLogin()){
            String name = sharedPreferences.getString("FbName","RoHIT");
            String locationCity = sharedPreferences.getString("FblocationCity","Okohama");
            String locationCountry = sharedPreferences.getString("FblocationCountry","Japan");
            String birthday = sharedPreferences.getString("Fbbirthday","India");
            ProfileNameEdtext.setText(name);
            ProfileCityEdtext.setText(locationCity);
            ProfileCountryEdtext.setText(locationCountry);
            ProfileBirthDayEdtext.setText(birthday);
        }else {
            String name = sharedPreferencesBeta.getString("FbFFName","Jane Doe");
            String locationCity = "";
            String locationCountry = "";
            String birthday = "";
            ProfileNameEdtext.setText(name);
            ProfileCityEdtext.setText(locationCity);
            ProfileCountryEdtext.setText(locationCountry);
            ProfileBirthDayEdtext.setText(birthday);
        }


        disableEditText(ProfileNameEdtext);
        disableEditText(ProfileCityEdtext);
        disableEditText(ProfileCountryEdtext);
        disableEditText(ProfileBirthDayEdtext);
        logoutbtn.setEnabled(true);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutbtn.setEnabled(false);
                if (FacebookStatus.CheckFbLogin()){
                    FacebookStatus.disconnectFromFacebook();
                    Snackbar.make(view,"USER LOGOUT",Snackbar.LENGTH_SHORT).show();
                    DoThisOperation();
                }else{
                    editorBeta = sharedPreferencesBeta.edit();
                    editorBeta.putString("FbFFID", "");
                    editorBeta.commit();
                    Snackbar.make(view,"USER LOGOUT",Snackbar.LENGTH_SHORT).show();
                    DoThisOperation();
                }

            }
        });





        editbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    enableEditText(ProfileNameEdtext);
                    enableEditText(ProfileCityEdtext);
                    enableEditText(ProfileCountryEdtext);
                    enableEditText(ProfileBirthDayEdtext);
                    editbtn.setText("Save");


                }else {
                    disableEditText(ProfileNameEdtext);
                    disableEditText(ProfileCityEdtext);
                    disableEditText(ProfileCountryEdtext);
                    disableEditText(ProfileBirthDayEdtext);
                    editbtn.setText("Edit");
                    MainActivity.hideKeyboard(context);
                }
            }
        });

        AdView mAdView = (AdView) view.findViewById(R.id.adViewProfile);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

//        recyclerView = (RecyclerView) view.findViewById(R.id.ProfileRecycleView);
//        recylerViewLayoutManager = new LinearLayoutManager(context);
//        recyclerView.setLayoutManager(recylerViewLayoutManager);
//        recyclerViewAdapter = new ProfileRecycleViewAdapter(userDetail,userValues,context);
//        recyclerView.setAdapter(recyclerViewAdapter);
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
        Log.d("Pro",ModUrl);

        JsonObjectRequest _JsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                ModUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Profile",response.toString());
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


}