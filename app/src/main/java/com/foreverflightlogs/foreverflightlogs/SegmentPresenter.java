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

}
