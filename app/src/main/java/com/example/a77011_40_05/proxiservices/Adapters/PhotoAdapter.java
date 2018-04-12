package com.example.a77011_40_05.proxiservices.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_05.proxiservices.Entities.Photo;
import com.example.a77011_40_05.proxiservices.Entities.Photos;
import com.example.a77011_40_05.proxiservices.Holders.PhotoHolder;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.Functions;
import com.google.gson.Gson;

/**
 * Created by 77011-40-05 on 16/03/2018.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {

    Photos photos;
    Context context;
    public PhotoAdapter(Context context) {
        this.context = context;
        loadPhotosFromPreferences();
    }

    @Override
    public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_photo,parent,false);
        return new PhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoHolder holder, int position) {
        Photo photo = photos.get(position);
        holder.setPhoto(photo,context);

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public int addPhoto(String name, String path){
        Photo photo = new Photo();
        photo.setId(getItemCount());
        photo.setName(name);
        photo.setPath(path);
        this.photos.add(photo);

        this.notifyDataSetChanged();
        savePhotosToPreferences();
        return photo.getId();
    }

    private void savePhotosToPreferences(){
        Gson gson = new Gson();
        String jsonPhotos = gson.toJson(this.photos);
        Log.e("[DEBUG]","Save: "+jsonPhotos);
        Functions.addPreferenceString(this.context,"Photos",jsonPhotos);
    }

    private void loadPhotosFromPreferences(){
        Gson gson = new Gson();
        String jsonPhotos = "";
        jsonPhotos = Functions.getPreferenceString(this.context,"Photos");
        Log.e("[DEBUG]","Load: "+jsonPhotos);
        if(!jsonPhotos.isEmpty()){
            this.photos = gson.fromJson(jsonPhotos,Photos.class);
        }else{
            this.photos = new Photos();
        }

    }
}