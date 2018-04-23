package com.example.a77011_40_05.proxiservices.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.example.a77011_40_05.proxiservices.BroadcastReceivers.InternetBroadcastReceiver;
import com.example.a77011_40_05.proxiservices.Entities.CategoriesPrestations;
import com.example.a77011_40_05.proxiservices.Entities.Erreur;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.App;
import com.example.a77011_40_05.proxiservices.Utils.AsyncCallWS;
import com.example.a77011_40_05.proxiservices.Utils.Constants;
import com.example.a77011_40_05.proxiservices.Utils.Functions;
import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity {


    App app;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        app = (App) getApplication();
        loadCategoriesPrestations();
        App.loadErreur(context);

       /*if(App.erreurs.size() > 0 && Functions.isConnectionAvailable(context))
        {
            for(Erreur erreur:App.erreurs)
            {
                Functions.addErreur(erreur,context);
            }
            App.deleteErreur(context);
        }*/



        //testErreur2();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            final String SOME_ACTION = "android.net.com.CONNECTIVITY_CHANGE";
            IntentFilter intentFilter = new IntentFilter(SOME_ACTION);

            InternetBroadcastReceiver mReceiver = new InternetBroadcastReceiver();
            context.registerReceiver(mReceiver, intentFilter);
        }


    }

    private void loadCategoriesPrestations() {
        AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "getAllCategoriesPrestations.php", new AsyncCallWS.OnCallBackAsyncTask() {
            @Override
            public void onResultCallBack(String result) {
                if (!result.isEmpty()) {
                    Log.e(Constants._TAG_LOG, "loadCategoriesPrestations: " + result);
                    Gson gson = new Gson();
                    try {
                        app.setCategoriesPrestations(gson.fromJson(result, CategoriesPrestations.class));
                        goToHome();
                    } catch (Exception e) {
                        Log.e("[ERROR]", e.toString());
                        Log.e("[ERROR]", "For result " + result);
                    }
                } else {
                    //TODO Gerer la non-reception des données
                }
            }
        });
        asyncCallWS.execute();
    }



    private void goToHome() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT >= 23) {
            intent = new Intent(getApplicationContext(), PermissionActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), HomeActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
            finish();
        }
    }

    private void loadUsers() {
        //Ancienne méthodes...
    }

    public void testErreur() {

        int j = 20, i = 0;
        try {
            int tt = (j/i);
        } catch (Exception e) {
            Erreur erreur = new Erreur(e);
            Functions.addErreur(erreur,context);
        }
    }

    public void testErreur2() {

        try {
            addContentView(null,null);
        } catch (Exception e) {
            Erreur erreur = new Erreur(e);

            Functions.addErreur(erreur,context);
        }
    }
}
