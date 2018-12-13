package com.foreverflightlogs.foreverflightlogs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Date;

/**
 * Segment Activity: Contains timer and toggles to create a segment Timer will populate start and
 * stop times, and once timer is stopped, Segment will be stored in db & synced to API If endFlight
 * button is selected, then this is final segment and will move to next activity to finalizeFlight
 *
 * Timer logic is all controlled in View. I think it should be stored in *   presenter, but since it
 * was needing to update the UI anytime the timer was going *   I was uncertain how to do that in
 * the presenter.
 */
public class SegmentActivity extends AppCompatActivity implements
    CompoundButton.OnCheckedChangeListener {

  private int seconds = 0;
  private boolean startRun;
  Button startBtn;
  Button stopBtn;
  Button endFlight;
  Switch pic;
  Switch dualHour;
  Switch simInstruments;
  Switch visualFlight;
  Switch instrFlight;
  Switch night;
  SegmentPresenter segmentPresenter; // = new SegmentPresenter(this);
  long flightID = -1; //set to -1 for ability to test for error

  public static final String FLIGHTID = "com.foreverflightlogs.FLIGHTID";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_segment);

    // Get the flight ID
    Intent intent = getIntent();
    flightID = intent.getLongExtra(FlightActivity.FLIGHTID, -1);

    // Create a new segment attached to the flightID passed in.
    segmentPresenter = new SegmentPresenter(getApplicationContext());
    segmentPresenter.startSegment(flightID, getApplicationContext());

    //in progress flights have option to start new segment or just end the flight.
    if (intent.hasExtra("In Progress")) {
      setSwitches();
      enableNewSegmentButton(true);
      enableEndFlightButton(true);
      enableEndButton(true);
    } else {
      setSwitches();
      enableNewSegmentButton(false);
      enableEndFlightButton(false);
      enableEndButton(false);
    }

    // get state of timer
    if (savedInstanceState != null) {
      seconds = savedInstanceState.getInt("seconds");
      startRun = savedInstanceState.getBoolean("startRun");
    }
    Timer(); //update timer value on screen
  }

  public void setSwitches() {
    // Get all of the switches
    pic = (Switch) findViewById(R.id.switch_PIC);
    dualHour = (Switch) findViewById(R.id.switch_dualHours);
    simInstruments = (Switch) findViewById(R.id.switch_SimInstruments);
    visualFlight = (Switch) findViewById(R.id.switch_VisualFlight);
    instrFlight = (Switch) findViewById(R.id.switch_InstrFlight);
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
   * onSaveInstanceState:
   */
  //    @Override
  public void onSaveInstanceState(Bundle saveInstanceState) {
    super.onSaveInstanceState(saveInstanceState);
    saveInstanceState.putInt("seconds", seconds);
    saveInstanceState.putBoolean("startRun", startRun);
  }

  public void onClickNewSegment(View view) {
    enableStartButton(true);
    enableEndButton(false);
    enableNewSegmentButton(false);
    enableEndFlightButton(false);
    // Instantiate a new segment
    segmentPresenter.startSegment(flightID, getApplicationContext());
    // Reset the switches
    setSwitches();
    // reset timer
    onClickReset(view);
  }

  public void enableStartButton(boolean enable) {
    startBtn = (Button) findViewById(R.id.btn_start);
    startBtn.setEnabled(enable);
  }

  public void enableEndButton(boolean enable) {
    startBtn = (Button) findViewById(R.id.btn_stop);
    startBtn.setEnabled(enable);
  }

  public void enableNewSegmentButton(boolean enable) {
    startBtn = (Button) findViewById(R.id.btn_newSegment);
    startBtn.setEnabled(enable);
  }

  public void enableEndFlightButton(boolean enable) {
    startBtn = (Button) findViewById(R.id.btn_endFlight);
    startBtn.setEnabled(enable);
  }

  /**
   * Process once start button is clicked After disabling start button and resetting timer move
   * remaining process handling to presenter
   */
  public void onClickStart(View view) {
    enableStartButton(false);
    enableEndButton(true);
    enableNewSegmentButton(false);
    enableEndFlightButton(false);
    onClickReset(view); //reset the clock
    startRun = true;

    // Pass in the current time to the segment.
    segmentPresenter.segment.setStartDate(new Date());
  }

  /**
   * Process once stop button is clicked Enable the start button, turn off timer, and move remaining
   * process handling to presenter
   */
  public void onClickStop(View view) {
    segmentPresenter.segment.setIsCompleted(true);
    enableStartButton(false);
    enableEndButton(false);
    enableNewSegmentButton(true);
    enableEndFlightButton(true);
    startRun = false;

    //Pass in the current time to the segment.
    segmentPresenter.segment.setEndDate(new Date());
  }

  /**
   * Reset the timer to 00:00
   */
  public void onClickReset(View view) {
    startRun = false;
    seconds = 0;
  }

  /**
   * Called when user taps end flight button
   *
   * @param view When user clicks endFlight * send process handling to presenter * and display
   * screen SegmentDetailList
   */
  public void onClickEndFlight(View view) {
    //call segmentPresenter.endSegment()

    //flightManager.endFlight()
    Intent intent = new Intent(this, ListSegmentActivity.class);
    intent.putExtra(FLIGHTID, flightID);
    startActivity(intent);
    // Toast.makeText(this, "onclickEndFlight opens ListSegmentActivity", Toast.LENGTH_SHORT).show();
  }

  /**
   * Handles the updating of the clock textView As long as startRun = true, update the timer This is
   * run an a background thread to not interrupt the main UI processes
   */
  private void Timer() {
    final TextView timeView = (TextView) findViewById(R.id.time_view);
    final Handler handler = new Handler();
    handler.post(new Runnable() {
      @Override
      public void run() {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;

        String time = String.format("%d:%02d:%02d", hours, minutes, secs);

        timeView.setText(time);

        if (startRun) {
          seconds++;
        }

        handler.postDelayed(this, 1000);
      }
    });
  }

  /**
   * onCheckedChanged: Called when a switch changes on the activity.
   *
   * @param buttonView The switch that changed.
   * @param isChecked The state of the switch that changed.
   */
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
      case R.id.switch_SimInstruments:
        //Toast.makeText(this, "simInstruments has changed", Toast.LENGTH_SHORT).show();
        segmentPresenter.segment.setSimulatedInstruments(isChecked);
        Log.i("simSwitch",
            "onCheckedChange:sim: " + segmentPresenter.segment.getSimulatedInstruments());
        break;
      case R.id.switch_VisualFlight:
        //Toast.makeText(this, "visualFlight has changed", Toast.LENGTH_SHORT).show();
        segmentPresenter.segment.setVisualFlight(isChecked);
        Log.i("visualSwitch",
            "onCheckedChange:visual: " + segmentPresenter.segment.getVisualFlight());
        break;
      case R.id.switch_InstrFlight:
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
}