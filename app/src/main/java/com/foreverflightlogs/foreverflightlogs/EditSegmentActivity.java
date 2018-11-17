package com.foreverflightlogs.foreverflightlogs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class EditSegmentActivity extends AppCompatActivity {

    EditSegmentPresenter presenter = new EditSegmentPresenter(this);
    String segmentID = null;
    private TextView passedView = null;

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

        //Get passed variables from our intent's EXTRAS
        segmentID = getIntent().getStringExtra(ListSegmentActivity.ID_EXTRA);
        //assign all fields and switches
        EditText duration = (EditText)findViewById(R.id.duration);
        EditText startTime = (EditText)findViewById(R.id.startTime);
        EditText endTime = (EditText)findViewById(R.id.endTime);
        pic = (Switch)findViewById(R.id.switch_PIC);
        dualHour = (Switch)findViewById(R.id.switch_dualHours);
        simInstruments = (Switch)findViewById(R.id.switch_SimInstruments);
        visualFlight = (Switch)findViewById(R.id.switch_VisualFlight);
        instrFlight = (Switch)findViewById(R.id.switch_InstrFlight);
        night = (Switch)findViewById(R.id.switch_Night);

        //populate fields from segment passed in
        //or using segmentID passed in, query db and get fields to populate
        //duration.setText(passedVar);
        presenter.populateSegmentFields(segmentID);

    }

    public void onClickButtonSave() {
        presenter.saveSegmentInDB();

    }
}
