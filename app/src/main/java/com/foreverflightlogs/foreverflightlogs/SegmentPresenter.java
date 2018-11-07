package com.foreverflightlogs.foreverflightlogs;

import java.util.Date;

public class SegmentPresenter {
    public int flightID; //for testing
    public int segmentID;

    public int segmentInProgressID; //get this from flightSegment model?

    /**
     * Default Constructor:
     * Not available to be called.
     */
    private SegmentPresenter() { }

    /**
     * Non-Default Constructor:
     * Pass in a segmentID to instantiate the SegmentPresenter with the segment desired.
     * @param segmentID The segmentID assigned by the model.
     */
    public SegmentPresenter(int segmentID) {

    }

    /**
     * Non-Default Constructor
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
    public void SegmentPresenter( Date startTime) {
        // Create a new model representing this segment

        //verify segment has been created and throw error if not
        //set segmentInProgress
        //do we need to return the segmentID?

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

    /**
     * End flight segment
     *   update current flight segment with
     *   end time (passed in) in this format: 2017-04-12 16:21:03
     *   and any options selected
     *   by user with toggles on segmentActivity. (or is this done in start?
     * @param endTimer
     */
    public void endSegment(Date endTimer) {
        //update segment:
            //endTime
            // duration - may need to cast to a float?
            // all toggles
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
       //endTime - startTime = duration
        return duration;
    }
}
