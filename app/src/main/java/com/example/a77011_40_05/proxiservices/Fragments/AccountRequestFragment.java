package com.example.a77011_40_05.proxiservices.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.a77011_40_05.proxiservices.Activities.HomeActivity;
import com.example.a77011_40_05.proxiservices.R;

import static com.example.a77011_40_05.proxiservices.Utils.Constants._FRAG_ACCOUNT_GALLERY;
import static com.example.a77011_40_05.proxiservices.Utils.Constants._FRAG_ACCOUNT_PROFILE_PICS;


public class AccountRequestFragment extends Fragment {

    public AccountRequestFragment() {
        // Required empty public constructor
    }



    public static AccountRequestFragment newInstance() {
        AccountRequestFragment fragment = new AccountRequestFragment();

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

}
