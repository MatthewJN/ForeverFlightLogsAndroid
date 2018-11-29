package com.foreverflightlogs.foreverflightlogs;


import android.content.Context;
import java.util.Date;
import java.util.List;


public class FlightPresenter implements Syncable {


    /**
     * The current flight.
     */
    public Flight flight;
    public List<Flight> flights;

    /**
     * Default Constructor:
     * Not available to be called.
     */
    private FlightPresenter() { }

    /**
     * Non-Default Constructor:
     *
     * Pass in a flightID to instantiate the FlightPresenter with the flight desired.
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
    public FlightPresenter(String origin, String destination, String aircraft, Date startDate, Context context) {
        FlightDbHelper flightDbHelper = new FlightDbHelper(context);
        flight = flightDbHelper.insertNewFlight(origin, destination, aircraft, startDate, context);
        flights = flightDbHelper.getAllFlights(false, context);
    }

    /**
     * endFlight
     * End the current flight.
     */
    public void endFlight() {
        syncData();
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
        // Needs to be handed out to a sync class I believe.
        // That would be responsible for packaging up the data and if succeful, it can return true
        // and then return true back to the user, if they are interested in knowing.
        return false;
    }

    /**
     * getFlightID:
     * @return The current ID of the running flight.
     */
    public long getFlightID() {
        return flight.getFlightID();
    }

    /**
     * getFlightDuration
     * Provides the duration of the flight. If flight was not ended or has not finished
     * it returns the current duration at the moment the request was made.
     * @return The flight duration in seconds.
     */
    public long getFlightDuration() {
        if (flight.getEndDate() == null) {
            return flight.getEndDate().getTime() - flight.getStartDate().getTime();
        } else {
            return new Date().getTime() - flight.getStartDate().getTime();
        }
    }

    private void getAllFlights(boolean synced, Context context) {
        FlightDbHelper dbHelper = new FlightDbHelper(context);
        this.flights =  dbHelper.getAllFlights(synced, context);
    }

}
