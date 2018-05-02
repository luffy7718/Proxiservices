package com.example.a77011_40_05.proxiservices.Utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.example.a77011_40_05.proxiservices.Entities.CategoriesPrestations;
import com.example.a77011_40_05.proxiservices.Entities.Erreur;
import com.example.a77011_40_05.proxiservices.Entities.Erreurs;
import com.example.a77011_40_05.proxiservices.Entities.Prestations;
import com.example.a77011_40_05.proxiservices.Entities.Users;
import com.google.gson.Gson;

/**
 * Created by 77011-40-05 on 15/03/2018.
 */

public class App extends Application {
    public static Erreurs erreurs;
    private static Users users;
    private static CategoriesPrestations categoriesPrestations;
    private static Prestations prestations;

    public static Erreurs getErreurs() {
        return erreurs;
    }

    public static void setErreurs(Erreurs erreurs) {
        App.erreurs = erreurs;
    }

    public static Prestations getPrestations() {
        return prestations;
    }

    public static void setPrestations(Prestations prestations) {
        App.prestations = prestations;
    }

    public static Users getUsers() {
        return users;
    }

    public static void setUsers(Users users) {
        App.users = users;
    }

    public static CategoriesPrestations getCategoriesPrestations() {
        return categoriesPrestations;
    }

    public static void setCategoriesPrestations(CategoriesPrestations categoriesPrestations) {
        App.categoriesPrestations = categoriesPrestations;
    }

    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        // Required initialization logic here!
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public static void addErreur(Erreur erreur, Context context) {

        if(App.erreurs == null)
        {
            App.erreurs = new Erreurs();
        }
        App.erreurs.add(erreur);
        saveErreur(context);
    }

    public static void saveErreur(Context context)
    {
        Gson gson = new Gson();
        String erreurs = gson.toJson(App.erreurs);

        Functions.addPreferenceString(context,"Erreurs",erreurs);
    }

    public static void loadErreur(Context context)
    {
        String erreurs = Functions.getPreferenceString(context,"Erreurs");

        if(!erreurs.isEmpty())
        {
            Gson gson = new Gson();
            App.erreurs.addAll(gson.fromJson(erreurs,Erreurs.class));
        }
    }

    public static void deleteErreur(Context context)
    {
        App.erreurs = new Erreurs();
        Functions.addPreferenceString(context,"Erreurs","");
    }

}