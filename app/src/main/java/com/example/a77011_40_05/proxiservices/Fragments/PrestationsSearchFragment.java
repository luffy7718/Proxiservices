package com.example.a77011_40_05.proxiservices.Fragments;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.example.a77011_40_05.proxiservices.Adapters.MyPagerAdapter;
import com.example.a77011_40_05.proxiservices.Entities.CategoriesPrestations;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.App;
import com.example.a77011_40_05.proxiservices.Utils.AsyncCallWS;
import com.example.a77011_40_05.proxiservices.Utils.Constants;
import com.example.a77011_40_05.proxiservices.Utils.DepthPageTransform;
import com.example.a77011_40_05.proxiservices.Utils.ZoomPageTransformer;
import com.google.gson.Gson;


public class ProposeFragment extends Fragment {

    ViewPager viewPager;
    Context context;
    FragmentActivity fragmentActivity;
    App app;


    public ProposeFragment() {
        // Required empty public constructor
    }


    public static ProposeFragment newInstance(Bundle args) {
        ProposeFragment fragment = new ProposeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity().getApplicationContext();
         app = (App) getActivity().getApplication();

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view=inflater.inflate(R.layout.fragment_propose, container, false);

        viewPager=(ViewPager)view.findViewById(R.id.pager);

        //getSupportFragmentManager()));
        viewPager.setAdapter(new MyPagerAdapter(fragmentActivity.getSupportFragmentManager()));

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        tabs.setViewPager(viewPager);

        viewPager.setPageTransformer(false, new ZoomPageTransformer());
        viewPager.setPageTransformer(false, new DepthPageTransform());

        //viewPager.On page change ??

     
        return view;
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.fragmentActivity = (FragmentActivity) activity;

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            context = activity.getBaseContext();
        }

    }

}
