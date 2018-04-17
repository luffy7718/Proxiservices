package com.example.a77011_40_05.proxiservices.Holders;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a77011_40_05.proxiservices.Activities.HomeActivity;
import com.example.a77011_40_05.proxiservices.Entities.CategoryPrestation;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.Constants;

/**
 * Created by 77011-40-05 on 14/03/2018.
 */

public class PrestationHolder extends RecyclerView.ViewHolder {

    public final TextView txtName;
    public final ImageView imgPresta;

    public PrestationHolder(View view){
        super(view);

        txtName = (TextView) view.findViewById(R.id.txtName);
        imgPresta = (ImageView) view.findViewById(R.id.imgPresta);
    }

    public void setPrestation(final CategoryPrestation categoryPrestation, final Context context, final Activity activity){
        txtName.setText(categoryPrestation.getName());
        int ressourceImage = context.getResources().getIdentifier(categoryPrestation.getImgName(),"drawable",context.getPackageName());
        imgPresta.setImageResource(ressourceImage);
        imgPresta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activity !=null){
                    HomeActivity home = (HomeActivity) activity;
                    Bundle params = new Bundle();
                    params.putInt("idPrestation", categoryPrestation.getId());
                    home.changeFragment(Constants._FRAG_USER_PRESTATION,params);
                }
            }
        });
    }
}
