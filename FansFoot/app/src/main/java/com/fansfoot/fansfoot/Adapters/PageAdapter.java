package com.fansfoot.fansfoot.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.fansfoot.fansfoot.TabLayout.AlphaHomePage;
import com.fansfoot.fansfoot.TabLayout.BetaHomePage;
import com.fansfoot.fansfoot.TabLayout.DeltaHomePage;

/**
 * Created by kafir on 09-Dec-16.
 */

public class PageAdapter extends FragmentStatePagerAdapter {

    int tabCount;
    public PageAdapter(FragmentManager fm,int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            AlphaHomePage alpha = new AlphaHomePage();
            return  alpha;
        }else if( position ==1){
            BetaHomePage beta = new BetaHomePage();
            return beta;
        }else if(position ==2){
            DeltaHomePage delta = new DeltaHomePage();
            return delta;
        }
        else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
