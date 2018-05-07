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
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a77011_40_05.proxiservices.Activities.HomeActivity;
import com.example.a77011_40_05.proxiservices.Adapters.MyPagerAdapter;
import com.example.a77011_40_05.proxiservices.Entities.CategoriesPrestations;
import com.example.a77011_40_05.proxiservices.Entities.Prestation;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.App;
import com.example.a77011_40_05.proxiservices.Utils.AsyncCallWS;
import com.example.a77011_40_05.proxiservices.Utils.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;


public class MapsSearchFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private GoogleMap Maps;
    double latitude = 48.866667;
    double longitude = 2.333333;
    double latitudePrestations = 0;
    double longitudePrestations = 0;
    Location location;
    private Circle circle;
    Context context;
    MapView mapView;
    AppCompatSeekBar progressRadius;
    TextView radiusText;
    int MaxValue = 40;
    Spinner spinCategory;
    //FILTERS
    int idCategoryPrestation = -1;
    LatLng mycoords;
    LatLng mycoordsPrestations;

    Activity activity;


    public MapsSearchFragment() {
        // Required empty public constructor
    }

    public static MapsSearchFragment newInstance(Bundle args) {


        MapsSearchFragment fragment = new MapsSearchFragment();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().containsKey("latitude") && getArguments().containsKey("longitude")) {
                latitudePrestations = getArguments().getDouble("latitude");
                longitudePrestations = getArguments().getDouble("longitude");
            }
        }
        context = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_maps_search, container, false);

        Log.e(Constants._TAG_LOG, "MapsSearchFragment");

        mapView = (MapView) view.findViewById(R.id.mapView);
        progressRadius = (AppCompatSeekBar) view.findViewById(R.id.progress);


        progressRadius.setMax(MaxValue - 1);
        //progress.setMin(MinValue); à partir de l'api 26
        mapView.onCreate(savedInstanceState);
        mapView.onResume(); //needed to get the map to display immediately
        radiusText = (TextView) view.findViewById(R.id.radius_text);

        //gets to googlemap from the mapview and does initialization stuff
        mapView.getMapAsync(this);
        spinCategory = (Spinner) view.findViewById(R.id.spinCategory);
        List<String> categories = new ArrayList<>();
        categories.add("Tous");
        CategoriesPrestations cps = App.getCategoriesPrestations();
        for (int i = 0; i < cps.size(); i++) {
            categories.add(cps.get(i).getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context, android.R.layout
                .simple_spinner_item, categories);

        // Drop down style will be listview with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        // attaching data adapter to spinner
        spinCategory.setAdapter(dataAdapter);

        if (idCategoryPrestation != -1) {
            spinCategory.setSelection(idCategoryPrestation);
        }

        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(context, "Selected",Toast.LENGTH_SHORT).show();
                idCategoryPrestation = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            context = activity.getBaseContext();
        }
        this.activity = activity;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {


        Maps = googleMap;
        Maps.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission
                .ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Maps.setMyLocationEnabled(true);

            LocationManager locationManager = (LocationManager) context.getSystemService
                    (LOCATION_SERVICE);

            // todo: à partir de l'api 23
            //LocationManager locationManager = (LocationManager)getContext().getSystemService
            // (LOCATION_SERVICE);


            String locationType = "";
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                locationType = LocationManager.GPS_PROVIDER;
            else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
                locationType = LocationManager.NETWORK_PROVIDER;
            else locationType = LocationManager.PASSIVE_PROVIDER;
            if (!locationType.isEmpty()) {
                Log.e(Constants._TAG_LOG, "locationType: " + locationType);
                // locationManager.requestLocationUpdates(locationType, MIN_TIME_UPDATES,
                // MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                location = locationManager.getLastKnownLocation(locationType);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                }


            }


            mycoords = new LatLng(latitude, longitude);
            mycoordsPrestations = new LatLng(latitudePrestations, longitudePrestations);

            //création d'un cercle

            final CircleOptions co = new CircleOptions();
            //mouvement de la caméra au centre de ma position
            Maps.moveCamera(CameraUpdateFactory.newLatLng(mycoords));
            //paramétre du cercle
            circle = Maps.addCircle(co.center(mycoords).radius(1000).fillColor(0x66aaaFFF));
            //ajout du marqueur
            Maps.addMarker(new MarkerOptions().position(mycoords).title("Coucou, Je suis Ici"));
            Maps.addMarker(new MarkerOptions().position(mycoordsPrestations).title("Coucou, la prestation est ici"));
            //mouvement de la caméra en fonction du lvl de zoom
            Maps.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    co.getCenter(), getZoomLevel(circle)));


            //utilisation du seekbar rayon
            progressRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                public void onProgressChanged(SeekBar seekBar, int progressRadius, boolean
                        fromUser) {

                    progressRadius++;

                    //changer le cercle grace au seekbar rayon
                    circle.setRadius(progressRadius);


                    //affichage du rayon en fonction du seekbar
                    radiusText.setText(String.valueOf((progressRadius)) + "Km");
                    //nettoyer la maps
                    Maps.clear();
                    Maps.addMarker(new MarkerOptions().position(mycoords).title("Coucou, Je suis Ici"));
                    Maps.addMarker(new MarkerOptions().position(mycoordsPrestations).title("Coucou, la prestation est ici"));

                    final CircleOptions circleoptions = new CircleOptions();


                    if (progressRadius > 1) {



                        circle = Maps.addCircle(circleoptions
                                .center(mycoords)
                                .radius(progressRadius *1000)// exemple:2*1000=2000m=2km
                                .strokeWidth(1)
                                .fillColor(0x66aaaFFF));
                        Maps.animateCamera(CameraUpdateFactory.newLatLngZoom(circleoptions.getCenter

                                (), getZoomLevel(circle)));

                    }
                    if (progressRadius == 1) {

                        circle = Maps.addCircle(circleoptions
                                .center(mycoords)
                                .radius(1000)
                                .strokeWidth(0)
                                .fillColor(0x66aaaFFF));
                        Maps.animateCamera(CameraUpdateFactory.newLatLngZoom(circleoptions.getCenter
                                (), getZoomLevel(circle)));

                    }
                }


                @Override
                public void onStartTrackingTouch(final SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(final SeekBar seekBar) {

                }
            });


        }

    }


    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }


    //methode pour gérer le lvl de zoom en fonction du cercle
    public int getZoomLevel(Circle circle) {

        int zoomLevel = 11;
        if (circle != null) {

            double radius = circle.getRadius() + circle.getRadius() / 2;
            double scale = radius / 500;
            zoomLevel = (int) (16 - Math.log(scale) / Math.log(2));
        }
        return zoomLevel;
    }


    @Override
    public void onLocationChanged(Location location) {

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


}
