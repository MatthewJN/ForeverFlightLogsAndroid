package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;
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
import android.widget.Toast;

import java.util.Date;
import java.util.List;

/**
 * Segment Activity:
 *   Contains timer and toggles to create a segment
 *   Timer will populate start and stop times, and once timer is stopped,
 *   Segment will be stored in db & synced to API
 *   If endFlight button is selected, then this is final segment
 *   and will move to next activity to finalizeFlight
 *
 *   Timer logic is all controlled in View. I think it should be stored in
 *  *   presenter, but since it was needing to update the UI anytime the timer was going
 *  *   I was uncertain how to do that in the presenter.
 */
public class SegmentActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
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
    private SegmentPresenter segmentPresenter; // = new SegmentPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segment);

        // Get the flight ID
        Intent intent = getIntent();
        long flightID = intent.getLongExtra(FlightActivity.FLIGHTID, -1);

        // Create a new segment attached to the flightID passed in.
        segmentPresenter = new SegmentPresenter(flightID, getApplicationContext());

        // Get all of the switches
        pic = (Switch)findViewById(R.id.switch_PIC);
        dualHour = (Switch)findViewById(R.id.switch_dualHours);
        simInstruments = (Switch)findViewById(R.id.switch_SimInstruments);
        visualFlight = (Switch)findViewById(R.id.switch_VisualFlight);
        instrFlight = (Switch)findViewById(R.id.switch_InstrFlight);
        night = (Switch)findViewById(R.id.switch_Night);

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

        // get state of timer
        if(savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            startRun = savedInstanceState.getBoolean("startRun");
        }
        Timer(); //update timer value on screen

    }

    /**
     * onSaveInstanceState:
     * @param saveInstanceState
     */
    //    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putInt("seconds", seconds);
        saveInstanceState.putBoolean("startRun", startRun);
    }

    /**
     * Process once start button is clicked
     * After disabling start button and resetting timer
     * move remaining process handling to presenter
     * @param view
     */
    public void onClickStart(View view) {
        startBtn = (Button) findViewById(R.id.btn_start);
        startBtn.setEnabled(false); //disable start button till stop is pushed
        onClickReset(view); //reset the clock
        startRun = true;

        // Pass in the current time to the segment.
        segmentPresenter.segment.setStartDate(new Date());
    }

    /**
     * Process once stop button is clicked
     * Enable the start button, turn off timer, and
     * move remaining process handling to presenter
     * @param view
     */
    public void onClickStop(View view) {
        startBtn = (Button) findViewById(R.id.btn_start); //find start button to enable it
        startBtn.setEnabled(true);
        startRun = false;

        //Pass in the current time to the segment.
        segmentPresenter.segment.setEndDate(new Date());

    }

    /**
     * Reset the timer to 00:00
     * @param view
     */
    public void onClickReset(View view) {
        startRun = false;
        seconds = 0;
    }

    /**
     * Called when user taps end flight button
     * @param view
     *
     * When user clicks endFlight
     *      * send process handling to presenter
     *      * and display screen SegmentDetailList
     */
    public void onClickEndFlight(View view){
        //call segmentPresenter.endSegment()

        //flightManager.endFlight()
        Intent intent = new Intent(this, ListSegmentActivity.class);
        startActivity(intent);
        Toast.makeText(this, "onclickEndFlight opens ListSegmentActivity", Toast.LENGTH_SHORT).show();
    }

    /**
     * Handles the updating of the clock textView
     * As long as startRun = true, update the timer
     * This is run an a background thread to not interrupt
     * the main UI processes
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

                handler.postDelayed(this, 100);
            }
        });
    }

    /**
     * onCheckedChanged:
     * Called when a switch changes on the activity.
     * @param buttonView The switch that changed.
     * @param isChecked The state of the switch that changed.
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_PIC:
                Toast.makeText(this, "pic has changed", Toast.LENGTH_SHORT).show();
                segmentPresenter.segment.setPilotInCommand(isChecked);
                break;
            case R.id.switch_dualHours:
                Toast.makeText(this, "dualHours has changed", Toast.LENGTH_SHORT).show();
                segmentPresenter.segment.setDualHours(isChecked);
                break;
            case R.id.switch_SimInstruments:
                Toast.makeText(this, "simInstruments has changed", Toast.LENGTH_SHORT).show();
                segmentPresenter.segment.setSimulatedInstruments(isChecked);
                break;
            case R.id.switch_VisualFlight:
                Toast.makeText(this, "visualFlight has changed", Toast.LENGTH_SHORT).show();
                segmentPresenter.segment.setVisualFlight(isChecked);
                break;
            case R.id.switch_InstrFlight:
                Toast.makeText(this, "instrFlight has changed", Toast.LENGTH_SHORT).show();
                segmentPresenter.segment.setInstrumentFlight(isChecked);
                break;
            case R.id.switch_Night:
                Toast.makeText(this, "night has changed", Toast.LENGTH_SHORT).show();
                segmentPresenter.segment.setNight(isChecked);
                break;
        }
    }
}