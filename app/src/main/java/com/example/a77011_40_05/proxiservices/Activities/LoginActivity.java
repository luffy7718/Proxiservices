package com.example.a77011_40_05.proxiservices.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.a77011_40_05.proxiservices.Entities.User;
import com.example.a77011_40_05.proxiservices.Entities.Users;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.AsyncCallWS;
import com.example.a77011_40_05.proxiservices.Utils.CallAsyncTask;
import com.example.a77011_40_05.proxiservices.Utils.Constants;
import com.example.a77011_40_05.proxiservices.Utils.Session;
import com.google.gson.Gson;


public class LoginActivity extends AppCompatActivity{

    Intent intent;
    ViewSwitcher vswLogin;
    TextView lblLoginCurrentAction;
    TextInputEditText txtLoginName;
    TextInputEditText txtLoginPwd;
    TextView lblLoginForgotten;
    TextView lblLoginRegister;
    Context context;
    int requestType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        intent = new Intent();


        txtLoginName = findViewById(R.id.txtLogin_name);
        txtLoginPwd = findViewById(R.id.txtLogin_pwd);

        lblLoginForgotten = findViewById(R.id.lblLogin_forgotten);
        lblLoginRegister = findViewById(R.id.lblLogin_register);
        lblLoginForgotten.setPaintFlags(lblLoginForgotten.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        lblLoginRegister.setPaintFlags(lblLoginRegister.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Button btnLoginValidate = findViewById(R.id.btnLogin_validate);
        Button btnLoginBack = findViewById(R.id.btnLogin_back);
        vswLogin = findViewById(R.id.vswLogin);
        lblLoginCurrentAction = findViewById(R.id.lblLogin_currentAction);

        lblLoginForgotten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPasswordForgottenDialog();
            }
        });

        lblLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLoginValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = txtLoginName.getText().toString();
                String password = txtLoginPwd.getText().toString();

                if(!login.isEmpty() && !password.isEmpty()){
                    vswLogin.showNext();
                    lblLoginCurrentAction.setText("Connexion ...");
                    AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "login.php", new AsyncCallWS.OnCallBackAsyncTask() {
                        @Override
                        public void onResultCallBack(String result) {
                            Gson gson = new Gson();
                            Users users = gson.fromJson(result,Users.class);
                            User user = users.get(0);
                            Session.setMyUser(user);
                            Session.setConnectionChecked(true);
                            intent.putExtra("RETURN","VALIDATE");
                            String[] data = new String[]{user.getNom(),user.getPrenom()};
                            intent.putExtra("DATA",data);
                            setResult(Constants._CODE_LOGIN,intent);
                            finish();//finishing activity
                        }
                    });
                    asyncCallWS.addParam("login",login);
                    asyncCallWS.addParam("password",password);
                    asyncCallWS.execute();
                    /*requestType = Constants._LOGIN_REQUEST;
                    lblLoginCurrentAction.setText("Connexion ...");
                    String url = Constants._URL_WEBSERVICE+"login.php";
                    String[] dataModel = new String[]{"login","password"};
                    CallAsyncTask callAsyncTask = new CallAsyncTask(url,dataModel,context);
                    callAsyncTask.useProgressDialog(context);
                    callAsyncTask.execute(login,password);*/
                }
            }
        });

        btnLoginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();//finishing activity
            }
        });
    }

    private void loginRequestResult(String result){
        //Log.e("[DEBUG]",result);
        Gson gson = new Gson();
        Users users = gson.fromJson(result,Users.class);
        User user = users.get(0);
        Session.setMyUser(user);
        Session.setConnectionChecked(true);
        intent.putExtra("RETURN","VALIDATE");
        String[] data = new String[]{user.getNom(),user.getPrenom()};
        intent.putExtra("DATA",data);
        setResult(Constants._CODE_LOGIN,intent);
        finish();//finishing activity*/
    }

    private void passwordRequestResult(String result){
        Log.e("[DEBUG]",result);
        Toast.makeText(context,"MDP: "+result, Toast.LENGTH_LONG).show();
        vswLogin.showPrevious();
    }

    private void showPasswordForgottenDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Mot de passe oublié");
        //alertDialog.setMessage(utilisateur.getNom());

        LayoutInflater inflater = LayoutInflater.from(context);
        View container = inflater.inflate(R.layout.dialog_forgotten_password,null);

        final TextView txtReminder =  container.findViewById(R.id.txtReminder);

        alertDialog.setView(container);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                vswLogin.showNext();
                String login = txtReminder.getText().toString();
                if(login.isEmpty()){
                    Toast.makeText(context,"Login manquant.",Toast.LENGTH_LONG).show();
                    return;
                }
                AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "lostPassword.php", new AsyncCallWS.OnCallBackAsyncTask() {
                    @Override
                    public void onResultCallBack(String result) {
                        Toast.makeText(context,"MDP: "+result, Toast.LENGTH_LONG).show();
                        vswLogin.showPrevious();
                    }
                });
                asyncCallWS.addParam("login",login);
                asyncCallWS.execute();

                /*requestType = Constants._PASSWORD_REQUEST;
                lblLoginCurrentAction.setText("Récupération du mot de passe ...");

                //String url = _URL_WEBSERVICE+"passwordReminder.php";
                String url = Constants._URL_WEBSERVICE+"lostpassword.php";
                String[] dataModel = new String[]{"login"};
                CallAsyncTask callAsyncTask = new CallAsyncTask(url,dataModel,context);
                callAsyncTask.execute(login);*/
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
}
