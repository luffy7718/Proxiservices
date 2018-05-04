package com.example.a77011_40_05.proxiservices.Holders;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a77011_40_05.proxiservices.Activities.HomeActivity;
import com.example.a77011_40_05.proxiservices.Entities.Prestation;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.Constants;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by 77011-40-05 on 14/03/2018.
 */

public class PrestationHolderAccount extends RecyclerView.ViewHolder {

    public final TextView txtName;
    public final TextView txtDescription;
    public final ImageView imgPhoto;
    public final LinearLayout lltBody;
    //public  final Button btnSearch;



    public PrestationHolderAccount(View view) {
        super(view);

        txtName = (TextView) view.findViewById(R.id.txtName);
        txtDescription = (TextView) view.findViewById(R.id.txtDescription);
        imgPhoto = (ImageView) view.findViewById(R.id.imgPhoto);
        lltBody = view.findViewById(R.id.lltBody);
        //btnSearch=(Button)view.findViewById(R.id.btnSearch);
    }

    public void setPrestation(final Prestation prestation, final Context context) {
        txtName.setText(prestation.getName() + " " + prestation.getFirstname());
        txtDescription.setText(prestation.getDescription());
        Picasso.with(context)
                .load(Constants._URL_WEBSERVICE + prestation.getPath())
                .transform(new CropCircleTransformation())
                .into(imgPhoto);

        lltBody.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                    HomeActivity home = (HomeActivity) context;
                    Bundle params = new Bundle();
                    Gson gson=new Gson();
                    String json = gson.toJson(prestation);
                    params.putString("prestation", json);
                    home.changeFragment(Constants._FRAG_PRESTATION_EDIT, params);
            }
        });



    }
}
