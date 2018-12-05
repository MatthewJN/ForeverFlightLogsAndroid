package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Remove any incomplete segments.
        FlightDbHelper flightDbHelper = new FlightDbHelper(getApplicationContext());
        flightDbHelper.cleanUpSegments(getApplicationContext());

        // Sync if connection available.
        if (isInternetOn(getApplicationContext())) {
            SyncManager.sync(getApplicationContext());
        }

        AirportPresenter airportPresenter = new AirportPresenter();
        airportPresenter.fetchAirports(getApplicationContext());
    }

    public void createNewFlight(View view) {
        Intent myIntent = new Intent(MainActivity.this, FlightActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void showAllFlights(View view) {
        Intent myIntent = new Intent(MainActivity.this, ListFlightsActivity.class);
        MainActivity.this.startActivity(myIntent);
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
