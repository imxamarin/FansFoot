package com.fansfoot.fansfoot.API;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

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
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.fansfoot.fansfoot.MainActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xamarin on 20/12/16.
 */

public  class FacebookStatus {

//
//  public static   CallbackManager callbackManager = CallbackManager.Factory.create();
//    public static   String socialAccessToken;
//    public static  AccessToken accessToken;


    public static boolean CheckFbLogin(){
        AccessToken accessToken;
        accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null){
            Log.d("wala","false");
            return false;
        }else {
            Log.d("wala","true");
            return true;
        }
    }


    public static void LogoutFb(){
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };

        accessTokenTracker.stopTracking();
    }


    public static void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

            }
        }).executeAsync();
    }

    public static String FBUserID(){
        String accessToken;
        accessToken = AccessToken.getCurrentAccessToken().getUserId();
        if (accessToken == null){
            return "User101";

        }else {
            return accessToken;
        }
    }



    public static List<String> GraphReq(){
        final List<String> userdetail = new ArrayList<>();
        if (AccessToken.getCurrentAccessToken().getUserId() != null) {
            GraphRequest request = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.v("LoginActivityAlpha", response.toString());
                            try {
                                String email = object.getString("email");
                                String birthday = object.getString("birthday"); // 01/31/1980 format
                                String name = object.getString("name");
                                String location = object.getJSONObject("location").getString("name");
                                String homelocation = object.getJSONObject("hometown").getString("name");
                                Log.d("holeinGround", email + birthday+location);
                                String[] s = location.split(",");
                                String locationCity = s[0];
                                String locationCountry = s[1];
                                userdetail.add(name);
                                userdetail.add(birthday);
                                userdetail.add(locationCity);
                                userdetail.add(locationCountry);
                                Log.d("Q",userdetail.size()+"");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,birthday,hometown,location,picture");
            request.setParameters(parameters);
            request.executeAsync();
            return userdetail;
        }else {
            return userdetail;
        }



    }


    public static String ProfileFb(){
        if(AccessToken.getCurrentAccessToken().getUserId()!=null) {
            Profile profile = Profile.getCurrentProfile();
         String fname = profile.getFirstName();
         String lname = profile.getName();
            return profile.getName();
        }
        else {
            return "User 101";
        }
    }

 public static void GetFbDetails() {
        if (AccessToken.getCurrentAccessToken().getUserId() != null) {
            GraphRequest request = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.v("LoginActivityAlpha", response.toString());
                            try {
                                String email = object.getString("email");
                                String id = object.getString("id");
                                String birthday = object.getString("birthday"); // 01/31/1980 format
                                String name = object.getString("name");
                                String location = object.getJSONObject("location").getString("name");
                                String homelocation = object.getJSONObject("hometown").getString("name");
                                String picture = object.getJSONObject("picture").getString("url");
                                String[] s = location.split(",");
                                String locationCity = s[0];
                                String locationCountry = s[1];
                                SharedPreferences sharedpreferences = MainActivity.getContext().getSharedPreferences("FacebookPrefrence", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("FbName", name);
                                editor.putString("Fbemail", email);
                                editor.putString("FbID", id);
                                editor.putString("Fbbirthday", birthday);
                                editor.putString("FblocationCity", locationCity);
                                editor.putString("FblocationCountry", locationCountry);
                                editor.putString("Fbpicture", picture);
                                editor.commit();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,birthday,hometown,location,picture");
            request.setParameters(parameters);
            request.executeAsync();

        }

    }


    public static void JumpToAlterFacebookAPI(){
        final SharedPreferences sharedPreferences = MainActivity.getContext().getSharedPreferences("FacebookPrefrence", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(MainActivity.getContext().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
        String ModUrl = ConstServer._baseUrl+
                ConstServer._type+
                ConstServer.post_signUp+
                ConstServer._ConCat+
                ConstServer.Register_type+
                ConstServer.Register_Type_Facebook+
                ConstServer._ConCat+
                ConstServer.Facebook_ID+
                sharedPreferences.getString("FbID","339322503127553")+
                ConstServer._ConCat+
                ConstServer.Facebook_UserName+
                sharedPreferences.getString("FbName","Raj")+
                ConstServer._ConCat+
                ConstServer.Facebook_EmailID+sharedPreferences.getString("Fbemail","amit.verma@trigma.in")+
                ConstServer._ConCat+
                ConstServer.Facebook_profilePic+sharedPreferences.getString("Fbpicture","https://scontent.xx.fbcdn.net/v/t1.0-1/p50x50/15590483_339385113121292_4884085331937605536_n.jpg?oh=7fc509aa2ff6e22729678893bc82c158&oe=58F5283F")+
                ConstServer._ConCat+
                ConstServer._deviceToken+"123"+
                ConstServer._device_type;
        Log.d("newFBI",ModUrl);
        JsonObjectRequest _JsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                ModUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                FacebookFansfoot facebookFansfoot;
                Gson _Gson = new Gson();
                facebookFansfoot =  _Gson.fromJson(response.toString(), FacebookFansfoot.class);
                editor.putString("FbFFID", facebookFansfoot.getUserId());
                editor.putString("FbFFMSG", facebookFansfoot.getMessage());
                editor.putInt("FbFFSTATUS", facebookFansfoot.getStatus());
                editor.commit();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(_JsonObjectRequest);
    }




    }









