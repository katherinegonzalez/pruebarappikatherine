package com.katherine.pruebarappi.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetValidation {

    public NetValidation() {
    }

    public Boolean isNetDisponible(Activity activity) {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();

        return (actNetInfo != null && actNetInfo.isConnected());
    }

    public Boolean isOnlineNet() {

        try {
            Process process = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            Integer val = process.waitFor();
            Boolean reachable = (val == 0);
            return reachable;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean isNet(Activity activity){
        return (isNetDisponible(activity) && isOnlineNet());
    }
}
