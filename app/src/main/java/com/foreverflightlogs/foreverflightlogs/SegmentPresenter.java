package com.foreverflightlogs.foreverflightlogs;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SegmentPresenter {
    //public int flightID; //for testing
    public int segmentID;
    SegmentActivity sView;
    SegmentModel sModel;
    String testMsg;

    public int segmentInProgressID; //get this from flightSegment model?

    /**
     * Default Constructor:
     * Nothing to be initialized
     */
    SegmentPresenter() { }

    /**
     * Non-Default Constructor:
     * Pass in a view to instantiate the SegmentPresenter with the view desired.
     * @param view The segment activity view
     */
    SegmentPresenter(SegmentActivity view) {
        sView = view;
    }

    /**
     *
     * Called when a new segment is starting.
     * API requires auth & bool (isUserPilotInCommand = 1 or 0) params
     * Required body for post:
     *      accountID of user
     *      flightID
     *
     *      segmentStartTime requires this format: 2017-04-12 16:21:03
     * @param startTime
     *
     *
     */
    public int startSegment( Date startTime) {

        sModel = new SegmentModel(); //create new segmentModel object
        sModel.setStartTime(startTime);  //store startTime

        //@todo remove toast once SQL is hooked up
        Toast.makeText(sView, "startTime stored in segmentModel object", Toast.LENGTH_SHORT).show(); //@todo Toast remove for unitTest
        //connect to SQL & create segment
        //verify segment has been created and throw error if not
        //set segmentInProgress

        int segmentID = -1;
        return segmentID;
    }

    /**
     * Use this for logic for entire segment creation process
     * Get start time, create segment with time, update model
     * sets the returned segmentID in segmentInProgressID
     */
    public void handleSegmentStart() {
        Date startTime = this.getCurrentTime();
        int segmentID = startSegment(startTime);
        setSegmentInProgressID(segmentID);
        return;
    }
    /**
     * End flight segment by pushing stop button on timer
     *  calculate the duration of time of segment
     *   update current flight segment with
     *   end time (passed in) in this format: 2017-04-12 16:21:03

     */
    public void handleSegmentEnd() {

        Date endTime = this.getCurrentTime();  //endTime
        long duration = calculateDuration(sModel.getStartTime(), endTime); //@todo not sure if long is right format here
        sModel.setStopTime(endTime);
        sModel.setSegmentDuration(duration);
        //update segment: w/ id from segmentInProcessID
        Toast.makeText(sView, "stopTime & segmentDuration updated", Toast.LENGTH_SHORT).show(); //@todo Toast remove for unitTest
        return;
    }


    /**
     * Calculate Duration
     * calculate the duration of the flight segment by subtracting the start time from the end time
     * Format of duration: 15.00
     * using .getTime() @https://www.geeksforgeeks.org/date-class-java-examples/
     *   long getTime() : Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this Date object.
     */
    public long calculateDuration(Date startTime, Date endTime) {
        long duration = endTime.getTime() - startTime.getTime(); //for testing, actual duration should be difference between 2 times.
        Toast.makeText(sView, "duration is: " + duration, Toast.LENGTH_SHORT).show(); //@todo Toast remove for unitTest
        return duration;
    }

    /**
     *  Get Segment In Progress ID
     */
    public int getSegmentInProgressID() {
        return segmentInProgressID;
    }

    /**
     * Set the Segment In Progress ID
     * @param segmentID
     */
    public void setSegmentInProgressID(int segmentID) {
        this.segmentInProgressID = segmentID;
    }

    public  Date getCurrentTime() {
        //timer should start
        //get current time and return it
        Date currentTime = Calendar.getInstance().getTime();
        /* Not sure format of currentTime*/
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        String test = sdf.format(currentTime);

//        Log.d("startTime", test);
        return currentTime;
    }


}
