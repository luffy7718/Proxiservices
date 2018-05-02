package com.example.a77011_40_05.proxiservices.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a77011_40_05.proxiservices.Entities.User;
import com.example.a77011_40_05.proxiservices.Fragments.AccountFragment;
import com.example.a77011_40_05.proxiservices.Fragments.AccountGalleryFragment;
import com.example.a77011_40_05.proxiservices.Fragments.AccountProfilePicsFragment;
import com.example.a77011_40_05.proxiservices.Fragments.HomeFragment;
import com.example.a77011_40_05.proxiservices.Fragments.MapsAddFragment;
import com.example.a77011_40_05.proxiservices.Fragments.MapsFragment;
import com.example.a77011_40_05.proxiservices.Fragments.MapsSearchFragment;
import com.example.a77011_40_05.proxiservices.Fragments.PrestationsSearchFragment;
import com.example.a77011_40_05.proxiservices.Fragments.SearchFragment;
import com.example.a77011_40_05.proxiservices.Fragments.SettingsFragment;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.AsyncCallWS;
import com.example.a77011_40_05.proxiservices.Utils.Constants;
import com.example.a77011_40_05.proxiservices.Utils.Session;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context context;
    ViewPager viewPager;
    Activity activity;
    TextView txtHeaderName;
    ImageView imgProfilePics;
    Button btnSearch;

    //Fragments
    FragmentManager fragmentManager;
    HomeFragment homeFragment = null;
    SettingsFragment settingsFragment = null;
    AccountFragment accountFragment = null;
    AccountProfilePicsFragment accountProfilePicsFragment = null;
    AccountGalleryFragment accountGalleryFragment = null;
    MapsFragment mapsFragment = null;
    MapsAddFragment mapsAddFragment = null;
    MapsSearchFragment mapsSearchFragment =null;
    PrestationsSearchFragment prestationsSearchFragment =null;
    SearchFragment searchFragment = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Chargement du fragment de départ
        fragmentManager = getFragmentManager();
        changeFragment(Constants._FRAG_HOME,null);

        /*TODO: voir le commentaire dans le layout.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        txtHeaderName = navigationView.getHeaderView(0).findViewById(R.id.txtHeader_name);
        imgProfilePics = navigationView.getHeaderView(0).findViewById(R.id.imgProfilePic);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        //Search
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.isEmpty()){
                    Bundle params = new Bundle();
                    params.putString("search", query);
                    changeFragment(Constants._FRAG_SEARCH_TEXT,params);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            changeFragment(Constants._FRAG_SETTINGS,null);

        }
        else if(id == R.id.action_AddCarte)
        {
            changeFragment(Constants._FRAG_MAPS_ADD,null);
        }
        else if(id == R.id.action_SearchCarte)
        {
            changeFragment(Constants._FRAG_MAPS_SEARCH,null);
        }
        else if(id == R.id.action_maps)
        {
            changeFragment(Constants._FRAG_MAPS,null);
        }


        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home){
            changeFragment(Constants._FRAG_HOME,null);
        } else if (id == R.id.nav_account) {
            // Handle the camera action
            if(Session.getConnectionChecked()){
                changeFragment(Constants._FRAG_ACCOUNT,null);
            }else{
                loadIntent(context,LoginActivity.class,Constants._CODE_LOGIN);
            }

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==Constants._CODE_LOGIN && data != null){

            String msg=data.getStringExtra("RETURN");
            switch (msg){
                case "VALIDATE":
                    String[] recive = data.getStringArrayExtra("DATA");
                    //txtHeaderName.setText(recive[0]+" "+recive[1]);
                    userHasChange(Session.getMyUser());
                    Toast.makeText(this,"Bienvenue "+recive[1]+" "+recive[0], Toast.LENGTH_LONG).show();
                    changeFragment(Constants._FRAG_ACCOUNT,null);
                    break;
                case "FORGOTTEN":
                    Toast.makeText(this,"On rechange le mot de passe", Toast.LENGTH_LONG).show();
                    break;
                default:
                    Toast.makeText(this,"RETOUR INVALIDE LOGINACTIVITY", Toast.LENGTH_LONG).show();
                    break;
            }

        }
    }

    public void changeFragment(int code, Bundle params){
        Fragment frag = null;
        switch (code){
            case Constants._FRAG_HOME:
                if(homeFragment == null){
                    homeFragment = new HomeFragment();
                }
                frag = homeFragment;
                break;
            case Constants._FRAG_SETTINGS:
                if(settingsFragment== null){
                    settingsFragment= new SettingsFragment();
                }
                frag = settingsFragment;
                break;
            case Constants._FRAG_ACCOUNT:
                accountFragment = new AccountFragment();

                frag = accountFragment;
                break;
            case Constants._FRAG_ACCOUNT_PROFILE_PICS:
                if(accountProfilePicsFragment == null){
                    accountProfilePicsFragment = new AccountProfilePicsFragment();
                }
                frag = accountProfilePicsFragment;
                break;
            case Constants._FRAG_ACCOUNT_GALLERY:
                if(accountGalleryFragment == null){
                    accountGalleryFragment = new AccountGalleryFragment();
                }
                frag = accountGalleryFragment;
                break;
            case Constants._FRAG_MAPS:
                if(mapsFragment == null){
                    mapsFragment = new MapsFragment();
                }
                frag = mapsFragment;
                break;
            case Constants._FRAG_MAPS_SEARCH:
                if(mapsSearchFragment == null){
                    mapsSearchFragment = new MapsSearchFragment();
                }
                frag = mapsSearchFragment;
                break;

            case Constants._FRAG_PRESTATION_SEARCH:
                prestationsSearchFragment = PrestationsSearchFragment.newInstance(params);
                frag = prestationsSearchFragment;
                break;
            case Constants._FRAG_MAPS_ADD:
                if(mapsAddFragment == null){
                    mapsAddFragment = new MapsAddFragment();
                }
                frag = mapsAddFragment;
                break;
            case Constants._FRAG_SEARCH_TEXT:
                searchFragment = SearchFragment.newInstance(params);
                frag = searchFragment;
                break;
            default:
                Log.e("[ERROR]","changeFragment: code invalide "+code);
                break;
        }

        if(frag !=null){
            loadFragment(frag);
        }

    }

    private void loadFragment(Fragment fragment){
        fragmentManager.beginTransaction()
                .replace(R.id.frtHome,fragment)
                .addToBackStack(null)
                .commit();
    }

    private void loadIntent(Context ctx, Class dest, int code){
        Intent intent = new Intent(ctx,dest);
        startActivityForResult(intent, code);
    }

    public void userHasChange(User user){
        txtHeaderName.setText(user.getFullName());
        if(user.getPath() != null ){
            Picasso.with(context)
                    .load(Constants._URL_WEBSERVICE+user.getPath())
                    .transform(new CropCircleTransformation())
                    .into(imgProfilePics);
        }else{
            AsyncCallWS asyncCallWS = new AsyncCallWS(Constants._URL_WEBSERVICE + "getPhotoProfil.php", new AsyncCallWS.OnCallBackAsyncTask() {
                @Override
                public void onResultCallBack(String result) {
                    Log.e(Constants._TAG_LOG,"Download profilpic ...");
                    if(result.isEmpty() || result.equals("error")){
                        Toast.makeText(context,"ERREUR: Telechargement photo de profil",Toast.LENGTH_LONG).show();
                    }else if(result.contains(Constants._ERROR_PHP)){
                        Toast.makeText(context,"ERREUR: Serveur",Toast.LENGTH_LONG).show();
                        Log.e(Constants._TAG_LOG,result);
                    }else{
                        Gson gson = new Gson();
                        JsonObject json = gson.fromJson(result,JsonObject.class);
                        if(json.has("path")){
                            Picasso.with(context)
                                    .load(Constants._URL_WEBSERVICE+json.get("path").getAsString())
                                    .transform(new CropCircleTransformation())
                                    .into(imgProfilePics);
                        }
                    }
                }
            });
            asyncCallWS.addParam("idUser",""+user.getIdUser());
            asyncCallWS.execute();
        }
    }
}
