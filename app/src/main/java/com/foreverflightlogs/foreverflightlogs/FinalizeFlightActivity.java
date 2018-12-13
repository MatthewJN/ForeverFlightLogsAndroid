package com.foreverflightlogs.foreverflightlogs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import java.util.Date;

public class FinalizeFlightActivity extends AppCompatActivity {

  FlightPresenter flightPresenter;
  EditText editRemarksText;
  long flightID;
  Switch cross;
  Switch solo;
  String remarks;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_finalize_flight);

    // Get the flight ID
    Intent intent = getIntent();
    flightID = intent.getLongExtra(SegmentActivity.FLIGHTID, -1);
    flightPresenter = new FlightPresenter(flightID, getApplicationContext());
    // Get all of the switches

    editRemarksText = (EditText) findViewById(R.id.remarksText);

  }

//    /* Flight is completed, save it*/
//    public void saveLog(int flightID) {
//        //using flight id,
//        // 1. add remarks entered by user, - flight.addRemarks();
//        // 2. update flight with cross country - flight.setCrossCountry(true)
//        // 3. update flight with solo flight - flight.setSoloFlight(true)
//        // 3. store in local db
//
//        flightPresenter.flight.setEndDate(new Date());
//    }

  public void saveLogEntry(View view) {
    Log.d("saveLogEntry", "flightID = " + flightID);
    // Context context = getApplicationContext();
    flightPresenter.flight.setRemarks(editRemarksText.getText().toString());

    cross = (Switch) findViewById(R.id.switch_crossCountry);
    solo = (Switch) findViewById(R.id.switch_solo);

    flightPresenter.flight.setCrosscountry(cross.isChecked());
    flightPresenter.flight.setSolo(solo.isChecked());
    flightPresenter.flight.setEndDate(new Date());
    flightPresenter.flight.setInProgress(false);
    SyncManager.sync(getApplicationContext());
    Intent i = new Intent(this, MainActivity.class);
    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(i);
  }

  /**
   * onCheckedChanged: Called when a switch changes on the activity.
   *
   * @param buttonView The switch that changed.
   * @param isChecked The state of the switch that changed.
   */
  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked, long flightID) {
    switch (buttonView.getId()) {
      case R.id.switch_crossCountry:
        //Toast.makeText(this, "pic has changed", Toast.LENGTH_SHORT).show();
        flightPresenter.flight.setCrosscountry(isChecked);
        Log.i("crossCountrySwitch",
            "onCheckedChange:cross: " + flightPresenter.flight.getCrosscountry());
        break;
      case R.id.switch_solo:
        //Toast.makeText(this, "dualHours has changed", Toast.LENGTH_SHORT).show();
        flightPresenter.flight.setSolo(isChecked);
        Log.i("soloSwitch", "onCheckedChange:solo: " + flightPresenter.flight.getSolo());
        break;

    }
  }
}
