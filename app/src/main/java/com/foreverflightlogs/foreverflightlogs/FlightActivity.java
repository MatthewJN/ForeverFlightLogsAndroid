package com.foreverflightlogs.foreverflightlogs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

public class FlightActivity extends AppCompatActivity {

    public static final String FLIGHTID = "com.foreverflightlogs.FLIGHTID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);
    }

    /**
     * startFlight:
     * Called when the start flight button is clicked.
     * @param view the Buttons view.
     */
    public void startFlight(View view) {
        // Get the text from the fields.
        EditText editOriginText = (EditText) findViewById(R.id.originText);
        EditText editAircraftText = (EditText) findViewById(R.id.aircraftText);

        // Convert those fields to strings
        String origin = editOriginText.getText().toString();
        String aircraft = editAircraftText.getText().toString();

        // Create a new flight
        FlightPresenter flightPresenter = new FlightPresenter(origin, aircraft, new Date(), getApplicationContext());

        
        // Pass the new flightID as an intent.
        Intent intent = new Intent(this, SegmentActivity.class);
        intent.putExtra(FLIGHTID, flightPresenter.getFlightID());

        startActivity(intent);
    }
}
