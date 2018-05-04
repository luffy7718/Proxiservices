package com.example.a77011_40_05.proxiservices.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_05.proxiservices.Entities.Prestation;
import com.example.a77011_40_05.proxiservices.Entities.Prestations;
import com.example.a77011_40_05.proxiservices.Holders.PrestationHolder;
import com.example.a77011_40_05.proxiservices.Holders.PrestationHolderAccount;
import com.example.a77011_40_05.proxiservices.R;

/**
 * Created by 77011-40-05 on 16/03/2018.
 */

public class PrestationAdapterAccount extends RecyclerView.Adapter<PrestationHolderAccount> {

    Prestations prestations;
    Context context;



    public PrestationAdapterAccount(Prestations prestations, Context context) {
        this.prestations = prestations;
        this.context = context;

    }



    @Override
    public PrestationHolderAccount onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_prestation_account,parent,false);
        return new PrestationHolderAccount(view);
    }

    @Override
    public void onBindViewHolder(PrestationHolderAccount holder, int position) {
        Prestation prestation = prestations.get(position);
        holder.setPrestation(prestation,context);

    }

    @Override
    public int getItemCount() {
        return prestations.size();
    }

}