package com.example.a77011_40_05.proxiservices.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.a77011_40_05.proxiservices.Fragments.pageFragment;
public class MyPagerAdapter extends FragmentStatePagerAdapter {//le state c'est pour avoir fragments ilimité

    public MyPagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {


        return pageFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position) {
        String tabtitles[] = new String[] { "proposition", "demande" };
        return tabtitles[position];
    }
}