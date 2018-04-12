package com.example.a77011_40_05.proxiservices.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.a77011_40_05.proxiservices.Entities.Prestation;
import com.example.a77011_40_05.proxiservices.Entities.Prestations;
import com.example.a77011_40_05.proxiservices.Entities.Users;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.App;
import com.example.a77011_40_05.proxiservices.Utils.CallAsyncTask;
import com.example.a77011_40_05.proxiservices.Utils.Constants;
import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity implements CallAsyncTask.OnAsyncTaskListner {


    private static int SPLASH_TIME_OUT = 2000;
    App app;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (App) getApplication();
        app.setPrestations(loadPrestations());
        context = this;

        /*Intent intent = new Intent(getApplicationContext(),BasicActivity.class);
        startActivity(intent);
        finish();*/

        try{
            CallAsyncTask callAsyncTask = new CallAsyncTask(Constants._URL_WEBSERVICE+"getAllUsers.php",new String[]{},context);
            //CallAsyncTask callAsyncTask = new CallAsyncTask(_URL_WEBSERVICE+"loadusers.php",new String[]{},context);
            callAsyncTask.execute();
        }catch (Exception e){
            Toast.makeText(this,"Connexion impossible", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onResultGet(String result) {
        if(!result.isEmpty()){
            try{
                Gson gson = new Gson();
                Users users = gson.fromJson(result,Users.class);
                app.setUsers(users);
            }catch (Exception e){
                Log.e("[ERROR]",e.toString());
                Log.e("[ERROR]","For result "+result);
            }
            //Session.setUsersList(users);
            Intent intent = null;
            if(Build.VERSION.SDK_INT >= 23 )  {
                intent = new Intent(getApplicationContext(),PermissionActivity.class);
            }else{
                intent = new Intent(getApplicationContext(),HomeActivity.class);
            }

            if(intent != null){
                startActivity(intent);
                finish();
            }
        }

    }


    private Prestations loadPrestations(){
        Prestations prestations = new Prestations();

        Prestation prestation = new Prestation("Jardinage",1);
        prestations.add(prestation);

        prestation = new Prestation("Bricolage",2);
        prestations.add(prestation);

        return prestations;

    }




 }
