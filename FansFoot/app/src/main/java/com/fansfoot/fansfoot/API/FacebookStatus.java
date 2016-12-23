package com.fansfoot.fansfoot.API;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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





    }









