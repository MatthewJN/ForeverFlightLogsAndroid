package com.foreverflightlogs.foreverflightlogs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserManager userManager = new UserManager();
        if (userManager.getAuthCode() == null) {
            finish();
            startActivity(new Intent(MainActivity.this, UserActivity.class));
        }

        //new UserManager().authWithAPI("", "", getApplicationContext());
    }

    public void createNewFlight(View view) {
        Intent myIntent = new Intent(MainActivity.this, FlightActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void showAllFlights() {

    }

}
