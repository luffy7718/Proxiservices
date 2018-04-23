package com.example.a77011_40_05.proxiservices.Holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a77011_40_05.proxiservices.Entities.Prestation;
import com.example.a77011_40_05.proxiservices.Entities.User;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.Constants;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by 77011-40-05 on 14/03/2018.
 */

public class UserHolder extends RecyclerView.ViewHolder {

    public final TextView txtName;
    public final TextView txtFirstname;
    public final ImageView imgPhoto;

    public UserHolder(View view){
        super(view);

        txtName = (TextView) view.findViewById(R.id.txtName);
        txtFirstname = (TextView) view.findViewById(R.id.txtFirstname);
        imgPhoto = (ImageView) view.findViewById(R.id.imgPhoto);
    }

    public void setUser(User user, Context context){
        txtName.setText(user.getName());
        txtFirstname.setText(user.getFirstname());
        Picasso.with(context)
                .load(Constants._URL_WEBSERVICE+user.getPath())
                .transform(new CropCircleTransformation())
                .into(imgPhoto);


    }
}
