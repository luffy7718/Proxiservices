package com.example.a77011_40_05.proxiservices.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_05.proxiservices.Entities.Photo;
import com.example.a77011_40_05.proxiservices.Entities.Photos;
import com.example.a77011_40_05.proxiservices.Entities.Prestation;
import com.example.a77011_40_05.proxiservices.Entities.Prestations;
import com.example.a77011_40_05.proxiservices.Holders.PhotoHolder;
import com.example.a77011_40_05.proxiservices.Holders.PrestationHolder;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.Functions;
import com.google.gson.Gson;

/**
 * Created by 77011-40-05 on 16/03/2018.
 */

public class PrestationAdapter extends RecyclerView.Adapter<PrestationHolder> {

    Prestations prestations;
    Context context;
    public PrestationAdapter(Prestations prestations,Context context) {
        this.context = context;
        this.prestations = prestations;
    }

    @Override
    public PrestationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_prestation,parent,false);
        return new PrestationHolder(view);
    }

    @Override
    public void onBindViewHolder(PrestationHolder holder, int position) {
        Prestation prestation = prestations.get(position);
        holder.setPrestation(prestation,context);

    }

    @Override
    public int getItemCount() {
        return prestations.size();
    }

}