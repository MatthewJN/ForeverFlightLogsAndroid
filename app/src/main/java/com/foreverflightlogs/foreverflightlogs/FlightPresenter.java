package com.foreverflightlogs.foreverflightlogs;


import android.content.Context;
import java.util.Date;
import java.util.List;


public class FlightPresenter {


  /**
   * The current flight.
   */
  public Flight flight;
  public List<Flight> flights;

  /**
   * Default Constructor: Not available to be called.
   */
  private FlightPresenter() {
  }

  /**
   * Non-Default Constructor:
   *
   * Pass in a flightID to instantiate the FlightPresenter with the flight desired.
   *
   * @param flightID The flightID assigned by the model.
   */
  public FlightPresenter(long flightID, Context context) {
    FlightDbHelper flightDbHelper = new FlightDbHelper(context);
    flight = flightDbHelper.getFlight(flightID, context);
  }

  /**
   * Non-Default Constructor:
   *
   * Called when a new flight is starting. Pass in the origin, aircraft, and date.
   *
   * @param origin The 3 character airport code.
   * @param aircraft The aircraft tail number.
   * @param startDate The start date (usually the exact time the start button was pressed).
   */
  public FlightPresenter(String origin, String destination, String aircraft, Date startDate,
      Context context) {
    FlightDbHelper flightDbHelper = new FlightDbHelper(context);
    flight = flightDbHelper.insertNewFlight(origin, destination, aircraft, startDate, context);
    flights = flightDbHelper.getAllFlightsOfType(false, false, context);
  }

  /**
   * Non-Default Constructor
   * @param hasSynced Do you want to initialise for flights that have synced? Pass null if you don't care.
   * @param inProgress Do you want flights that are in progress? Pass null if you don't care.
   * @param context The context.
   */
  public FlightPresenter(Boolean hasSynced, Boolean inProgress, Context context) {
    FlightDbHelper flightDbHelper = new FlightDbHelper(context);
    flights = flightDbHelper.getAllFlightsOfType(hasSynced, inProgress, context);
  }

  /**
   * getFlightID:
   *
   * @return The current ID of the running flight.
   */
  public long getFlightID() {
    return flight.getFlightID();
  }

  /**
   * getFlightDuration Provides the duration of the flight. If flight was not ended or has not
   * finished it returns the current duration at the moment the request was made.
   *
   * @return The flight duration in seconds.
   */
  public long getFlightDuration() {
    if (flight.getEndDate() == null) {
      return flight.getEndDate().getTime() - flight.getStartDate().getTime();
    } else {
      return new Date().getTime() - flight.getStartDate().getTime();
    }
  }

}
