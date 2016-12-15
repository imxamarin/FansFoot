package com.fansfoot.fansfoot.DefaultPages;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fansfoot.fansfoot.Adapters.ProfileRecycleViewAdapter;
import com.fansfoot.fansfoot.MainActivity;
import com.fansfoot.fansfoot.R;

/**
 * Created by xamarin on 05/12/16.
 */

public class ProfilePage extends Fragment {

    Context context;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.profile_fragment,null,false);
        context = getActivity();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.Profiletoolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        final CheckBox editbtn = (CheckBox) view.findViewById(R.id.cm_ProfileToolBar_Edit);
        final CheckBox logoutbtn = (CheckBox) view.findViewById(R.id.cm_ProfileToolBar_Logout);
        final EditText ProfileNameEdtext = (EditText) view.findViewById(R.id.ProfileNameEditView);
        final EditText ProfileCityEdtext = (EditText) view.findViewById(R.id.ProfileCityEditView);
        final EditText ProfileCountryEdtext = (EditText) view.findViewById(R.id.ProfileCountryEditView);
        final EditText ProfileBirthDayEdtext = (EditText) view.findViewById(R.id.ProfileBirthdayEditView);
        disableEditText(ProfileNameEdtext);
        disableEditText(ProfileCityEdtext);
        disableEditText(ProfileCountryEdtext);
        disableEditText(ProfileBirthDayEdtext);

        logoutbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(editbtn.isChecked()){
                    if(b==true){
                        Snackbar.make(view,"USER LOGOUT",Snackbar.LENGTH_SHORT).show();
                    }else {
                        Snackbar.make(view,"DATA SAVED",Snackbar.LENGTH_SHORT).show();
                    }
                }else {
                    Snackbar.make(view,"USER LOGOUT",Snackbar.LENGTH_SHORT).show();
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
                    logoutbtn.setText("SAVE");
                }else {
                    disableEditText(ProfileNameEdtext);
                    disableEditText(ProfileCityEdtext);
                    disableEditText(ProfileCountryEdtext);
                    disableEditText(ProfileBirthDayEdtext);
                    logoutbtn.setText("LOGOUT");
                }
            }
        });


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
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        MenuInflater _menu_inflater = MainActivity.gettheMenuInflater();
//        _menu_inflater.inflate(R.menu.profile_menu,menu);
//    }





}