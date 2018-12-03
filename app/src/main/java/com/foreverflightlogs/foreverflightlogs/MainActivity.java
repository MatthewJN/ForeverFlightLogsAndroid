package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (isInternetOn(getApplicationContext())) {
            SyncManager.sync(getApplicationContext());
        }
    }

    public void createNewFlight(View view) {
        Intent myIntent = new Intent(MainActivity.this, FlightActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void showAllFlights(View view) {

    }

    public boolean isInternetOn(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            //           this.connection = true;
            return true;
        } else {
            //           this.connection = false;
            return false;
        }
//        this.setChanged();
//        this.notifyObservers(connection);
    }

}
