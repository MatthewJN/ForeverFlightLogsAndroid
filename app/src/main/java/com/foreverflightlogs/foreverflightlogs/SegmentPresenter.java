package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SegmentPresenter {

  FlightDbHelper flightDbHelper;

  // The segment that is currently instantiated.
  public Segment segment;

  // All of the segments associated with the current flight.
  public List<Segment> segments;

  /**
   * Default Constructor: Nothing to be initialized
   */
  private SegmentPresenter() {
  }

  /**
   * SegmentPresenter Non-Default Constructor Pass in the associated flightId and Context of the
   * activity.
   *
   * @param context The context.
   */
  SegmentPresenter(Context context) {
    flightDbHelper = new FlightDbHelper(context);
  }

  /**
   * Non-Default constructor used for getting all segments for an activity.
   *
   * @param flightID The flight.
   * @param context The context
   */
  SegmentPresenter(long flightID, Context context) {
    flightDbHelper = new FlightDbHelper(context);
    segments = flightDbHelper.getAllSegments(flightID, context);
  }

  SegmentPresenter(long flightID, long segmentID, Context context) {
    flightDbHelper = new FlightDbHelper(context);
    segment = flightDbHelper.getSegment(segmentID, context);
  }

  /**
   * startSegment: Used to start a new segment.
   *
   * @param flightID The flight the segment is associated with.
   * @param context The context.
   */
  public void startSegment(long flightID, Context context) {
    segment = flightDbHelper.insertNewSegment(flightID, context);
    segments = flightDbHelper.getAllSegments(flightID, context);
  }

//    /**
//     * Calculate Duration
//     * calculate the duration of the flight segment by subtracting the start time from the end time
//     * Format of duration: 15.00
//     * using .getTime() @https://www.geeksforgeeks.org/date-class-java-examples/
//     *   long getTime() : Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this Date object.
//     */
//    public long getSegmentDuration() {
//        if (segment.getEndDate() == null) {
//            return segment.getEndDate().getTime() - segment.getStartDate().getTime();
//        } else {
//            return new Date().getTime() - segment.getStartDate().getTime();
//        }
//    }

  /**
   * getCurrentTime: Returns the current time in the specified format.
   *
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

//    /**
//     * Use this for logic for entire segment creation process
//     * Get start time, create segment with time, update model
//     * sets the returned segmentID in segmentInProgressID
//     */
//    public void handleSegmentStart() {
//        Date startTime = this.getCurrentTime();
//
//       // int segmentID = startSegment(startTime);
//       // setSegmentInProgressID(segmentID);
//        return;
//    }

}
