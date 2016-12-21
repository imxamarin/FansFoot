package com.fansfoot.fansfoot.DefaultPages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by xamarin on 16/12/16.
 */

public class LoginPage extends Fragment {

    Button Loginbtn,ForgetpasswdBtn,SignUpBtn;
    EditText EmailEdTxt,PasswdEdTxt;
    LoginButton FbBtn;
    CallbackManager callbackManager;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


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
                if(CheckFields(EmailEdTxt)&& CheckFields(PasswdEdTxt)){
                    FragmentTransaction fragmentTransaction;
                    FragmentManager manager = MainActivity.getBaseFragmentManager();
                    fragmentTransaction = manager.beginTransaction();
                    ProfilePage profilePage = new ProfilePage();
                    fragmentTransaction.replace(R.id.frag,profilePage);
                    fragmentTransaction.commit();
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



    public boolean CheckFields(EditText ed){
        String ed_text = ed.getText().toString().trim();

        if(ed_text.isEmpty() || ed_text.length() == 0 || ed_text.equals("") || ed_text == null)
        {
            return false;
        }else {
            return true;
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
