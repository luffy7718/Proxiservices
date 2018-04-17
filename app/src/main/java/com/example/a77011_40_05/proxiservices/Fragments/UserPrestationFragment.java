package com.example.a77011_40_05.proxiservices.Fragments;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a77011_40_05.proxiservices.Entities.CategoriesPrestations;
import com.example.a77011_40_05.proxiservices.Entities.User;
import com.example.a77011_40_05.proxiservices.Entities.Users;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.CallAsyncTask;
import com.example.a77011_40_05.proxiservices.Utils.Constants;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


public class UserPrestationFragment extends Fragment {

    RecyclerView rvwUserPrestationFragment;
    ListView lswUserPrestation;
    Context context;
    CategoriesPrestations categoriesPrestations;
    int idPrestation;

    public UserPrestationFragment() {
        // Required empty public constructor
    }

    public static UserPrestationFragment newInstance(Bundle args) {
        UserPrestationFragment fragment = new UserPrestationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idPrestation = getArguments().getInt("idPrestation");
        }
        //idPrestation = savedInstanceState.getInt("idPrestation");
        context = getActivity().getApplicationContext();
        //String url = Constants._URL_WEBSERVICE+"getUserPrestation.php";
        String url = Constants._URL_WEBSERVICE+"getAllUserPrestation.php";
        String[] dataModel = new String[]{"idPrestation"};
        CallAsyncTask callAsyncTask = new CallAsyncTask(url, dataModel, new CallAsyncTask.OnAsyncTaskListner() {
            @Override
            public void onResultGet(String result) {
                Gson gson = new Gson();
                Users users = gson.fromJson(result, Users.class);
                UserAdapter userAdapter = new UserAdapter(context, users);
                lswUserPrestation.setAdapter(userAdapter);
            }
        });
        callAsyncTask.execute(""+idPrestation);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_prestation, container, false);
        lswUserPrestation = view.findViewById(R.id.lswUserPrestation);

        return view;
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            context = activity.getBaseContext();
        }
    }

    private class UserAdapter extends ArrayAdapter<User> {

        private class ViewHolder {
            TextView txtNom;
            ImageView imgUser;
        }

        public UserAdapter(Context context, Users users) {
            super(context, R.layout.item_user, users);
        }

        @Override
        public View getView(final int position, View itemView, ViewGroup parent) {

            final User user = getItem(position);
            ViewHolder viewHolder;

            if (itemView == null) {
                viewHolder = new ViewHolder();

                itemView = LayoutInflater.from(context)
                        .inflate(R.layout.item_user, parent, false);

                viewHolder.txtNom = itemView.findViewById(R.id.txtNom);
                viewHolder.imgUser = itemView.findViewById(R.id.imgUser);

                itemView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) itemView.getTag();
            }
            Log.e("[DEBUG]",user.getPath());
            viewHolder.txtNom.setText(user.getNom().toUpperCase());
            Picasso.with(context).load(user.getPath()).into(viewHolder.imgUser);

            return itemView;
        }
    }
}
