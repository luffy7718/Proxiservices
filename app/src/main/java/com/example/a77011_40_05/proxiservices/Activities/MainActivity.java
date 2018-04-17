package com.example.a77011_40_05.proxiservices.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.a77011_40_05.proxiservices.Entities.CategoriesPrestations;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.App;
import com.example.a77011_40_05.proxiservices.Utils.AsyncCallWS;
import com.example.a77011_40_05.proxiservices.Utils.CallAsyncTask;
import com.example.a77011_40_05.proxiservices.Utils.Constants;
import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity {


    private static int SPLASH_TIME_OUT = 2000;
    App app;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (App) getApplication();
        loadCategoriesPrestations();
        context = this;
    }

    private void loadCategoriesPrestations(){
        AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "getAllCategoriesPrestations.php", new AsyncCallWS.OnCallBackAsyncTask() {
            @Override
            public void onResultCallBack(String result) {
                if(!result.isEmpty()){
                    Gson gson = new Gson();
                    try{
                        app.setCategoriesPrestations(gson.fromJson(result,CategoriesPrestations.class));
                        goToHome();
                    }catch (Exception e){
                        Log.e("[ERROR]",e.toString());
                        Log.e("[ERROR]","For result "+result);
                    }
                }else{
                    //TODO Gerer la non-reception des données
                }
            }
        });
        asyncCallWS.execute();
    }

    private void goToHome(){
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

    private void loadUsers(){
        //Ancienne méthodes...
        /*try{
            CallAsyncTask callAsyncTask = new CallAsyncTask(Constants._URL_WEBSERVICE+"getAllUsers.php",new String[]{},context);
            //CallAsyncTask callAsyncTask = new CallAsyncTask(_URL_WEBSERVICE+"loadusers.php",new String[]{},context);
            callAsyncTask.execute();
        }catch (Exception e){
            Toast.makeText(this,"Connexion impossible", Toast.LENGTH_LONG).show();
        }*/
    }

 }
