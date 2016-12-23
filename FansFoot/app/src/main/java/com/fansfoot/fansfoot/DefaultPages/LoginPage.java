package com.fansfoot.fansfoot.DefaultPages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.fansfoot.fansfoot.API.ConstServer;
import com.fansfoot.fansfoot.API.FacebookFansfoot;
import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by xamarin on 16/12/16.
 */

public class LoginPage extends Fragment {
    RequestQueue mRequestQueue;
    Button Loginbtn,ForgetpasswdBtn,SignUpBtn;
    EditText EmailEdTxt,PasswdEdTxt;
    LoginButton FbBtn;
    CallbackManager callbackManager;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferencesBeta;
    SharedPreferences sharedPreferencesDelta;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editorBeta;
    SharedPreferences.Editor editorDelta;
    FacebookFansfoot facebookFansfoot;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.login_page,container,false);
        sharedPreferences =getActivity().getSharedPreferences("FacebookPrefrence", Context.MODE_PRIVATE);
        sharedPreferencesBeta =getActivity().getSharedPreferences("FansFootPerfrence", Context.MODE_PRIVATE);
        sharedPreferencesDelta =getActivity().getSharedPreferences("FansFootServersPerfrence", Context.MODE_PRIVATE);
        Cache cache = new DiskBasedCache(MainActivity.getContext().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
        Loginbtn = (Button) view.findViewById(R.id.LoginButton);
        FbBtn = (LoginButton) view.findViewById(R.id.LoginFbBtn);
        ForgetpasswdBtn = (Button) view.findViewById(R.id.LoginForgetPasswdButton);
        SignUpBtn = (Button) view.findViewById(R.id.LoginSignUpButton);
        EmailEdTxt = (EditText) view.findViewById(R.id.LoginEmailEditText);
        PasswdEdTxt = (EditText) view.findViewById(R.id.LoginPasswordEditText);
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
                                   Log.v("LoginActivityAlpha", response.toString());
                                   try {
                                       String email = object.getString("email");
                                       String id = object.getString("id");
                                       String birthday = object.getString("birthday"); // 01/31/1980 format
                                       String name = object.getString("name");
                                       String location = object.getJSONObject("location").getString("name");
                                       String homelocation = object.getJSONObject("hometown").getString("name");
                                       String picture = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                       String[] s = location.split(",");
                                       String locationCity = s[0];
                                       String locationCountry = s[1];
                                       editor = sharedPreferences.edit();
                                       editor.putString("FbName", name);
                                       editor.putString("Fbemail", email);
                                       editor.putString("FbID", id);
                                       editor.putString("Fbbirthday", birthday);
                                       editor.putString("FblocationCity", locationCity);
                                       editor.putString("FblocationCountry", locationCountry);
                                       editor.putString("Fbpicture", picture);
                                       Log.v("ActivityAlpha", response.toString());
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
                DoTheCallToFB();
                Snackbar.make(view,"Login Sucessful",Snackbar.LENGTH_SHORT).show();
                DoThisOperation();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.getContext(), "yuhu", Toast.LENGTH_SHORT).show();
                if(CheckFields(EmailEdTxt)&& CheckFields(PasswdEdTxt)){
                    if(CheckEmail(EmailEdTxt)) {
                        if(CheckPassword(PasswdEdTxt)) {
                            FragmentTransaction fragmentTransaction;
                            FragmentManager manager = MainActivity.getBaseFragmentManager();
                            fragmentTransaction = manager.beginTransaction();
                            ProfilePage profilePage = new ProfilePage();
                            fragmentTransaction.replace(R.id.frag, profilePage);
                            fragmentTransaction.commit();
                        }else {
                            Snackbar.make(view,"Password cant be short",Snackbar.LENGTH_SHORT).show();
                        }
                    }else {
                        Snackbar.make(view,"Enter a Valid Email ID",Snackbar.LENGTH_SHORT).show();
                    }
                }else {
                    Snackbar.make(view,"Fields Cannot Be Empty",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    FragmentTransaction fragmentTransaction;
                    FragmentManager manager = MainActivity.getBaseFragmentManager();
                    fragmentTransaction = manager.beginTransaction();
                    SignUpPage profileSignUpPage = new SignUpPage();
                    fragmentTransaction.replace(R.id.frag,profileSignUpPage);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

            }
        });


        ForgetpasswdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    FragmentTransaction fragmentTransaction;
                    FragmentManager manager = MainActivity.getBaseFragmentManager();
                    fragmentTransaction = manager.beginTransaction();
                    ForgetPasswordPage profileForgetPage = new ForgetPasswordPage();
                    fragmentTransaction.replace(R.id.frag,profileForgetPage);
                     fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

            }
        });




        return view;
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
                ConstServer._ConCat+
                ConstServer._device_type;
        Log.d("ChkLog",ModUrl);

        JsonObjectRequest _JsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                ModUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Gson _Gson = new Gson();
                facebookFansfoot =  _Gson.fromJson(response.toString(), FacebookFansfoot.class);
                Log.d("ChkLog",facebookFansfoot.getMessage());
                Log.d("ChkLog",facebookFansfoot.getUserId());
                Log.d("ChkLog",facebookFansfoot.getDeviceToken());
                editorBeta = sharedPreferencesBeta.edit();
                editorBeta.putString("FbFFID", facebookFansfoot.getUserId());
                editorBeta.putString("FbFFMSG", facebookFansfoot.getMessage());
                editorBeta.putInt("FbFFSTATUS", facebookFansfoot.getStatus());
                editorBeta.commit();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(_JsonObjectRequest);

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

    public boolean CheckPassword(EditText edittext){
        String ed_text = edittext.getText().toString().trim();
        if(ed_text.length()>7 && ed_text.length()<15){
            edittext.setTextColor(Color.BLACK);
            return true;
        }else {
            edittext.setTextColor(Color.RED);
            return false;
        }

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
