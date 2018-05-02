package com.example.a77011_40_05.proxiservices.Holders;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a77011_40_05.proxiservices.Activities.HomeActivity;
import com.example.a77011_40_05.proxiservices.Adapters.PrestationAdapter;
import com.example.a77011_40_05.proxiservices.Entities.Photo;
import com.example.a77011_40_05.proxiservices.Entities.Prestation;
import com.example.a77011_40_05.proxiservices.Entities.Prestations;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.Constants;
import com.example.a77011_40_05.proxiservices.Utils.Functions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by 77011-40-05 on 14/03/2018.
 */

public class PrestationHolder extends RecyclerView.ViewHolder {

    public final TextView txtName;
    public final TextView txtDescription;
    public final ImageView imgPhoto;
    public  final Button btnSearch;



    public PrestationHolder(View view) {
        super(view);

        txtName = (TextView) view.findViewById(R.id.txtName);
        txtDescription = (TextView) view.findViewById(R.id.txtDescription);
        imgPhoto = (ImageView) view.findViewById(R.id.imgPhoto);
        btnSearch=(Button)view.findViewById(R.id.btnSearch);
    }

    public void setPrestation(final Prestation prestation, final Context context, final Activity activity) {
        txtName.setText(prestation.getName() + " " + prestation.getFirstname());
        txtDescription.setText(prestation.getDescription());
        Picasso.with(context)
                .load(Constants._URL_WEBSERVICE + prestation.getPath())
                .transform(new CropCircleTransformation())
                .into(imgPhoto);

        btnSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.e("longitude :", String.valueOf(prestation.getLongitude()));
                Log.e("latitude :", String.valueOf(prestation.getLatitude()));
                if (activity != null) {
                    HomeActivity home = (HomeActivity) activity;
                    Bundle params = new Bundle();
                   /* Gson gson=new Gson();
                    String json=gson.toJson(prestation.getClass());*/
                    params.putDouble("latitude", prestation.getLatitude());
                    params.putDouble("longitude", prestation.getLongitude());
                    home.changeFragment(Constants._FRAG_MAPS_SEARCH, params);


                }
            }
        });



    }
}
