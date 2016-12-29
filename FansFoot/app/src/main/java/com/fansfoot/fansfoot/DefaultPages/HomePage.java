package com.fansfoot.fansfoot.DefaultPages;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.fansfoot.fansfoot.Adapters.PageAdapter;
import com.fansfoot.fansfoot.MainActivity;
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
        progress = ProgressDialog.show(getActivity(), "", "Please wait, Getting the new data...", true);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.Hometoolbar);
        viewPager = (ViewPager) view.findViewById(R.id.Pager);
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        final CheckBox searchBtn = (CheckBox) view.findViewById(R.id.cm_HomeToolBar_search);
        final SearchView searchview = (SearchView) view.findViewById(R.id.AlphaSearchView);
        CheckBox refresh = (CheckBox) view.findViewById(R.id.cm_HomeToolBar_Refesh);

        viewPager.setOffscreenPageLimit(2);
        searchview.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchview.setVisibility(View.GONE);
                searchview.clearFocus();
                return true;
            }
        });

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

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 0) {
                    FragmentTransaction transaction = MainActivity.getBaseFragmentManager().beginTransaction();
                    Fragment newFragment = new SearchFragment(); //your search fragment
                    Bundle args = new Bundle();
                    args.putString("query_string", query);
                    newFragment.setArguments(args);
                    transaction.replace(R.id.frag, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });


        tabLayout = (TabLayout) view.findViewById(R.id.TabbedLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Hot"));
        tabLayout.addTab(tabLayout.newTab().setText("Trending"));
        tabLayout.addTab(tabLayout.newTab().setText("Fresh"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
       CallthisOne();


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

                    progress.show();
                    final Handler handler = new Handler();
                    Runnable runable = new Runnable() {
                        @Override
                        public void run() {
                            CallthisOne();
                        }
                    };
                    handler.postDelayed(runable, 5000);



            }
        });

        return view;

    }

    private void CallthisOne() {

        mDemoCollection = new PageAdapter(
                this.getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(mDemoCollection);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        progress.dismiss();
    }


}
