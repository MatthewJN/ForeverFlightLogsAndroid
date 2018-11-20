package com.foreverflightlogs.foreverflightlogs;


import android.content.Context;
import android.util.Log;

import java.util.Date;


public class FlightPresenter implements Syncable {

    // Used for testing
    public Date startDate;

    private Flight flight;

    /**
     * The flightID of the current flight.
     */
    private long flightID;

    /**
     * The duration of the flight, in seconds.
     */
    private long flightDuration;

    /**
     * Default Constructor:
     * Not available to be called.
     */
    private FlightPresenter() { }

    /**
     * Non-Default Constructor:
     * Pass in a flightID to instantiate the FlightPresenter with the flight desired.
     * @param flightID The flightID assigned by the model.
     */
    public FlightPresenter(long flightID, Context context) {
        FlightDbHelper flightDbHelper = new FlightDbHelper(context);
        flight = flightDbHelper.getFlight(flightID, context);
        this.flightID = flight.getFlightID();
    }

    /**
     * Non-Default Constructor
     * Called when a new flight is starting. Pass in the origin, aircraft, and date.
     *
     * @param origin The 3 character airport code.
     * @param aircraft The aircraft tail number.
     * @param startDate The start date (usually the exact time the start button was pressed).
     */
    public FlightPresenter(String origin, String aircraft, Date startDate, Context context) {
        FlightDbHelper flightDbHelper = new FlightDbHelper(context);
        flight = flightDbHelper.insertNewFlight(origin, aircraft, startDate, context);
        flightID = flight.getFlightID();
        flight.setSolo(true);
        flight.setDestination("LBA");
        Log.i("ORIGINTEST", flight.getOrigin());
    }

    /**
     * endFlight
     * End the current flight.
     */
    public void endFlight() {
        syncData();
    }

    public long getFlightID() {
        return flightID;
    }

    /**
     * getFlightDuration
     * Provides the duration of the flight. If flight was not ended or has not finished
     * it returns the current duration at the moment the request was made.
     * @return The flight duration in seconds.
     */
    public long getFlightDuration() {
        return new Date().getTime() - startDate.getTime();
    }

    /**
     * The remarks that can be added to the end of a flight log.
     * @param remarksText The text of the remarks.
     */
    public void addRemarks(String remarksText) {

    }

    /**
     * Store whether it is a cross country flight
     * API requires 1 if true, 0 if false
     * @param crossCountry boolean
     *    to verify only 1 or 0, boolean param will be taken and then correct value stored
     * @author: Sheri
     */
    public void setIsCrossCountry(boolean crossCountry ) {
        if (crossCountry){
            //set value for cross country as 1
        }
        else {
            //set value for cross country to 0
        }
        return;
    }

    /**
     * Store whether it is a solo flight
     * API requires 1 if true, 0 if false
     * @param solo boolean
     *    to verify only 1 or 0, boolean param will be taken and then correct value stored
     * @author: Sheri
     */
    public void setIsSolo(boolean solo ) {
        if (solo){
            //set value for cross country as 1
        }
        else {
            //set value for cross country to 0
        }
        return;
    }

    /**
     * syncData
     * Synchronises the data with the ForeverFlightLog server.
     * Typically called when a flight ends, but left public in case that fails and
     * the app needs to try again at a later time.
     * @return A boolean to indicate if the sync was successful.
     */
    @Override
    public boolean syncData() {
        return false;
    }
}
