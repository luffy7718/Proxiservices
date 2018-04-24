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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.astuetz.PagerSlidingTabStrip;
import com.example.a77011_40_05.proxiservices.Adapters.MyPagerAdapter;
import com.example.a77011_40_05.proxiservices.Entities.CategoriesPrestations;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.App;
import com.example.a77011_40_05.proxiservices.Utils.AsyncCallWS;
import com.example.a77011_40_05.proxiservices.Utils.Constants;
import com.example.a77011_40_05.proxiservices.Utils.DepthPageTransform;
import com.example.a77011_40_05.proxiservices.Utils.ZoomPageTransformer;

import java.util.ArrayList;
import java.util.List;


public class PrestationsSearchFragment extends Fragment {

    ViewPager viewPager;
    Spinner spinCategory;
    Context context;
    MyPagerAdapter myPagerAdapter;
    FragmentActivity fragmentActivity;
    App app;

    //FILTERS
    int idCategoryPrestation = -1;


    public PrestationsSearchFragment() {
        // Required empty public constructor
    }


    public static PrestationsSearchFragment newInstance(Bundle args) {
        PrestationsSearchFragment fragment = new PrestationsSearchFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if(getArguments().containsKey("idCategoryPrestation")){
                idCategoryPrestation = getArguments().getInt("idCategoryPrestation");
            }
        }
        context = getActivity().getApplicationContext();
        app = (App) getActivity().getApplication();

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view=inflater.inflate(R.layout.fragment_prestations_search, container, false);

        viewPager=(ViewPager)view.findViewById(R.id.pager);

        //getSupportFragmentManager()));
        myPagerAdapter = new MyPagerAdapter(fragmentActivity.getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        tabs.setViewPager(viewPager);

        viewPager.setPageTransformer(false, new ZoomPageTransformer());
        viewPager.setPageTransformer(false, new DepthPageTransform());


        spinCategory = (Spinner) view.findViewById(R.id.spinCategory);

        List<String> categories=new ArrayList<>();
        categories.add("Tous");
        CategoriesPrestations cps = App.getCategoriesPrestations();
        for(int i= 0;i<cps.size();i++){
            categories.add(cps.get(i).getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,categories);

        // Drop down style will be listview with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        // attaching data adapter to spinner
        spinCategory.setAdapter(dataAdapter);

        if(idCategoryPrestation != -1){
            spinCategory.setSelection(idCategoryPrestation);
        }

        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(context, "Selected",Toast.LENGTH_SHORT).show();
                idCategoryPrestation = position;
                filterHasChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

    private void filterHasChange(){
        AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "getPrestationsByFilter.php", new AsyncCallWS.OnCallBackAsyncTask() {
            @Override
            public void onResultCallBack(String result) {
                if(!result.isEmpty()){
                    Log.e(Constants._TAG_LOG,"Result: "+result);
                    myPagerAdapter.reloadPrestations(result);
                }
            }
        });
        //asyncCallWS.addParam("isRequest",""+mode);
        if(idCategoryPrestation != 0){
            asyncCallWS.addParam("idCategoryPrestation",""+idCategoryPrestation);
        }
        asyncCallWS.execute();
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
