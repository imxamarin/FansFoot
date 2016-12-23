package com.fansfoot.fansfoot.DefaultPages;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fansfoot.fansfoot.API.FacebookStatus;
import com.fansfoot.fansfoot.Adapters.PageAdapter;
import com.fansfoot.fansfoot.R;

/**
 * Created by kafir on 09-Dec-16.
 */

public class HomePage extends Fragment {
    PageAdapter mDemoCollection;
    ViewPager viewPager;
    TabLayout tabLayout;
    Context context;
    ProgressDialog progress;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.home_layout,container,false);

        context = getContext();
        progress = new ProgressDialog(context);
        progress.setMessage("Downloading Music");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setProgress(0);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.Hometoolbar);

        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        final TextView title = (TextView) view.findViewById(R.id.cmHomeTool_Title);
        final CheckBox searchBtn = (CheckBox) view.findViewById(R.id.cm_HomeToolBar_search);
        final SearchView searchview = (SearchView) view.findViewById(R.id.AlphaSearchView);
        CheckBox refresh = (CheckBox) view.findViewById(R.id.cm_HomeToolBar_Refesh);



        searchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    searchview.setVisibility(View.VISIBLE);
                }else {
                    searchview.setVisibility(View.GONE);
                }
            }
        });


        tabLayout = (TabLayout) view.findViewById(R.id.TabbedLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Hot"));
        tabLayout.addTab(tabLayout.newTab().setText("Trending"));
        tabLayout.addTab(tabLayout.newTab().setText("Fresh"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mDemoCollection = new PageAdapter(
                this.getChildFragmentManager(), tabLayout.getTabCount());
        viewPager = (ViewPager) view.findViewById(R.id.Pager);
        viewPager.setAdapter(mDemoCollection);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




        refresh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    progress.show();
                  viewPager.refreshDrawableState();
                }else {
                    tabLayout.refreshDrawableState();
                    progress.dismiss();
                }

            }
        });

        return view;

    }




}
