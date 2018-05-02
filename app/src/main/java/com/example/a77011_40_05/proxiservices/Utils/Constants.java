package com.example.a77011_40_05.proxiservices.Utils;

/**
 * Created by 77011-40-05 on 14/03/2018.
 */

public class Constants {

    public final static String[] _LOGO_LIST = new String[]{null,"logo_jardinage","logo_menage","logo_enseignement"};

    //GENERAL
    public static final String SHARED_PREFERENCE = "ShareData" ;
    public static final String _TAG_LOG = "#PROXISERVICES";
    public static final String _ERROR_PHP = "Erreur PHP";

    //FRAGMENT CODE -->HomeActivity
    public final static int _FRAG_HOME = 0;
    public final static int _FRAG_SETTINGS = 1;
    public final static int _FRAG_ACCOUNT = 2;
    public final static int _FRAG_ACCOUNT_PROFILE_PICS = 3;
    public final static int _FRAG_ACCOUNT_GALLERY = 4;
    public final static int _FRAG_PRESTATION_SEARCH = 5;
    public final static int _FRAG_MAPS = 6;
    public final static int _FRAG_MAPS_SEARCH = 7;
    public final static int _FRAG_MAPS_ADD = 8;

    public final static int _FRAG_SEARCH_TEXT = 100;

    //VIEW PAGER PRESTATIONS
    public final static int _MODE_PROPOSE = 0;
    public final static int _MODE_REQUEST = 1;

    //STARTACTIVITYFORRESULT -->HomeActivity
    public final static int _CODE_LOGIN = 2;

    //REQUEST CODE -->LoginActivity
    public final static int _LOGIN_REQUEST = 1;
    public final static int _PASSWORD_REQUEST = 2;

    public static final String APP_NAME = "ProxiServices";

    public static final String _URL_WEBSERVICE =  "http://10.75.25.36:8080/proxiservices/";

}
