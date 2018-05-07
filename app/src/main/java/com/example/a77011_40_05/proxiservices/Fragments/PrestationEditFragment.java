package com.example.a77011_40_05.proxiservices.Fragments;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a77011_40_05.proxiservices.Activities.HomeActivity;
import com.example.a77011_40_05.proxiservices.Adapters.PrestationAdapterAccount;
import com.example.a77011_40_05.proxiservices.Entities.CategoriesPrestations;
import com.example.a77011_40_05.proxiservices.Entities.Prestation;
import com.example.a77011_40_05.proxiservices.Entities.Prestations;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.App;
import com.example.a77011_40_05.proxiservices.Utils.AsyncCallWS;
import com.example.a77011_40_05.proxiservices.Utils.Constants;
import com.example.a77011_40_05.proxiservices.Utils.GenericAlertDialog;
import com.example.a77011_40_05.proxiservices.Utils.Session;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;


public class PrestationEditFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;

    //SETTINGS
    private Prestation prestation;
    private App app;
    private boolean isNew;
    private boolean isRequest;


    //VIEWS
    Spinner spinCategory;
    EditText txtDescription;
    ImageButton btnEdit;
    ImageButton btnCancel;
    ImageButton btnDelete;


    //MAPS
    private static long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5;//en metres
    private static long MIN_TIME_UPDATES = 1000 * 60 * 1;//en millisecondes (1 minutes)
    boolean checkGps;
    boolean checkNetwork;
    Context context;
    String typePostion;
    private MapView mapView;
    private Marker marker;
    Location location;
    LatLng position;
    double latitude = 48.866667;
    double longitude = 2.333333;


    private MapsSearchFragment.OnFragmentInteractionListener mListener;

    public PrestationEditFragment() {
        // Required empty public constructor
    }

    public static PrestationEditFragment newInstance(Bundle args) {
        PrestationEditFragment fragment = new PrestationEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if(getArguments().containsKey("prestation")){
                Gson gson = new Gson();
                String json = getArguments().getString("prestation");
                //Log.e(Constants._TAG_LOG,"Prestation Edit: "+json);
                prestation = gson.fromJson(json ,Prestation.class);
                isNew = false;
            }else if(getArguments().containsKey("isRequest")){
                isNew = true;
                prestation = new Prestation();
                isRequest =getArguments().getBoolean("isRequest");
            }
        }else{
            isNew = true;
            prestation = new Prestation();
            isRequest = true;
        }
        context = getActivity();
        app = (App) getActivity().getApplication();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_prestation_edit, container, false);

        spinCategory = view.findViewById(R.id.spinCategory);
        txtDescription = view.findViewById(R.id.txtDescription);
        btnEdit = view.findViewById(R.id.btnEdit);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnCancel = view.findViewById(R.id.btnCancel);
        spinCategory = (Spinner) view.findViewById(R.id.spinCategory);


        //Alimentation du spinner
        List<String> categories=new ArrayList<>();
        CategoriesPrestations cps = App.getCategoriesPrestations();
        for(int i= 0;i<cps.size();i++){
            categories.add(cps.get(i).getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,categories);
        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinCategory.setAdapter(dataAdapter);


        //Switch entre Add et Edit
        if(!isNew){
            txtDescription.setText(prestation.getDescription());
            spinCategory.setSelection(prestation.getIdCategoryPrestation()-1);
        }else{
            btnDelete.setVisibility(View.GONE);
            if(isRequest){
                txtDescription.setHint("Je recherche ...");
            }else{
                txtDescription.setHint("Je propose ...");
            }
        }


        //Button
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNew){
                    addPrestation();
                }else{
                    editPrestation();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GenericAlertDialog((HomeActivity) context, "Supprimer ?", null, new GenericAlertDialog.CallGenericAlertDialog() {
                    @Override
                    public void onValidate() {
                        removePrersation();
                    }
                });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) context).onBackPressed();
            }
        });

        mapView=(MapView)view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume(); //needed to get the map to display immediately

        //gets to googlemap from the mapview and does initialization stuff
        mapView.getMapAsync(this);


        return view;
    }



    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mMap.setMyLocationEnabled(true);

            LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

            // todo: à partir de l'api 23
            //LocationManager locationManager = (LocationManager)getContext().getSystemService(LOCATION_SERVICE);
            String locationType = "";
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                locationType = LocationManager.GPS_PROVIDER;
            }else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                locationType = LocationManager.NETWORK_PROVIDER;
            }else{
                locationType = LocationManager.PASSIVE_PROVIDER;
            }

            if (!locationType.isEmpty()) {
                // locationManager.requestLocationUpdates(locationType, MIN_TIME_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                location = locationManager.getLastKnownLocation(locationType);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }

            if(!isNew){
                latitude = prestation.getLatitude();
                longitude = prestation.getLongitude();
            }

            position = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(position).title(""));

            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));//min:2, max:21

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                @Override
                public void onMapClick(LatLng latLng) {
                    position = latLng;
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(position));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(position)
                            .zoom(15)
                            .build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 1500, null);
                }

            });

        }


    }

    @Override
    public void onLocationChanged(Location location) {
        mMap.clear();//effacer les marqueurs précédents

        LatLng mycoords = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(mycoords).title(""));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mycoords, 20));//min:2, max:21
        marker.setPosition(mycoords);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void addPrestation(){
        AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "addPrestation.php", new AsyncCallWS.OnCallBackAsyncTask() {
            @Override
            public void onResultCallBack(String result) {
                Log.e(Constants._TAG_LOG, "addPresation: "+result);
                if(!result.isEmpty()){
                    Gson gson = new Gson();
                    JsonObject json = gson.fromJson(result,JsonObject.class);
                    if(json.has("status")){
                        Log.e(Constants._TAG_LOG,"Has status: "+json.get("status"));
                        if(json.get("status").getAsString().equals("SUCCESS")){
                            Toast.makeText(context,"Enregisté !",Toast.LENGTH_LONG).show();
                            ((HomeActivity) context).onBackPressed();
                        }else{
                            String msg = json.get("msg").getAsString();
                            Toast.makeText(context,"ERREUR: "+msg,Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
        /*Log.e(Constants._TAG_LOG, "idCategoryPrestation: "+""+(spinCategory.getSelectedItemPosition()+1));
        Log.e(Constants._TAG_LOG, "idUser: "+""+ Session.getMyUser().getIdUser());
        Log.e(Constants._TAG_LOG, "description: "+txtDescription.getText().toString());
        Log.e(Constants._TAG_LOG, "latitude: "+""+ position.latitude);
        Log.e(Constants._TAG_LOG, "longitude: "+""+ position.longitude);*/
        asyncCallWS.addParam("idCategoryPrestation",""+ (spinCategory.getSelectedItemPosition()+1));
        asyncCallWS.addParam("idUser",""+ Session.getMyUser().getIdUser());
        asyncCallWS.addParam("description",txtDescription.getText().toString());
        asyncCallWS.addParam("latitude",""+ position.latitude);
        asyncCallWS.addParam("longitude",""+ position.longitude);
        if(isRequest){
            asyncCallWS.addParam("isRequest",""+1);
        }else{
            asyncCallWS.addParam("isRequest",""+0);
        }

        asyncCallWS.execute();
    }

    private void editPrestation(){
        AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "editPrestation.php", new AsyncCallWS.OnCallBackAsyncTask() {
            @Override
            public void onResultCallBack(String result) {
                Log.e(Constants._TAG_LOG, "editPresation: "+result);
                if(!result.isEmpty()){
                    Gson gson = new Gson();
                    JsonObject json = gson.fromJson(result,JsonObject.class);
                    if(json.has("status")){
                        Log.e(Constants._TAG_LOG,"Has status: "+json.get("status"));
                        if(json.get("status").getAsString().equals("SUCCESS")){
                            Toast.makeText(context,"Enregisté !",Toast.LENGTH_LONG).show();
                            ((HomeActivity) context).onBackPressed();
                        }else{
                            String msg = json.get("msg").getAsString();
                            Toast.makeText(context,"ERREUR: "+msg,Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
        /*Log.e(Constants._TAG_LOG, "idPrestation: "+""+ prestation.getIdPrestation());
        Log.e(Constants._TAG_LOG, "idCategoryPrestation: "+""+(spinCategory.getSelectedItemPosition()+1));
        Log.e(Constants._TAG_LOG, "idUser: "+""+ Session.getMyUser().getIdUser());
        Log.e(Constants._TAG_LOG, "description: "+txtDescription.getText().toString());
        Log.e(Constants._TAG_LOG, "latitude: "+""+ position.latitude);
        Log.e(Constants._TAG_LOG, "longitude: "+""+ position.longitude);*/
        asyncCallWS.addParam("idPrestation",""+ prestation.getIdPrestation());
        asyncCallWS.addParam("idCategoryPrestation",""+ (spinCategory.getSelectedItemPosition()+1));
        asyncCallWS.addParam("idUser",""+ Session.getMyUser().getIdUser());
        asyncCallWS.addParam("description",txtDescription.getText().toString());
        asyncCallWS.addParam("latitude",""+ position.latitude);
        asyncCallWS.addParam("longitude",""+ position.longitude);

        asyncCallWS.execute();
    }

    private void removePrersation(){
        AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "removePrestation.php", new AsyncCallWS.OnCallBackAsyncTask() {
            @Override
            public void onResultCallBack(String result) {
                Log.e(Constants._TAG_LOG, "editPresation: "+result);
                if(!result.isEmpty()){
                    Gson gson = new Gson();
                    JsonObject json = gson.fromJson(result,JsonObject.class);
                    if(json.has("status")){
                        Log.e(Constants._TAG_LOG,"Has status: "+json.get("status"));
                        if(json.get("status").getAsString().equals("SUCCESS")){
                            Toast.makeText(context,"Supprimé !",Toast.LENGTH_LONG).show();
                            ((HomeActivity) context).onBackPressed();
                        }else{
                            String msg = json.get("msg").getAsString();
                            Toast.makeText(context,"ERREUR: "+msg,Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
        //Log.e(Constants._TAG_LOG, "idPrestation: "+""+ prestation.getIdPrestation());
        asyncCallWS.addParam("idPrestation",""+ prestation.getIdPrestation());

        asyncCallWS.execute();
    }

}