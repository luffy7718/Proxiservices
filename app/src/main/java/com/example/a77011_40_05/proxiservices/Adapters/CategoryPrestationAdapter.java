package com.example.a77011_40_05.proxiservices.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_05.proxiservices.Entities.CategoryPrestation;
import com.example.a77011_40_05.proxiservices.Entities.CategoriesPrestations;
import com.example.a77011_40_05.proxiservices.Holders.CategoryPrestationHolder;
import com.example.a77011_40_05.proxiservices.R;


/**
 * Created by 77011-40-05 on 16/03/2018.
 */

public class CategoryPrestationAdapter extends RecyclerView.Adapter<CategoryPrestationHolder> {

    CategoriesPrestations categoriesPrestations;
    Context context;
    Activity activity = null;
    public CategoryPrestationAdapter(CategoriesPrestations categoriesPrestations, Context context) {
        this.categoriesPrestations = categoriesPrestations;
        this.context = context;
    }

    public CategoryPrestationAdapter(CategoriesPrestations categoriesPrestations, Context context, Activity activity) {
        this.categoriesPrestations = categoriesPrestations;
        this.context = context;
        this.activity = activity;
    }


    @Override
    public CategoryPrestationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_prestation,parent,false);
        return new CategoryPrestationHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryPrestationHolder holder, int position) {
        CategoryPrestation categoryPrestation = categoriesPrestations.get(position);
        holder.setPrestation(categoryPrestation,context,activity);

    }

    @Override
    public int getItemCount() {
        return categoriesPrestations.size();
    }
}