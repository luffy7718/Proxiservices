package com.example.a77011_40_05.proxiservices.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.a77011_40_05.proxiservices.Entities.Prestations;
import com.example.a77011_40_05.proxiservices.Entities.Users;
import com.example.a77011_40_05.proxiservices.Fragments.PagePrestationsListFragment;
import com.example.a77011_40_05.proxiservices.Utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class MyPagerAdapter extends FragmentStatePagerAdapter {//le state c'est pour avoir
// fragments ilimité

    PagePrestationsListFragment propose;
    PagePrestationsListFragment request;

    public MyPagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
        propose = PagePrestationsListFragment.newInstance(0);
        request = PagePrestationsListFragment.newInstance(1);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return propose;
            case 1:
                return request;
            default:
                return propose;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position) {
        String tabtitles[] = new String[]{"proposition", "demande"};
        return tabtitles[position];
    }

    public void reloadPrestations(String result) {
        if (!result.isEmpty()) {
            //Log.e(Constants._TAG_LOG,"Search for "+search+": "+result);
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(result, JsonObject.class);
            if (json.has("proposes")) {
                Log.e(Constants._TAG_LOG, "Proposes: " + json.get("proposes"));
                try {
                    Prestations prestations = gson.fromJson(json.get("proposes"), Prestations
                            .class);
                    propose.refreshPrestationsList(prestations);
                } catch (Exception e) {
                    Log.e(Constants._TAG_LOG, "ERROR " + e.getMessage());
                }

            }

            if (json.has("requests")) {
                Log.e(Constants._TAG_LOG, "Requests: " + json.get("requests"));
                try {
                    Prestations prestations = gson.fromJson(json.get("requests"), Prestations
                            .class);
                    request.refreshPrestationsList(prestations);
                } catch (Exception e) {
                    Log.e(Constants._TAG_LOG, "ERROR " + e.getMessage());
                }

            }
        }
    }
}