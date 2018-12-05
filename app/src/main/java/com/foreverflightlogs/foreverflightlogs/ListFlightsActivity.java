package com.foreverflightlogs.foreverflightlogs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ListFlightsActivity extends AppCompatActivity {

    ListView listView;
    FlightsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_flights);

        FlightPresenter flightPresenter = new FlightPresenter(null, null, getApplicationContext());

        listView = (ListView)findViewById(R.id.flightsList);

        adapter = new FlightsAdapter(this, flightPresenter.flights);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Intent intent = new Intent(ListSegmentActivity.this, EditSegmentActivity.class);
//                intent.putExtra(SEGMENTID, segmentPresenter.segments.get(position).getSegmentID());
//                intent.putExtra(FLIGHTID, flightID);
//                startActivity(intent);
            }
        });
    }

}
