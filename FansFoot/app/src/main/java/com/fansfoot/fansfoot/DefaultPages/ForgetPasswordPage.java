package com.fansfoot.fansfoot.DefaultPages;

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

import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;

/**
 * Created by xamarin on 16/12/16.
 */

public class ForgetPasswordPage extends Fragment {


    Button ForgetpaswdEmailBtn;
    EditText EmailEdTxt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.forget_password,container,false);
        EmailEdTxt = (EditText) view.findViewById(R.id.ForgetpassEmailEdtxt);
        ForgetpaswdEmailBtn = (Button) view.findViewById(R.id.ForgetpassBtn);
        ForgetpaswdEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckFields(EmailEdTxt)){
                    Snackbar.make(view,"Password Sent",Snackbar.LENGTH_SHORT).show();
                    FragmentTransaction fragmentTransaction;
                    FragmentManager manager = MainActivity.getBaseFragmentManager();
                    fragmentTransaction = manager.beginTransaction();
                    LoginPage Homepage = new LoginPage();
                    fragmentTransaction.replace(R.id.frag,Homepage);
                    fragmentTransaction.commit();
                }else {
                    Snackbar.make(view,"Fields Cannot Be Empty",Snackbar.LENGTH_SHORT).show();
                }
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
