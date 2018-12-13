package com.foreverflightlogs.foreverflightlogs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class EditSegmentActivity extends AppCompatActivity implements
    CompoundButton.OnCheckedChangeListener {

  long segmentID;
  long flightID;

  SegmentPresenter segmentPresenter;

  Switch pic;
  Switch dualHour;
  Switch simInstruments;
  Switch visualFlight;
  Switch instrFlight;
  Switch night;
  Button btnSave;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_segment);

    Intent intent = getIntent();
    segmentID = intent.getLongExtra(ListSegmentActivity.SEGMENTID, -1);
    flightID = intent.getLongExtra(ListSegmentActivity.FLIGHTID, -1);
    segmentPresenter = new SegmentPresenter(flightID, segmentID, getApplicationContext());
    setSwitches();
    setTextLabels();
  }

  /**
   * Sets the text labels.
   */
  private void setTextLabels() {
    TextView duration = (TextView) findViewById(R.id.duration);
    String segmentDurationText = String
        .format("%ds", getSegmentDuration(segmentPresenter.segment) / 1000);
    duration.setText(segmentDurationText);
  }

  /**
   * getSegmentDuration:
   *
   * @param segment The segment that the
   */
  public long getSegmentDuration(Segment segment) {
    return segment.getEndDate().getTime() - segment.getStartDate().getTime();
  }

  private void setSwitches() {
    // Get all of the switches
    pic = (Switch) findViewById(R.id.switch_PIC);
    dualHour = (Switch) findViewById(R.id.switch_dualHours);
    simInstruments = (Switch) findViewById(R.id.switch_simInstruments);
    visualFlight = (Switch) findViewById(R.id.switch_visualFlight);
    instrFlight = (Switch) findViewById(R.id.switch_instrFlight);
    night = (Switch) findViewById(R.id.switch_Night);

    // Set the listeners.
    pic.setOnCheckedChangeListener(this);
    dualHour.setOnCheckedChangeListener(this);
    simInstruments.setOnCheckedChangeListener(this);
    visualFlight.setOnCheckedChangeListener(this);
    instrFlight.setOnCheckedChangeListener(this);
    night.setOnCheckedChangeListener(this);

    // Sets the default button positions based on what is
    // created in the FlightDbHelper class (all default to false except PIC).
    pic.setChecked(segmentPresenter.segment.getPilotInCommand());
    dualHour.setChecked(segmentPresenter.segment.getDualHours());
    simInstruments.setChecked(segmentPresenter.segment.getSimulatedInstruments());
    visualFlight.setChecked(segmentPresenter.segment.getVisualFlight());
    instrFlight.setChecked(segmentPresenter.segment.getinstrumentFlight());
    night.setChecked(segmentPresenter.segment.getNight());
  }

  @Override
  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    switch (buttonView.getId()) {
      case R.id.switch_PIC:
        //Toast.makeText(this, "pic has changed", Toast.LENGTH_SHORT).show();
        segmentPresenter.segment.setPilotInCommand(isChecked);
        Log.i("picSwitch", "onCheckedChange:pic: " + segmentPresenter.segment.getPilotInCommand());
        break;
      case R.id.switch_dualHours:
        //Toast.makeText(this, "dualHours has changed", Toast.LENGTH_SHORT).show();
        segmentPresenter.segment.setDualHours(isChecked);
        Log.i("dualSwitch", "onCheckedChange:dual: " + segmentPresenter.segment.getDualHours());
        break;
      case R.id.switch_simInstruments:
        //Toast.makeText(this, "simInstruments has changed", Toast.LENGTH_SHORT).show();
        segmentPresenter.segment.setSimulatedInstruments(isChecked);
        Log.i("simSwitch",
            "onCheckedChange:sim: " + segmentPresenter.segment.getSimulatedInstruments());
        break;
      case R.id.switch_visualFlight:
        //Toast.makeText(this, "visualFlight has changed", Toast.LENGTH_SHORT).show();
        segmentPresenter.segment.setVisualFlight(isChecked);
        Log.i("visualSwitch",
            "onCheckedChange:visual: " + segmentPresenter.segment.getVisualFlight());
        break;
      case R.id.switch_instrFlight:
        // Toast.makeText(this, "instrFlight has changed", Toast.LENGTH_SHORT).show();
        segmentPresenter.segment.setInstrumentFlight(isChecked);
        Log.i("instrSwitch",
            "onCheckedChange:instr: " + segmentPresenter.segment.getinstrumentFlight());
        break;
      case R.id.switch_Night:
        // Toast.makeText(this, "night has changed", Toast.LENGTH_SHORT).show();
        segmentPresenter.segment.setNight(isChecked);
        Log.i("nightSwitch", "onCheckedChange:night: " + segmentPresenter.segment.getNight());
        break;
    }
  }

  public void onSaveButtonClicked(View view) {
    finish();
  }
}
