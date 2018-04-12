package com.example.a77011_40_05.proxiservices.Utils;

import com.example.a77011_40_05.proxiservices.Entities.User;

/**
 * Created by 77011-40-05 on 08/03/2018.
 */

public class Session {

    private static User myUser;

    private static Boolean connectionChecked = false;

    public static Boolean getConnectionChecked() {
        return connectionChecked;
    }

    public static void setConnectionChecked(Boolean connectionChecked) {
        Session.connectionChecked = connectionChecked;
    }

    public static User getMyUser() {
        return myUser;
    }

    public static void setMyUser(User myUser) {
        Session.myUser = myUser;
    }
}
