package com.example.a77011_40_05.proxiservices.Utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 77011-40-05 on 15/03/2018.
 */

public class Functions {

    public static void emulateTime(int time){
        try{
            Thread.sleep(time);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static String getPostDataString(HashMap<String,String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String,String> entry : params.entrySet()){
            if(first){
                first = false;
            }else{
                result.append("&");
            }

            result.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
        }

        return result.toString();
    }

    public static String saveToInternalStorage(Bitmap photo, String imageName, String directoryName, Context context){

        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/directoryName
        File directory = cw.getDir(directoryName, Context.MODE_PRIVATE);
        // Create imageDir
        File path=new File(directory,imageName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            // Use the compress method on the BitMap object to write image to the OutputStream
            int size = photo.getByteCount();
            int quality = 100;

            if(size > 1000)
            {
                quality = 80;
            }

            photo.compress(Bitmap.CompressFormat.JPEG, quality, fos);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                Log.e("Tag",e.getMessage());
            }
        }
        return directory.getAbsolutePath();
    }

    public static Bitmap loadFromInternalStorage(String path, String photoName)
    {
        Bitmap bitmap=null;

        try {
            File f=new File(path, photoName);
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (Exception e)
        {
            Log.e("Tag",e.getMessage());
        }
        return bitmap;
    }

    public static void addPreferenceString(Context context, String key, String value){
        addPreferenceString(context, key,value, true);
    }

    public static void addPreferenceString(Context context, String key, String value, boolean allowUpdate){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCE,MODE_PRIVATE);

        //Ouvre le fichier en mode édition
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //On enregistre les données dans le fichier
        if(!sharedPreferences.contains(key) || allowUpdate){
            editor.putString(key, value);
        }
        //On referme le fichier
        editor.commit();
    }

    public static String getPreferenceString(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCE,MODE_PRIVATE);

        String value = "";
        if(sharedPreferences.contains(key)){
            value = sharedPreferences.getString(key,"");
        }

        return value;
    }

    public static int getResourceId(Context context, String pVariableName, String pResourcename){
        try{
            return  context.getResources().getIdentifier(pVariableName,pResourcename,context.getPackageName());
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
}
