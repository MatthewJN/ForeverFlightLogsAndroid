package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FlightActivity extends AppCompatActivity {

  public static final String FLIGHTID = "com.foreverflightlogs.FLIGHTID";
  AutoCompleteTextView editOriginText; // = (AutoCompleteTextView) findViewById(R.id.originText);
  AutoCompleteTextView editDestinationText; // = (AutoCompleteTextView) findViewById(R.id.destinationText);
  ArrayAdapter<String> adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_flight);

    editOriginText = (AutoCompleteTextView) findViewById(R.id.originText);
    editDestinationText = (AutoCompleteTextView) findViewById(R.id.destinationText);

    final AirportPresenter airportPresenter = new AirportPresenter();

    adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item,
        airportPresenter.getAirports(getApplicationContext()));

    editOriginText.setThreshold(1);
    editOriginText.setAdapter(adapter);

    editDestinationText.setThreshold(1);
    editDestinationText.setAdapter(adapter);
  }


  /**
   * startFlight: Called when the start flight button is clicked.
   *
   * @param view the Buttons view.
   */
  public void startFlight(View view) {
    // Get the text from the fields.
    EditText editAircraftText = (EditText) findViewById(R.id.aircraftText);

    // Convert those fields to strings
    String origin = editOriginText.getText().toString().split("\\ -")[0];
    String destination = editDestinationText.getText().toString().split("\\ -")[0];
    String aircraft = editAircraftText.getText().toString();

    // Create a new flight
    FlightPresenter flightPresenter = new FlightPresenter(origin, destination, aircraft, new Date(),
        getApplicationContext());

    // Pass the new flightID as an intent.
    Intent intent = new Intent(this, SegmentActivity.class);
    intent.putExtra(FLIGHTID, flightPresenter.getFlightID());
    startActivity(intent);
  }

}
