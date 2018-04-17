package com.example.a77011_40_05.proxiservices.Utils;

import android.app.Application;
import android.content.res.Configuration;

import com.example.a77011_40_05.proxiservices.Entities.CategoriesPrestations;
import com.example.a77011_40_05.proxiservices.Entities.Users;

/**
 * Created by 77011-40-05 on 15/03/2018.
 */

public class App extends Application {

    private static Users users;
    private static CategoriesPrestations categoriesPrestations;

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
}