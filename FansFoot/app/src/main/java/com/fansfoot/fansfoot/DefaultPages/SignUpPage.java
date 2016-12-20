package com.fansfoot.fansfoot.DefaultPages;

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

import com.fansfoot.fansfoot.DefaultActivities.YoutubePlayerActivity;
import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;

/**
 * Created by xamarin on 16/12/16.
 */

public class SignUpPage extends Fragment {

    Button SignUpRegBtn;
    EditText  NameEdTxt,EmailEdTxt,PasswdEdTxt,ConnPasswdTxt;
    ImageButton  FbBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.sign_up_page,container,false);
        FbBtn = (ImageButton) view.findViewById(R.id.SignUpFbBtn);
        NameEdTxt = (EditText) view.findViewById(R.id.SignUpNameEditText);
        EmailEdTxt = (EditText) view.findViewById(R.id.SignUpEmailEditText);
        PasswdEdTxt = (EditText) view.findViewById(R.id.SignUpPasswordEditText);
        ConnPasswdTxt = (EditText) view.findViewById(R.id.SignUpConnPasswdEditText);
        SignUpRegBtn = (Button) view.findViewById(R.id.SignUpRegBtn);
        SignUpRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckFields(NameEdTxt)&& CheckFields(EmailEdTxt) && CheckFields(PasswdEdTxt) && CheckFields(ConnPasswdTxt)){
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

        FbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction;
                FragmentManager manager = MainActivity.getBaseFragmentManager();
                manager.popBackStackImmediate();
                fragmentTransaction = manager.beginTransaction();
                FbLikePage fbLikePage = new FbLikePage();
                manager.popBackStackImmediate();
                fragmentTransaction.replace(R.id.frag,fbLikePage);
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

}
