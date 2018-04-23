package com.example.a77011_40_05.proxiservices.Fragments;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.a77011_40_05.proxiservices.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;


public class MapsFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;

    private static long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5;//en metres
    private static long MIN_TIME_UPDATES = 1000 * 60 * 1;//en millisecondes (1 minutes)
    boolean checkGps;
    boolean checkNetwork;
    Context context;
    String typePostion;
    private MapView mapView;
    private Marker marker;
    Location location;
    double latitude = 48.866667;
    double longitude = 2.333333;


    private MapsSearchServicesFragment.OnFragmentInteractionListener mListener;

    public MapsFragment() {
        // Required empty public constructor
    }

    public static MapsFragment newInstance(int zoom, List<Location> locations) {
        MapsFragment fragment = new MapsFragment();


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
        View view=inflater.inflate(R.layout.fragment_maps, container, false);

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

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            this.context = activity.getBaseContext();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                locationType = LocationManager.GPS_PROVIDER;
            else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
                locationType = LocationManager.NETWORK_PROVIDER;
            else locationType = LocationManager.PASSIVE_PROVIDER;

            if (!locationType.isEmpty()) {
                // locationManager.requestLocationUpdates(locationType, MIN_TIME_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                location = locationManager.getLastKnownLocation(locationType);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }
            LatLng paris = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(paris).title("Coucou, Je suis Ici"));

            mMap.moveCamera(CameraUpdateFactory.newLatLng(paris));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paris, 15));//min:2, max:21

        }


    }

    @Override
    public void onLocationChanged(Location location) {
        mMap.clear();//effacer les marqueurs précédents

        LatLng mycoords = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(mycoords).title("Coucou, Je suis Ici"));

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


}