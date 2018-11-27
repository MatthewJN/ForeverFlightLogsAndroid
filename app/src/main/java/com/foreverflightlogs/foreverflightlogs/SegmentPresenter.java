package com.foreverflightlogs.foreverflightlogs;
import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SegmentPresenter {
    //public int flightID; //for testing
//    public int segmentID;
//    SegmentActivity sView;
//    SegmentModel sModel;
//    String testMsg;

//    public int segmentInProgressID; //get this from flightSegment model?

    // The segment that is currently instantiated.
    public Segment segment;

    // All of the segments associated with the current flight.
    public List<Segment> segments;

    /**
     * Default Constructor:
     * Nothing to be initialized
     */
    private SegmentPresenter() { }

    /**
     * SegmentPresenter Non-Default Constructor
     * Pass in the associated flightId and Context of the activity.
     * @param flightID The current flight that the segment is associated with.
     * @param context The context.
     */
    SegmentPresenter(long flightID, Context context) {
        FlightDbHelper flightDbHelper = new FlightDbHelper(context);
        segment = flightDbHelper.insertNewSegment(flightID, context);
    }

//    /**
//     *
//     * Called when a new segment is starting.
//     * API requires auth & bool (isUserPilotInCommand = 1 or 0) params
//     * Required body for post:
//     *      accountID of user
//     *      flightID
//     *
//     *      segmentStartTime requires this format: 2017-04-12 16:21:03
//     * @param startTime
//     *
//     *
//     */
//    public void startSegment(Date startTime) {
//
//        segment.setStartDate(startTime);
//
////        //@todo remove toast once SQL is hooked up
////        Toast.makeText(sView, "startTime stored in segmentModel object", Toast.LENGTH_SHORT).show(); //@todo Toast remove for unitTest
////        //connect to SQL & create segment
////        //verify segment has been created and throw error if not
////        //set segmentInProgress
////
////        int segmentID = -1;
////        return segmentID;
//    }

//    public void endSegment(Date endTime) {
//        segment.setEndDate(endTime);
//    }
    
    /**
     * Calculate Duration
     * calculate the duration of the flight segment by subtracting the start time from the end time
     * Format of duration: 15.00
     * using .getTime() @https://www.geeksforgeeks.org/date-class-java-examples/
     *   long getTime() : Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this Date object.
     */
    public long getSegmentDuration() {
        return segment.getEndDate().getTime() - segment.getStartDate().getTime(); //for testing, actual duration should be difference between 2 times.
    }

    /**
     * getCurrentTime:
     * Returns the current time in the specified format.
     * @return Returns a Date.
     */
    public Date getCurrentTime() {
        Date currentTime = Calendar.getInstance().getTime();
        /* Not sure format of currentTime*/
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        String test = sdf.format(currentTime);
        return currentTime;
    }

    // All segments associated with the currently flight ID will be
    // set in the public property called "segments" as a list.
    private void getAllSegmentsForFlight(long flightId, Context context) {
        FlightDbHelper dbHelper = new FlightDbHelper(context);
        this.segments = dbHelper.getAllSegments(flightId, context);
    }

}
