package com.foreverflightlogs.foreverflightlogs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FlightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);
    }

    public void startFlight(View view) {
        // Call default contsructor of FlightManager

        // Call startFlight() and pass in the origin, aircraft, and current date/time.

        // Push to Segment Activity - including the flightID as the intent.

    }
}
