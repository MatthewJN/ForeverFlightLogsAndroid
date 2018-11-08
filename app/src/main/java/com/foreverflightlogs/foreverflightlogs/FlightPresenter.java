package com.foreverflightlogs.foreverflightlogs;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.Date;

public class FlightPresenter implements Syncable {

    // Used for testing
    public Date startDate;

    /**
     * The flightID of the current flight.
     */
    private int flightID;

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
    public FlightPresenter(int flightID, Context context) {

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

        // Create a new model representing this flight
        // Get an ID and set the private flightID.

        FlightDbHelper mDbHelper = new FlightDbHelper(context);


        flightID = 0;
        this.startDate = startDate;

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FlightContract.FlightEntry.COLUMN_NAME_STARTDATE, "Date");
        long newRowID = db.insert(FlightContract.FlightEntry.TABLE_NAME, null, values);



    }

    /**
     * endFlight
     * End the current flight.
     */
    public void endFlight() {
        syncData();
    }

    public int getFlightID() {
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
