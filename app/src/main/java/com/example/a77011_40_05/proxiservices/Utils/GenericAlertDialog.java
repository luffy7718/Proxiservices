package com.example.a77011_40_05.proxiservices.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by 77011-40-05 on 21/03/2018.
 */

public class GenericAlertDialog {

    private CallGenericAlertDialog callBack;

    public GenericAlertDialog(Activity activity, String title, View view, CallGenericAlertDialog call){

        this.callBack = call;

        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(title);

        alertDialog.setView(view);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callBack.onValidate();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Fermer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public interface CallGenericAlertDialog{
        void onValidate();
    }


}
