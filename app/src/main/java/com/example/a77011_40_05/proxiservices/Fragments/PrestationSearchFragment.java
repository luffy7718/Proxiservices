package com.example.a77011_40_05.proxiservices.Fragments;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.a77011_40_05.proxiservices.Adapters.PrestationAdapter;
import com.example.a77011_40_05.proxiservices.Adapters.UserAdapter;
import com.example.a77011_40_05.proxiservices.Entities.CategoriesPrestations;
import com.example.a77011_40_05.proxiservices.Entities.Prestations;
import com.example.a77011_40_05.proxiservices.Entities.Users;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.AsyncCallWS;
import com.example.a77011_40_05.proxiservices.Utils.Constants;
import com.google.gson.Gson;


public class PrestationSearchFragment extends Fragment {

    Context context;
    RecyclerView rvwPrestationsList;
    Prestations prestations;
    PrestationAdapter prestationAdapter;
    int idPrestation;

    public PrestationSearchFragment() {
        // Required empty public constructor
    }

    public static PrestationSearchFragment newInstance(Bundle args) {
        PrestationSearchFragment fragment = new PrestationSearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idPrestation = getArguments().getInt("idPrestation");
        }
        //idPrestation = savedInstanceState.getInt("idPrestation");
        context = getActivity().getApplicationContext();
        AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "getPrestationsByCategory.php", new AsyncCallWS.OnCallBackAsyncTask() {
            @Override
            public void onResultCallBack(String result) {
                Log.e(Constants._TAG_LOG,"Result: "+result);
                Gson gson = new Gson();
                prestations = gson.fromJson(result,Prestations.class);
                prestationAdapter = new PrestationAdapter(prestations,context);
                rvwPrestationsList.setAdapter(prestationAdapter);
            }
        });
        asyncCallWS.addParam("idCategoryPrestation",""+idPrestation);
        asyncCallWS.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prestation_search, container, false);
        rvwPrestationsList = view.findViewById(R.id.rvwPrestationsList);
        prestations = new Prestations();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        rvwPrestationsList.setLayoutManager(layoutManager);
        rvwPrestationsList.setItemAnimator(new DefaultItemAnimator());
        prestationAdapter = new PrestationAdapter(prestations,context);
        rvwPrestationsList.setAdapter(prestationAdapter);
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
            context = activity.getBaseContext();
        }
    }
}
