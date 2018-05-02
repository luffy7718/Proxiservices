package com.example.a77011_40_05.proxiservices.Fragments;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.a77011_40_05.proxiservices.Activities.HomeActivity;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.AsyncCallWS;
import com.example.a77011_40_05.proxiservices.Utils.Constants;
import com.example.a77011_40_05.proxiservices.Utils.DataParser;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static android.content.Context.LOCATION_SERVICE;


public class MapsAddFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private GoogleMap Maps;
    double latitude = 48.866667;
    double longitude = 2.333333;
    Location location;
    Context context;
    MapView mapView;
    LatLng latLngfrom;
    LatLng latLngto;
    boolean isFrom=true;
    Button btnClearRoute;
    Button btnTraceRoute;
    PolylineOptions polylineOptions;
    Activity activity;
    Button btnReturn;

    private OnFragmentInteractionListener mListener;

    public MapsAddFragment() {
        // Required empty public constructor
    }

    public static MapsAddFragment newInstance(int zoom, List<Location> locations) {

        MapsAddFragment fragment = new MapsAddFragment();


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
        View view = inflater.inflate(R.layout.fragment_maps_add, container, false);

        mapView = (MapView) view.findViewById(R.id.mapView);
        btnClearRoute=(Button) view.findViewById(R.id.btnClearRoute);
        btnTraceRoute=(Button) view.findViewById(R.id.btnTraceRoute);
        //progress.setMin(MinValue); à partir de l'api 26
        mapView.onCreate(savedInstanceState);
        mapView.onResume(); //needed to get the map to display immediately

        //gets to googlemap from the mapview and does initialization stuff
        mapView.getMapAsync(this);


        btnTraceRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadJsonRoute();
            }
        });

        btnClearRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Maps.clear();

                latLngfrom=null;
                latLngto=null;
                isFrom=true;
            }
        });

        btnReturn=(Button)view.findViewById(R.id.btnReturn);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                HomeActivity home = (HomeActivity) activity;
                Bundle params = new Bundle();
                   /* Gson gson=new Gson();
                    String json=gson.toJson(prestation.getClass());*/
                home.changeFragment(Constants._FRAG_HOME, params);



            }
        });
        FloatingActionButton fbSave = view.findViewById(R.id.fbSave);
        fbSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveJsonRoute();
            }
        });

        return view;
    }

    private void loadJsonRoute()
    {
        try {
            String url = getUrl(latLngfrom, latLngto);

            AsyncCallWS asyncCallWS = new AsyncCallWS(url, new AsyncCallWS.OnCallBackAsyncTask() {
                @Override
                public void onResultCallBack(String result) {
                    ParserTask parserTask=new ParserTask();
                    parserTask.execute(result);

                    Log.e("TAG", result);
                }
            });
            asyncCallWS.execute();
        }catch (Exception e)
        {
            Log.e("Tag",e.getMessage());
        }
    }

    private void saveJsonRoute()
    {
        try {
            String url = getUrl(latLngfrom, latLngto);

            AsyncCallWS asyncCallWS = new AsyncCallWS(url, new AsyncCallWS.OnCallBackAsyncTask() {
                @Override
                public void onResultCallBack(String result) {
                    ParserTask parserTask = new ParserTask();
                    parserTask.saveGpx(result);

                    Log.e("TAG", result);
                }
            });
            asyncCallWS.execute();
        }catch (Exception e)
        {
            Log.e("Tag",e.getMessage());
        }
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

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            this.context = activity.getBaseContext();
        }
        this.activity = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onMapReady( final GoogleMap googleMap) {

        Maps = googleMap;
        Maps.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Maps.setMyLocationEnabled(true);

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
            Maps.addMarker(new MarkerOptions().position(paris).title("Coucou, Je suis Ici"));

            Maps.moveCamera(CameraUpdateFactory.newLatLng(paris));
            Maps.moveCamera(CameraUpdateFactory.newLatLngZoom(paris, 15));//min:2, max:21


            // Setting a click event handler for the map
            Maps.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                @Override
                public void onMapClick(LatLng latLng) {
                    if(isFrom)
                    {
                        latLngfrom=latLng;
                        Maps.addMarker(new MarkerOptions().position(latLng).title("Depart:"+latLng.latitude+" : "+latLng.longitude));

                        //markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                        isFrom=false;
                    }
                    else
                    {
                        latLngto=latLng;
                        Maps.addMarker(new MarkerOptions().position(latLng).title("Arrivé"+latLng.latitude+" : "+latLng.longitude));
                        //markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                        isFrom=true;
                    }




                }
            });





        }
    }





    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }






    @Override
    public void onLocationChanged(Location location) {
        Maps.clear();//effacer les marqueurs précédents
        LatLng mycoords = new LatLng(location.getLatitude(), location.getLongitude());
        Maps.addMarker(new MarkerOptions().position(mycoords).title("Coucou, Je suis Ici"));
        Maps.moveCamera(CameraUpdateFactory.newLatLngZoom(mycoords, 20));//min:2, max:21
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

    private String getUrl(LatLng from,LatLng to)
    {
        String str_origin="origin="+from.latitude+","+from.longitude;
        String str_dest="destination="+to.latitude+","+to.longitude;
        String sensor="sensor=false";
        String mode="mode=bicycling";
        //driving
        //walking
        //bicycling
        //transit


        //building the parameters to the web service
        String parameters=str_origin+"&"+str_dest+"&"+sensor+"&"+mode;
        //output format
        String output="json";


        //building the url to webservice

        String url="http://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
        return url;
    }


    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask",jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask","Executing routes");
                Log.d("ParserTask",routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask",e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                polylineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                int path_size=path.size();
                for (int j = 0; j < path_size; j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                polylineOptions.addAll(points);
                polylineOptions.width(10);
                polylineOptions.color(Color.RED);

                Log.d("onPostExecute","onPostExecute lineoptions decoded");
                Log.e("tag","points=" + points.size());
                Log.e("tag","lineOptions=" + polylineOptions.getPoints().size());

            }

            // Drawing polyline in the Google Map for the i-th route
            if(polylineOptions != null) {
                Maps.addPolyline(polylineOptions);
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }
        }

        public void saveGpx(String... jsonData){
            try {
                JSONObject jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask",jsonData[0].toString());
                DataParser parser = new DataParser();

                List<List<HashMap<String, String>>> routes = parser.parse(jObject);
                ArrayList<LatLng> points;

                int len = routes.size();
                if(len > 0){
                    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                    Document document = documentBuilder.newDocument();

                    Element gpx = document.createElement("gpx");
                    document.appendChild(gpx);

                    Element trk = document.createElement("trk");
                    trk.appendChild(document.createTextNode("Rita"));
                    gpx.appendChild(trk);

                    Element name = document.createElement("name");
                    name.appendChild(document.createTextNode("Toto"));
                    trk.appendChild(name);

                    Element trkseg = document.createElement("trkseg");
                    trk.appendChild(trkseg);

                    for (int i = 0; i < len; i++) {
                        List<HashMap<String, String>> path = routes.get(i);
                        int path_len = path.size();
                        for (int j = 0; j < path_len; j++) {
                            HashMap<String, String> point = path.get(j);

                            Element trkpt = document.createElement("trkpt");
                            //name.appendChild(document.createTextNode("Toto"));
                            trkpt.setAttribute("lat", point.get("lat"));
                            trkpt.setAttribute("lon", point.get("lng"));

                            trkseg.appendChild(trkpt);
                        }
                    }

                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(document);

                    File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                            "proxiservices.gpx");
                    StreamResult result = new StreamResult(path);
                    transformer.transform(source, result);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
