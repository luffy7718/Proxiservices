package com.example.a77011_40_05.proxiservices.Fragments;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a77011_40_05.proxiservices.Adapters.PhotoAdapter;
import com.example.a77011_40_05.proxiservices.Entities.Photos;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.CallAsyncTask;
import com.example.a77011_40_05.proxiservices.Utils.Functions;
import com.example.a77011_40_05.proxiservices.Utils.GenericAlertDialog;
import com.example.a77011_40_05.proxiservices.Utils.Session;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;
import static com.example.a77011_40_05.proxiservices.Utils.Constants._URL_WEBSERVICE;


public class AccountGalleryFragment extends Fragment {

    private final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    Context context;
    RecyclerView rvwAccountGallery;
    Photos photos;
    Uri uri;
    PhotoAdapter photoAdapter;
    String path;

    Button btnTakePicture;

    //ALERT DIALOG
    ImageView imgTakePicture;
    TextView txtImgName;
    Bitmap currentPhoto;

    public AccountGalleryFragment() {
        // Required empty public constructor
    }

    public static AccountGalleryFragment newInstance() {
        AccountGalleryFragment fragment = new AccountGalleryFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_gallery, container, false);
        rvwAccountGallery = view.findViewById(R.id.rvwAccountGallery);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);

        RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(context,3);

        rvwAccountGallery.setLayoutManager(layoutManager2);
        rvwAccountGallery.setItemAnimator(new DefaultItemAnimator());
        photoAdapter = new PhotoAdapter(context);
        rvwAccountGallery.setAdapter(photoAdapter);

        btnTakePicture = view.findViewById(R.id.btnTakePicture);
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
        return view;
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            context = activity.getBaseContext();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {


        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            Bitmap photo = null;
            if(intent != null && intent.hasExtra("data"))
            {
                Bundle extras = intent.getExtras();
                photo = (Bitmap) extras.get("data");
                //imgProfilePic.setImageBitmap(photo);
                showTakePictureDialog(photo);

            }

        }
    }

    private void oldShowTakePictureDialog(final Bitmap bmpPhoto){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Ma photo");

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View container = inflater.inflate(R.layout.dialog_take_picture,null);

        ImageView imgTakePicture =container.findViewById(R.id.imgTakePicture);
        final TextView txtImgName =  container.findViewById(R.id.txtImgName);
        imgTakePicture.setImageBitmap(bmpPhoto);

        alertDialog.setView(container);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String txtPhoto = txtImgName.getText().toString();
                if(!txtPhoto.isEmpty()){
                    path = Functions.saveToInternalStorage(bmpPhoto,txtPhoto,"img",context);
                    photoAdapter.addPhoto(txtPhoto,path);
                }
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

    private void showTakePictureDialog(final Bitmap photo){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_take_picture,null);
        imgTakePicture =view.findViewById(R.id.imgTakePicture);
        txtImgName =  view.findViewById(R.id.txtImgName);
        imgTakePicture.setImageBitmap(photo);


        new GenericAlertDialog(getActivity(), "Ma photo", view,
                new GenericAlertDialog.CallGenericAlertDialog() {
                    @Override
                    public void onValidate() {
                        String txtPhoto = txtImgName.getText().toString();
                        if(!txtPhoto.isEmpty()){
                            path = Functions.saveToInternalStorage(photo,txtPhoto,"img",context);
                            int idPhotoPhone = photoAdapter.addPhoto(txtPhoto,path);
                            String photoString = castPhotoToString(photo);

                            int idUser = Session.getMyUser().getIdUser();
                            String url = _URL_WEBSERVICE+"addPhoto.php";
                            String[] dataModel = new String[]{"idPhotoPhone","idUser","name","photoString"};
                            CallAsyncTask callAsyncTask = new CallAsyncTask(url, dataModel, new CallAsyncTask.OnAsyncTaskListner() {
                                @Override
                                public void onResultGet(String result) {
                                    Toast.makeText(context,"Result: "+result, Toast.LENGTH_LONG).show();
                                    Log.e("[DEBUG]","Result: "+result);
                                }
                            });
                            callAsyncTask.useProgressDialog(getActivity());
                            callAsyncTask.execute(""+idPhotoPhone,""+idUser,txtPhoto,photoString);
                        }
                    }
                });
    }

    private String castPhotoToString(Bitmap bitmap){
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,bao);
        byte[] ba = bao.toByteArray();
        return Base64.encodeToString(ba, Base64.NO_WRAP);
    }
}