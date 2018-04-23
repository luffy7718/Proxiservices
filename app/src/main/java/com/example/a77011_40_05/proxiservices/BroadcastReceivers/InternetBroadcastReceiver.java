package com.example.a77011_40_05.proxiservices.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.a77011_40_05.proxiservices.Entities.Erreur;
import com.example.a77011_40_05.proxiservices.Utils.App;
import com.example.a77011_40_05.proxiservices.Utils.Functions;

public class InternetBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
                ConnectivityManager connectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager
                        .getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    if(App.erreurs.size() > 0)
                    {
                        for(Erreur erreur:App.erreurs)
                        {
                            Functions.addErreur(erreur,context);
                        }
                        App.deleteErreur(context);
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
