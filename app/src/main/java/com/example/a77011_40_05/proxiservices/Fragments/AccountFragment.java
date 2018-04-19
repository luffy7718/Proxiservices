package com.example.a77011_40_05.proxiservices.Fragments;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.a77011_40_05.proxiservices.Activities.HomeActivity;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.Session;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import static com.example.a77011_40_05.proxiservices.Utils.Constants._FRAG_ACCOUNT_GALLERY;
import static com.example.a77011_40_05.proxiservices.Utils.Constants._FRAG_ACCOUNT_PROFILE_PICS;


public class AccountFragment extends Fragment {

    Context context;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        Button btnProfilPics = view.findViewById(R.id.btnProfilPics);
        Button btnGallery = view.findViewById(R.id.btnGallery);
        ImageView imgProfil = view.findViewById(R.id.imgProfil);
        Picasso.with(context).load(Session.getMyUser().getPath())
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
