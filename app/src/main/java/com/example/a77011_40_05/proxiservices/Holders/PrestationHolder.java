package com.example.a77011_40_05.proxiservices.Holders;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a77011_40_05.proxiservices.Entities.Photo;
import com.example.a77011_40_05.proxiservices.Entities.Prestation;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.Functions;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by 77011-40-05 on 14/03/2018.
 */

public class PrestationHolder extends RecyclerView.ViewHolder {

    public final TextView txtName;
    public final TextView txtDescription;
    public final ImageView imgPhoto;

    public PrestationHolder(View view){
        super(view);

        txtName = (TextView) view.findViewById(R.id.txtName);
        txtDescription = (TextView) view.findViewById(R.id.txtDescription);
        imgPhoto = (ImageView) view.findViewById(R.id.imgPhoto);
    }

    public void setPrestation(Prestation prestation, Context context){
        txtName.setText(prestation.getName()+" "+prestation.getFirstname());
        txtDescription.setText(prestation.getDescription());
        Picasso.with(context)
                .load(prestation.getPath())
                .transform(new CropCircleTransformation())
                .into(imgPhoto);


    }
}
