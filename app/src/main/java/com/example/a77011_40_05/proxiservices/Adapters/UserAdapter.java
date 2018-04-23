package com.example.a77011_40_05.proxiservices.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_05.proxiservices.Entities.Prestation;
import com.example.a77011_40_05.proxiservices.Entities.Prestations;
import com.example.a77011_40_05.proxiservices.Entities.User;
import com.example.a77011_40_05.proxiservices.Entities.Users;
import com.example.a77011_40_05.proxiservices.Holders.PrestationHolder;
import com.example.a77011_40_05.proxiservices.Holders.UserHolder;
import com.example.a77011_40_05.proxiservices.R;

/**
 * Created by 77011-40-05 on 16/03/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserHolder> {

    Users users;
    Context context;
    public UserAdapter(Users users, Context context) {
        this.context = context;
        this.users = users;
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_user,parent,false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        User user = users.get(position);
        holder.setUser(user,context);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

}