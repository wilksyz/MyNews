package com.company.antoine.mynews.Fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class pageAdapter extends FragmentPagerAdapter{

    private String[] onglet = {"TOP STORIES","MOST POPULAR","BUSINESS"};

    public pageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        return (viewPagerFragment.newInstance(position));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return onglet[position];
    }
}
