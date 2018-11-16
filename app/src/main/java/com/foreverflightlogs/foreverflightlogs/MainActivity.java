package com.foreverflightlogs.foreverflightlogs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FlightPresenter flightPresenter = new FlightPresenter(3, getApplicationContext());
    }

    public void createNewFlight() { }

    public void showAllFlights() { }

}
