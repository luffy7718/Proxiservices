package com.example.a77011_40_05.proxiservices.Utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;

import com.example.a77011_40_05.proxiservices.Entities.Erreur;
import com.example.a77011_40_05.proxiservices.Services.ErreurIntentService;

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

    public static boolean isConnectionAvailable(Context context) {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();

            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    public static void addErreur(Erreur erreur, Context context) {

        if (isConnectionAvailable(context)) {
            try {
                ErreurIntentService.startActionErreur(context, erreur);

            } catch (Exception ex) {

            }
        } else {
            App.addErreur(erreur,context);
        }
    }


    //ANIMATION
    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void rotate(View view, float from, float to, int duration){
        RotateAnimation rotateAnimation = new RotateAnimation(from, to, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setRepeatCount(0);
        rotateAnimation.setDuration(duration);
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }


    //CONVERTER

    public static int dpToPixels(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
}
