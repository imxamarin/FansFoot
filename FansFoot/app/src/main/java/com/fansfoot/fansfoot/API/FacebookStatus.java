package com.fansfoot.fansfoot.API;

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
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xamarin on 20/12/16.
 */

public  class FacebookStatus {


  public static   CallbackManager callbackManager = CallbackManager.Factory.create();
    public static   String socialAccessToken;
    public static  AccessToken accessToken;

    public static boolean CheckFbLogin(){
        AccessToken accessToken;
        accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null){
            return false;
        }else {
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

    public static void GraphReq(){
//        GraphRequestAsyncTask graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
//                new GraphRequest.GraphJSONObjectCallback() {
//            @Override
//            public void onCompleted(JSONObject object, GraphResponse response) {
//
//
//                Log.d("Fbdata",object.toString());
//            }
//        }).executeAsync();

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,birthday,first_name,gender,last_name,location,email,picture.type(large)");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "me", parameters, HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if (response != null) {
                            try {
                                JSONObject data = response.getJSONObject();
                                Log.d("FACEBOOK",data.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).executeAsync();



//        GraphRequest request = GraphRequest.newMeRequest(
//                AccessToken.getCurrentAccessToken(),
//                new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(
//                            JSONObject object,
//                            GraphResponse response) {
//                        // Application code
//                    }
//                });
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,name,link");
//        request.setParameters(parameters);
//        request.executeAsync();



//        GraphRequest request = GraphRequest.newMeRequest( AccessToken.getCurrentAccessToken(),
//                new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(JSONObject object,GraphResponse response) {
//                        try {
//                            String  email=object.getString("email");
//                            Log.d( "user email ", email);
//                        } catch (JSONException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//
//                    }
//
//                });
//
//        request.executeAsync();


    }


    public static void DoThat(){
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                socialAccessToken = AccessToken.getCurrentAccessToken().getToken();
                accessToken = loginResult.getAccessToken();
                GraphRequest request = GraphRequest.newMeRequest(
                        accessToken,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                     Log.d("Checkup",object.toString());
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,birthday,first_name,gender,last_name,location,email,picture.type(large)");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        }
      );

    }



    }









