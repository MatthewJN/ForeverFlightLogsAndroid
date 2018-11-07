package com.foreverflightlogs.foreverflightlogs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SegmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segment);
    }

    /** Start Flight Segment
    *    User has pushed button to start segment*/
    public void startSegment() {
        //call Timer.start() & return time
        // initialize a new SegmentPresenter(Date startTime) //instead of startSegment() this will start it
        return;
    }

    /** End Flight Segment */
    public void endSegment() {
        //call Timer.end() & get time ended
        // call segmentPresenter.endSegment(Date endTime)
        return;
    }

    /**
     * End Flight
    * User can opt to either end segment (and
    * create a new one for that flight, OR
    * End the flight. If ending flight, they should be taken
    * to the segmentListActivity to review the segments and add remarks/finalize flight*/
    public void endFlight() {
        //call segmentPresenter.endSegment()

        //flightManager.endFlight()

        return;

    }
}
