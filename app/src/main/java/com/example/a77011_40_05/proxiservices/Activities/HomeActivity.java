package com.example.a77011_40_05.proxiservices.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a77011_40_05.proxiservices.Entities.User;
import com.example.a77011_40_05.proxiservices.Fragments.AccountFragment;
import com.example.a77011_40_05.proxiservices.Fragments.AccountGalleryFragment;
import com.example.a77011_40_05.proxiservices.Fragments.AccountProfilePicsFragment;
import com.example.a77011_40_05.proxiservices.Fragments.HomeFragment;
import com.example.a77011_40_05.proxiservices.Fragments.MapsFragment;
import com.example.a77011_40_05.proxiservices.Fragments.SettingsFragment;
import com.example.a77011_40_05.proxiservices.Fragments.UserPrestationFragment;
import com.example.a77011_40_05.proxiservices.R;
import com.example.a77011_40_05.proxiservices.Utils.Constants;
import com.example.a77011_40_05.proxiservices.Utils.Functions;
import com.example.a77011_40_05.proxiservices.Utils.Session;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context context;

    //Fragments
    FragmentManager fragmentManager;
    HomeFragment homeFragment = null;
    SettingsFragment settingsFragment = null;
    AccountFragment accountFragment = null;
    AccountProfilePicsFragment accountProfilePicsFragment = null;
    AccountGalleryFragment accountGalleryFragment = null;
    UserPrestationFragment userPrestationFragment = null;
    MapsFragment mapsFragment=null;

    TextView txtHeaderName;
    ImageView imgProfilePics;

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
            changeFragment(Constants._FRAG_SETTINGS, null);
        }  else if(id == R.id.action_maps)
            {
                /*Intent intent=new Intent(HomeActivity.this,MapsActivity.class);
                startActivityForResult(intent, 1);// Activity is started with requestCode 2*/
                changeFragment(Constants.FRAG_Maps, null);
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
                if(accountFragment == null){
                    accountFragment = new AccountFragment();
                }
                frag = accountFragment;
                break;
            case Constants._FRAG_ACCOUNT_PROFILE_PICS:
                if(accountProfilePicsFragment == null){
                    accountProfilePicsFragment = new AccountProfilePicsFragment();
                }
                frag = accountProfilePicsFragment;
                break;
            case Constants.FRAG_Maps:
                if(mapsFragment == null){
                    mapsFragment = new MapsFragment();
                }
                frag = mapsFragment;
                break;
            case Constants._FRAG_ACCOUNT_GALLERY:
                if(accountGalleryFragment == null){
                    accountGalleryFragment = new AccountGalleryFragment();
                }
                frag = accountGalleryFragment;
                break;
            case Constants._FRAG_USER_PRESTATION:
                //if(userPrestationFragment == null){
                    userPrestationFragment = UserPrestationFragment.newInstance(params);
                //}
                frag = userPrestationFragment;
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
        if(user.getProfilePic() != null ){
            Bitmap photo = Functions.loadFromInternalStorage(user.getProfilePic(),"profile.jpg");
            imgProfilePics.setImageBitmap(photo);
        }
    }
}
