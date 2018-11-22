package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;
import java.util.Observable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/** This class should allow other parts of the app to check whether there is an internet connection */
public class InternetConnection extends Observable {

    boolean connection;

    public boolean getConnection() { return connection; }

    public void isInternetOn(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            this.connection = true;
        } else {
            this.connection = false;
        }
        this.setChanged();
        this.notifyObservers(connection);
    }

}