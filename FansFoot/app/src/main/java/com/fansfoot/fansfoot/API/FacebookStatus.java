package com.fansfoot.fansfoot.API;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

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




    }









