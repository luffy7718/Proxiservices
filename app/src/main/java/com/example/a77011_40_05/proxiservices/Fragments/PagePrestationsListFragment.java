package com.example.a77011_40_05.proxiservices.Fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_05.proxiservices.Adapters.PrestationAdapter;
import com.example.a77011_40_05.proxiservices.Entities.Prestations;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.AsyncCallWS;
import com.example.a77011_40_05.proxiservices.Utils.Constants;
import com.google.gson.Gson;


public class PagePrestationsListFragment extends Fragment {

    int mode;
    Context context;
    PrestationAdapter prestationAdapter;
    Prestations prestations;
    RecyclerView rvwPrestationsList;

    public static PagePrestationsListFragment newInstance(int mode) {
        Bundle args = new Bundle();
        args.putInt("mode", mode);
        PagePrestationsListFragment fragment = new PagePrestationsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_prestations_list, container, false);
        mode = getArguments().getInt("mode");

        rvwPrestationsList = view.findViewById(R.id.rvwPrestationsList);

        prestations = new Prestations();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        rvwPrestationsList.setLayoutManager(layoutManager);
        rvwPrestationsList.setItemAnimator(new DefaultItemAnimator());
        prestationAdapter = new PrestationAdapter(prestations,context);
        rvwPrestationsList.setAdapter(prestationAdapter);

        return view;
    }


    public void refreshPrestationsList(Prestations newList){
        this.prestations = newList;
        prestationAdapter = new PrestationAdapter(prestations,context);
        rvwPrestationsList.setAdapter(prestationAdapter);
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
            context = activity.getBaseContext();
        }
    }
}