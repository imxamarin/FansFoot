package com.fansfoot.fansfoot.DefaultPages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;

import java.util.Arrays;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by xamarin on 16/12/16.
 */

public class LoginPage extends Fragment {

    Button Loginbtn,ForgetpasswdBtn,SignUpBtn;
    EditText EmailEdTxt,PasswdEdTxt;
    LoginButton FbBtn;
    CallbackManager callbackManager;


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



        Loginbtn = (Button) view.findViewById(R.id.LoginButton);
        FbBtn = (LoginButton) view.findViewById(R.id.LoginFbBtn);
        ForgetpasswdBtn = (Button) view.findViewById(R.id.LoginForgetPasswdButton);
        SignUpBtn = (Button) view.findViewById(R.id.LoginSignUpButton);
        EmailEdTxt = (EditText) view.findViewById(R.id.LoginEmailEditText);
        PasswdEdTxt = (EditText) view.findViewById(R.id.LoginPasswordEditText);

        FbBtn.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday","user_hometown","user_about_me"));
        FbBtn.setFragment(this);
        FbBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
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
