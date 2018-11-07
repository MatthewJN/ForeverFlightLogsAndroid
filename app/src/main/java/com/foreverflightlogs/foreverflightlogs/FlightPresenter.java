package com.foreverflightlogs.foreverflightlogs;

import java.util.Date;

public class FlightPresenter implements Syncable {

    /**
     * The flightID of the current flight.
     */
    private int flightID;

    /**
     * The duration of the flight, in seconds.
     */
    private int flightDuration;

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
    public FlightPresenter(int flightID) {

    }

    /**
     * Non-Default Constructor
     * Called when a new flight is starting. Pass in the origin, aircraft, and date.
     *
     * @param origin The 3 character airport code.
     * @param aircraft The aircraft tail number.
     * @param startDate The start date (usually the exact time the start button was pressed).
     */
    public FlightPresenter(String origin, String aircraft, Date startDate) {
        // Create a new model representing this flight
        // Get an ID and set the private flightID.
    }

    /**
     * endFlight
     * End the current flight.
     */
    public void endFlight() {
        syncData();
    }

    public int getFlightID() {
        return 0;
    }

    /**
     * getFlightDuration
     * Provides the duration of the flight. If flight was not ended or has not finished
     * it returns the current duration at the moment the request was made.
     * @return The flight duration in seconds.
     */
    public int getFlightDuration() {
        return flightDuration;
    }

    /**
     * The remarks that can be added to the end of a flight log.
     * @param remarksText The text of the remarks.
     */
    public void addRemarks(String remarksText) {

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
