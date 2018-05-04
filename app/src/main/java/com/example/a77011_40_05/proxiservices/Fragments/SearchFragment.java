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

import com.example.a77011_40_05.proxiservices.Adapters.PrestationAdapter;
import com.example.a77011_40_05.proxiservices.Adapters.UserAdapter;
import com.example.a77011_40_05.proxiservices.Entities.Prestations;
import com.example.a77011_40_05.proxiservices.Entities.Users;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.AsyncCallWS;
import com.example.a77011_40_05.proxiservices.Utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class SearchFragment extends Fragment {

    Context context;
    Activity activity;
    String search;

    RecyclerView rvwSearchUsers;
    RecyclerView rvwSearchServices;
    Prestations prestations;
    Users users;
    PrestationAdapter prestationAdapter;
    UserAdapter userAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }



    public static SearchFragment newInstance(Bundle args) {
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        if (getArguments() != null) {
            search = getArguments().getString("search");
        }
        if(!search.isEmpty()){
            AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "textSearch.php", new AsyncCallWS.OnCallBackAsyncTask() {
                @Override
                public void onResultCallBack(String result) {
                    if(!result.isEmpty()){
                        //Log.e(Constants._TAG_LOG,"Search for "+search+": "+result);
                        Gson gson = new Gson();
                        JsonObject json = gson.fromJson(result,JsonObject.class);
                        if(json.has("services")){
                            Log.e(Constants._TAG_LOG,"Services: "+json.get("services"));
                            try{
                                prestations = gson.fromJson(json.get("services"),Prestations.class);
                                prestationAdapter = new PrestationAdapter(prestations,context);
                                rvwSearchServices.setAdapter(prestationAdapter);
                            }catch (Exception e){
                                Log.e(Constants._TAG_LOG,"ERROR "+e.getMessage());
                            }

                        }

                        if(json.has("users")){
                            Log.e(Constants._TAG_LOG,"Users: "+json.get("users"));
                            try{
                                users = gson.fromJson(json.get("users"),Users.class);
                                userAdapter = new UserAdapter(users,context);
                                rvwSearchUsers.setAdapter(userAdapter);
                            }catch (Exception e){
                                Log.e(Constants._TAG_LOG,"ERROR "+e.getMessage());
                            }

                        }
                    }
                }
            });
            asyncCallWS.addParam("search",search);
            asyncCallWS.execute();
        }
    }

    private void refresh() {
        prestationAdapter = new PrestationAdapter(prestations,context);
        rvwSearchServices.setAdapter(prestationAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        rvwSearchServices = view.findViewById(R.id.rvwSearchServices);
        rvwSearchUsers = view.findViewById(R.id.rvwSearchUsers);

        prestations = new Prestations();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        rvwSearchServices.setLayoutManager(layoutManager);
        rvwSearchServices.setItemAnimator(new DefaultItemAnimator());
        prestationAdapter = new PrestationAdapter(prestations,context);
        rvwSearchServices.setAdapter(prestationAdapter);

        users = new Users();
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        rvwSearchUsers.setLayoutManager(layoutManager2);
        rvwSearchUsers.setItemAnimator(new DefaultItemAnimator());
        userAdapter = new UserAdapter(users,context);
        rvwSearchUsers.setAdapter(userAdapter);

        return view;
    }

}
