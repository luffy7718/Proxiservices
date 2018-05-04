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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a77011_40_05.proxiservices.Activities.HomeActivity;
import com.example.a77011_40_05.proxiservices.Adapters.PrestationAdapter;
import com.example.a77011_40_05.proxiservices.Adapters.PrestationAdapterAccount;
import com.example.a77011_40_05.proxiservices.Entities.Prestations;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.AsyncCallWS;
import com.example.a77011_40_05.proxiservices.Utils.Constants;
import com.example.a77011_40_05.proxiservices.Utils.Session;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import static com.example.a77011_40_05.proxiservices.Utils.Constants._FRAG_ACCOUNT_GALLERY;
import static com.example.a77011_40_05.proxiservices.Utils.Constants._FRAG_ACCOUNT_PROFILE_PICS;


public class AccountFragment extends Fragment {

    Context context;

    Prestations myRequests;
    Prestations myProposes;
    PrestationAdapterAccount myRequestsAdapter;
    PrestationAdapterAccount myProposesAdapter;

    RecyclerView rvwMyRequestsList;
    RecyclerView rvwMyProposesList;



    public AccountFragment() {
        // Required empty public constructor
    }



    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        //LinearLayout lltSections = view.findViewById(R.id.lltSections);

        rvwMyRequestsList = view.findViewById(R.id.rvwMyRequestsList);
        rvwMyProposesList = view.findViewById(R.id.rvwMyProposesList);
        Button btnProfilPics = view.findViewById(R.id.btnProfilPics);
        Button btnGallery = view.findViewById(R.id.btnGallery);
        ImageView imgProfil = view.findViewById(R.id.imgProfil);
        TextView txtName = view.findViewById(R.id.txtName);

        txtName.setText(Session.getMyUser().getFullName());

        Log.e(Constants._TAG_LOG,Session.getMyUser().getPath());
        Picasso.with(context).load(Constants._URL_WEBSERVICE+Session.getMyUser().getPath())
                .transform(new CropCircleTransformation()).into(imgProfil);

        btnProfilPics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity home = (HomeActivity) getActivity();
                home.changeFragment(_FRAG_ACCOUNT_PROFILE_PICS,null);
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity home = (HomeActivity) getActivity();
                home.changeFragment(_FRAG_ACCOUNT_GALLERY,null);
            }
        });

        //RECYCLER VIEW
        myRequests = new Prestations();
        RecyclerView.LayoutManager layoutManagerR = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        rvwMyRequestsList.setLayoutManager(layoutManagerR);
        rvwMyRequestsList.setItemAnimator(new DefaultItemAnimator());
        myRequestsAdapter = new PrestationAdapterAccount(myRequests,context);
        rvwMyRequestsList.setAdapter(myRequestsAdapter);

        myProposes = new Prestations();
        RecyclerView.LayoutManager layoutManagerP = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        rvwMyProposesList.setLayoutManager(layoutManagerP);
        rvwMyProposesList.setItemAnimator(new DefaultItemAnimator());
        myProposesAdapter = new PrestationAdapterAccount(myProposes,context);
        rvwMyProposesList.setAdapter(myProposesAdapter);

        loadMyPrestation();

        return view;
    }

    private void loadMyPrestation(){
        AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "getPrestationsByFilter.php", new AsyncCallWS.OnCallBackAsyncTask() {
            @Override
            public void onResultCallBack(String result) {
                if(!result.isEmpty()){
                    Gson gson = new Gson();
                    JsonObject json = gson.fromJson(result,JsonObject.class);
                    if(json.has("proposes")){
                        Log.e(Constants._TAG_LOG,"Proposes: "+json.get("proposes"));
                        try{
                            myProposes = gson.fromJson(json.get("proposes"),Prestations.class);
                            myProposesAdapter = new PrestationAdapterAccount(myProposes,context);
                            rvwMyProposesList.setAdapter(myProposesAdapter);
                            //myProposesAdapter.notifyDataSetChanged();
                        }catch (Exception e){
                            Log.e(Constants._TAG_LOG,"ERROR "+e.getMessage());
                        }

                    }

                    if(json.has("requests")){
                        Log.e(Constants._TAG_LOG,"Requests: "+json.get("requests"));
                        try{
                            myRequests = gson.fromJson(json.get("requests"),Prestations.class);
                            myRequestsAdapter= new PrestationAdapterAccount(myRequests,context);
                            rvwMyRequestsList.setAdapter(myRequestsAdapter);
                            //myRequestsAdapter.notifyDataSetChanged();
                        }catch (Exception e){
                            Log.e(Constants._TAG_LOG,"ERROR "+e.getMessage());
                        }

                    }
                }
            }
        });
        //asyncCallWS.addParam("isRequest",""+mode);
        asyncCallWS.addParam("idUser",""+Session.getMyUser().getIdUser());

        asyncCallWS.execute();
    }
}
