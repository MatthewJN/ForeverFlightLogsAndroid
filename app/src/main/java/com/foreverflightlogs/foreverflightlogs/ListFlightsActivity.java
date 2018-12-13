package com.foreverflightlogs.foreverflightlogs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ListFlightsActivity extends AppCompatActivity {

  ListView listView;
  FlightsAdapter adapter;

  public static final String FLIGHTID = "com.foreverflightlogs.FLIGHTID";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_flights);

    FlightPresenter flightPresenter = new FlightPresenter(null, null, getApplicationContext());

    listView = (ListView) findViewById(R.id.flightsList);

    adapter = new FlightsAdapter(this, flightPresenter.flights);

    listView.setAdapter(adapter);

    listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(ListFlightsActivity.this, SegmentActivity.class);
        Flight flight = adapter.getItem(position);
        if (!flight.getInProgress()) {

          Toast.makeText(getApplicationContext(), "Flight Already Completed", Toast.LENGTH_SHORT)
              .show();
        } else {
          intent.putExtra(FLIGHTID, flight.getFlightID());
          intent.putExtra("In Progress", true);
          startActivity(intent);
        }
      }
    });
  }

}
