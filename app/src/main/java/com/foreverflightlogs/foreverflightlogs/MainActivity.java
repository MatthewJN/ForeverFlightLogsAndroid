package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String FLIGHTID = "com.foreverflightlogs.FLIGHTID";
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

        // handle one or more flights in progress
        List<Flight> inProgressFlights = flightDbHelper.getAllFlightsOfType(false, true, getApplicationContext());

        if (inProgressFlights.size() == 1) {
            Intent intent = new Intent(MainActivity.this, SegmentActivity.class );
            intent.putExtra(FLIGHTID, inProgressFlights.get(0).getFlightID());
            MainActivity.this.startActivity(intent);
            Toast.makeText(this, "This flight is unfinished. ", Toast.LENGTH_LONG).show();

        }
        else if(inProgressFlights.size() > 1){ //more than 1 flight in progress go to listFlights
            Intent intent = new Intent(MainActivity.this, ListFlightsActivity.class );
            MainActivity.this.startActivity(intent);
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
