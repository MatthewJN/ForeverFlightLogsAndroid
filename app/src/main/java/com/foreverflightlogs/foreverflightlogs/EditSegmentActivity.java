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
import java.util.concurrent.TimeUnit;

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

    String segmentDurationText = String.format("%02d:%02d:%02d",
        TimeUnit.MILLISECONDS.toHours(getSegmentDuration(segmentPresenter.segment)),
        TimeUnit.MILLISECONDS.toMinutes(getSegmentDuration(segmentPresenter.segment)) - TimeUnit.MINUTES
            .toMinutes(TimeUnit.MILLISECONDS.toHours(getSegmentDuration(segmentPresenter.segment))),
        TimeUnit.MILLISECONDS.toSeconds(getSegmentDuration(segmentPresenter.segment)) - TimeUnit.MINUTES
            .toSeconds(TimeUnit.MILLISECONDS.toMinutes(getSegmentDuration(segmentPresenter.segment))));
    duration.setText(segmentDurationText);
  }

  /**
   * getSegmentDuration: Calculates the duration of the segment from start to end date.
   *
   * @param segment The segment that is being queried.
   */
  public long getSegmentDuration(Segment segment) {
    return segment.getEndDate().getTime() - segment.getStartDate().getTime();
  }

  /**
   * Sets the switches to the required positions.
   */
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

  /**
   * onCheckedChanged: Checks for changes with the buttons and puts in a request to update the
   * model.
   *
   * @param buttonView The button
   * @param isChecked Is the button on or off?
   */
  @Override
  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    switch (buttonView.getId()) {
      case R.id.switch_PIC:
        segmentPresenter.segment.setPilotInCommand(isChecked);
        break;
      case R.id.switch_dualHours:
        segmentPresenter.segment.setDualHours(isChecked);
        break;
      case R.id.switch_simInstruments:
        segmentPresenter.segment.setSimulatedInstruments(isChecked);
        break;
      case R.id.switch_visualFlight:
        segmentPresenter.segment.setVisualFlight(isChecked);
        break;
      case R.id.switch_instrFlight:
        segmentPresenter.segment.setInstrumentFlight(isChecked);
        break;
      case R.id.switch_Night:
        segmentPresenter.segment.setNight(isChecked);
        break;
    }
  }

  /**
   * onSaveButtonClicked: Finishes the segment.
   *
   * @param view The view
   */
  public void onSaveButtonClicked(View view) {
    finish();
  }
}
