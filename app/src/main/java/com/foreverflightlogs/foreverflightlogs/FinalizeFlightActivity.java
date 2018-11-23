package com.foreverflightlogs.foreverflightlogs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Date;

public class FinalizeFlightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalize_flight);
    }

    /* Flight is completed, save it*/
    public void saveLog(int flightID) {
        //using flight id,
        // 1. add remarks entered by user, - flight.addRemarks();
        // 2. update flight with cross country - flight.setCrossCountry(true)
        // 3. update flight with solo flight - flight.setSoloFlight(true)
        // 3. store in local db
        FlightPresenter flightPresenter = new FlightPresenter(2, getApplicationContext());
        flightPresenter.flight.setEndDate(new Date());
    }

    public void saveLogEntry(View view) {
        Intent i=new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
