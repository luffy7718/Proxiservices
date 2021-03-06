package com.example.a77011_40_05.proxiservices.Fragments;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.a77011_40_05.proxiservices.Activities.HomeActivity;
import com.example.a77011_40_05.proxiservices.Adapters.CategoryPrestationAdapter;
import com.example.a77011_40_05.proxiservices.Entities.CategoriesPrestations;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.App;
import com.example.a77011_40_05.proxiservices.Utils.Constants;

public class HomeFragment extends Fragment {

    RecyclerView rvwHomeFragment;
    Context context;
    CategoriesPrestations categoriesPrestations;
    Button btnAllServices;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity().getApplicationContext();
        App app = (App) getActivity().getApplication();
        categoriesPrestations = app.getCategoriesPrestations();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rvwHomeFragment = view.findViewById(R.id.rvwHomeFragment);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);

        RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(context,3);

        rvwHomeFragment.setLayoutManager(layoutManager2);
        rvwHomeFragment.setItemAnimator(new DefaultItemAnimator());
        rvwHomeFragment.setAdapter(new CategoryPrestationAdapter(categoriesPrestations,context,getActivity()));
        btnAllServices=(Button)view.findViewById(R.id.btnAllServices);

        btnAllServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity home = (HomeActivity) getActivity();
                home.changeFragment(Constants._FRAG_PRESTATION_SEARCH,null);

            }
        });




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

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            this.context = activity.getBaseContext();
        }
    }


}
