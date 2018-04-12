package com.example.a77011_40_05.proxiservices.Holders;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a77011_40_05.proxiservices.Entities.Photo;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.Functions;

/**
 * Created by 77011-40-05 on 14/03/2018.
 */

public class PhotoHolder extends RecyclerView.ViewHolder {

    public final TextView txtName;
    public final ImageView imgPhoto;

    public PhotoHolder(View view){
        super(view);

        txtName = (TextView) view.findViewById(R.id.txtName);
        imgPhoto = (ImageView) view.findViewById(R.id.imgPhoto);
    }

    public void setPhoto(Photo photo, Context context){
        txtName.setText(photo.getName());
        Bitmap bmpPhoto = Functions.loadFromInternalStorage(photo.getPath(),photo.getName());
        imgPhoto.setImageBitmap(bmpPhoto);
    }
}
