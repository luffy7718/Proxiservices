package com.example.a77011_40_05.proxiservices.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by 77011-40-05 on 12/03/2018.
 */

public class CallAsyncTask extends AsyncTask<String,Integer,String> {

    private String urlCible = "";
    private String[] dataModel = new String[]{};
    private OnAsyncTaskListner mCallBack;
    ProgressDialog progress;


    public CallAsyncTask(String urlCible, String[] dataModel, Context context) {
        this.urlCible = urlCible;
        this.dataModel = dataModel;
        this.mCallBack = (OnAsyncTaskListner) context;

    }

    public CallAsyncTask(String urlCible, String[] dataModel, OnAsyncTaskListner callBack) {
        this.urlCible = urlCible;
        this.dataModel = dataModel;
        this.mCallBack = callBack;

    }

    public interface OnAsyncTaskListner{
        void onResultGet(String result);
    }

    public void useProgressDialog(Context context){
        progress = new ProgressDialog(context);
        progress.setMessage("Veuillez patienter...");
        progress.setTitle("Chargement");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(progress != null){
            progress.show();
        }
    }



    @Override
    protected String doInBackground(String... params) {

        String result="";
        HttpURLConnection httpURLConnection=null;
        HashMap<String,String> postDataParams;

        //Functions.emulateTime(SPLASH_TIME_OUT);

        try{
            //Créer un instance de la class URL mode GET
            URL url = new URL(urlCible);

            //Ouvrir une connexion
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Accept-Charset","UTF-8");
            postDataParams = new HashMap<>();
            //postDataParams.put("datanom",params[0].toString());
            if(dataModel.length == params.length){
                for(int i=0;i<dataModel.length;i++){
                    postDataParams.put(dataModel[i].toString(),params[i].toString());
                }
            }else{
                Log.e("[ERROR]","Data Model doesn't match");
            }

            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            writer.write(Functions.getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            //Récupération du message retour du service Web
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream())
            );
            String line;
            while((line=bufferedReader.readLine())!=null){
                result += line;
            }



        }catch(Exception e){
            Log.e("[ERROR]",e.toString());
            try {
                throw (e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.e("[RESULTAT]",result);
        if(progress !=null){
            progress.dismiss();
        }

        mCallBack.onResultGet(result);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        Exception e = new Exception("Connection cancelled");
        try {
            throw(e);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}

