package com.example.a77011_40_05.proxiservices.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.AsyncCallWS;
import com.example.a77011_40_05.proxiservices.Utils.Constants;


public class RegisterActivity extends AppCompatActivity {

    EditText txtRegisterName, txtRegisterFirstName, txtRegisterLogin, txtRegisterpwd;
    ViewSwitcher vswRegister;
    Context context;
    Button btnRegisterValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_create);
        context = this;

        txtRegisterName = findViewById(R.id.txtRegister_name);
        txtRegisterFirstName = findViewById(R.id.txtRegister_firstName);
        txtRegisterLogin = findViewById(R.id.txtRegister_login);
        txtRegisterpwd = findViewById(R.id.txtRegister_pwd);
        btnRegisterValidate = findViewById(R.id.btnRegister_validate);
        vswRegister = findViewById(R.id.vswRegister);

        btnRegisterValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = txtRegisterName.getText().toString();
                String firstname = txtRegisterFirstName.getText().toString();
                String login = txtRegisterLogin.getText().toString();
                String password = txtRegisterpwd.getText().toString();

                if(!name.isEmpty() && !firstname.isEmpty() && !login.isEmpty() && !password.isEmpty()){
                    vswRegister.showNext();
                    AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "addUser.php", new AsyncCallWS.OnCallBackAsyncTask() {
                        @Override
                        public void onResultCallBack(String result) {
                            onResultGet(result);
                        }
                    });
                    asyncCallWS.addParam("name",name);
                    asyncCallWS.addParam("firstname",firstname);
                    asyncCallWS.addParam("login",login);
                    asyncCallWS.addParam("password",password);
                    asyncCallWS.execute();
                }

            }
        });
    }

    public void onResultGet(String result) {
        Log.e("[DEBUG]",result);
        int idUser = 0;
        try{
            idUser = Integer.parseInt(result);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }

        String msg;
        switch (idUser){
            case 0:
                Toast.makeText(context,"Erreur, veuillez recommencer.", Toast.LENGTH_LONG).show();
                vswRegister.showPrevious();
                break;
            case -1:
                Toast.makeText(context,"Login déjà existant.", Toast.LENGTH_LONG).show();
                vswRegister.showPrevious();
                break;
            default:
                Toast.makeText(context,"Votre compte a bien été créé.", Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }
}
