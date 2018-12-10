package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String FLIGHTID = "com.foreverflightlogs.FLIGHTID";
    long inProgressFlightID;
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
        Button continueFlight = (Button) findViewById(R.id.btn_continue_flight);
        continueFlight.setVisibility(View.INVISIBLE); //make btn invisible unless unfinished flight

        List<Flight> inProgressFlights = flightDbHelper.getAllFlightsOfType(false, true, getApplicationContext());

        if (inProgressFlights.size() == 1) {
            continueFlight.setVisibility(View.VISIBLE); //make btn invisible unless unfinished flightIntent intent = new Intent(MainActivity.this, SegmentActivity.class );
            inProgressFlightID = inProgressFlights.get(0).getFlightID();

            Toast.makeText(this, "You have an unfinished flight. To complete flight, select Continue Flight. ", Toast.LENGTH_LONG).show();

        }
        else if(inProgressFlights.size() > 1){ //more than 1 flight in progress go to listFlights
            Intent intent = new Intent(MainActivity.this, ListFlightsActivity.class );
            MainActivity.this.startActivity(intent);
            Toast.makeText(this, "You have unfinished flights. To complete a flight, select Continue Flight. ", Toast.LENGTH_LONG).show();
        }

        AirportPresenter airportPresenter = new AirportPresenter();
        airportPresenter.fetchAirports(getApplicationContext());

    }

    public void continueFlight(View view) {
        Intent intent = new Intent(MainActivity.this, ListFlightsActivity.class );
        intent.putExtra(FLIGHTID, inProgressFlightID);
        MainActivity.this.startActivity(intent);
        Toast.makeText(this, "This flight is unfinished. ", Toast.LENGTH_LONG).show();
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
