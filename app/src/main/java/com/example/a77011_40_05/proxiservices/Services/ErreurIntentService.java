package com.example.a77011_40_05.proxiservices.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.a77011_40_05.proxiservices.Entities.Erreur;
import com.example.a77011_40_05.proxiservices.Utils.Constants;
import com.google.gson.Gson;

import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ErreurIntentService extends IntentService {

    private static final String ACTION_ERREUR = "android.com.proxiservices.Services.action.FOO";
    private static final String EXTRA_ERREUR = "android.com.proxiservices.Services.extra.PARAM1";

    private static Gson gson;

    public ErreurIntentService() {
        super("ErreurIntentService");

        gson = new Gson();
    }

    public static void startActionErreur(Context context, Erreur erreur) {

        Intent intent = new Intent(context, ErreurIntentService.class);
        intent.setAction(ACTION_ERREUR);
        intent.putExtra(EXTRA_ERREUR,gson.toJson(erreur));
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            if (intent != null) {
                final String action = intent.getAction();

                if (ACTION_ERREUR.equals(action)) {

                    String erreurGson = intent.getStringExtra(EXTRA_ERREUR);
                    String url = Constants._URL_WEBSERVICE + "addErreur.php";
                    Erreur erreur = gson.fromJson(erreurGson, Erreur.class);

                    HashMap<String, String> params = new HashMap<>();
                    params.put("className", erreur.getClassName());
                    handleActionErreur(url, params);
                }
            }
        }
        catch (Exception e)
        {
            Log.e("Tag", e.getMessage());
        }
    }

    private void handleActionErreur(String url, HashMap<String, String> params) {

        OkHttpClient client = new OkHttpClient();

        //Méthode POST
        FormBody.Builder formBuilder = new FormBody.Builder();

        for (HashMap.Entry<String, String> entry : params.entrySet()) {
            formBuilder.add(entry.getKey(), entry.getValue());
        }

        RequestBody requestBody = formBuilder.build();

        Request request = new Request.Builder()
                //.header("Authorization", tokenEncode)
                .method("POST", requestBody)
                .url(url)
                .build();

        try {
            client.newCall(request).execute();

        } catch (Exception e) {
            Log.e("Tag", e.getMessage());
        }

    }
}
