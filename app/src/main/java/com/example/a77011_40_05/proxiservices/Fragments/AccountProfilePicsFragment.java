package com.example.a77011_40_05.proxiservices.Fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a77011_40_05.proxiservices.Activities.HomeActivity;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.Functions;
import com.example.a77011_40_05.proxiservices.Utils.Session;

import static android.app.Activity.RESULT_OK;
import static com.example.a77011_40_05.proxiservices.Utils.Constants._FRAG_ACCOUNT;


public class AccountProfilePicsFragment extends Fragment {

    private final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    Context context;
    Uri uri;

    ImageView imgProfilePic;
    String path =null;

    public AccountProfilePicsFragment() {
        // Required empty public constructor
    }

    public static AccountProfilePicsFragment newInstance() {
        AccountProfilePicsFragment fragment = new AccountProfilePicsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_profile_pics, container, false);



        imgProfilePic = view.findViewById(R.id.imgProfilePic);
        Button btnTakePicture = view.findViewById(R.id.btnTakePicture);
        Button btnSaveImg = view.findViewById(R.id.btnSaveImg);

        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    // start the image capture Intent
                    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

                } else {
                    Toast.makeText(context, "Camera not supported", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnSaveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(path != null){
                    Session.getMyUser().setProfilePic(path);
                    HomeActivity home = (HomeActivity) getActivity();
                    home.userHasChange(Session.getMyUser());
                    home.changeFragment(_FRAG_ACCOUNT,null);
                }
            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {


        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            Bitmap photo = null;
            if(intent != null && intent.hasExtra("data"))
            {
                Bundle extras = intent.getExtras();
                photo = (Bitmap) extras.get("data");
                imgProfilePic.setImageBitmap(photo);
            }

            //Imageprev.setImageURI(uri);


            //TODO le nom du fichier est en static, à changer
            path = Functions.saveToInternalStorage(photo,"profile.jpg","img",context);

            //Bitmap photo2 = Functions.loadFromInternalStorage(path,"test.jpg");

        }
    }

}
