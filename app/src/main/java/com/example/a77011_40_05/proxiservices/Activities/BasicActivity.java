package com.example.a77011_40_05.proxiservices.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.GenericAlertDialog;

public class BasicActivity extends AppCompatActivity {

    Context context;
    EditText txtImgName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        context = this;



        Button btnCallAlertDialog = findViewById(R.id.btnCallAlertDialog);
        btnCallAlertDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View container = LayoutInflater.from(context).inflate(R.layout.dialog_take_picture,null);

                txtImgName = container.findViewById(R.id.txtImgName);
                ImageView imageView = container.findViewById(R.id.imgTakePicture);
                imageView.setImageResource(R.drawable.logo_jardinage);

                GenericAlertDialog genericAlertDialog = new GenericAlertDialog(BasicActivity.this, "Toto", container,
                        new GenericAlertDialog.CallGenericAlertDialog() {
                            @Override
                            public void onValidate() {
                                Toast.makeText(context,"Validate: "+txtImgName.getText().toString(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}
