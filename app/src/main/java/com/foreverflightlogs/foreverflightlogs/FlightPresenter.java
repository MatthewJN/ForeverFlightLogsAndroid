package com.foreverflightlogs.foreverflightlogs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Debug;
import android.provider.BaseColumns;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(startDate);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FlightContract.FlightEntry.COLUMN_NAME_STARTDATE, date);
        values.put(FlightContract.FlightEntry.COLUMN_NAME_AIRCRAFT, "G-BOAG");
        values.put(FlightContract.FlightEntry.COLUMN_NAME_ORIGIN, "SLC");
        long newRowID = db.insert(FlightContract.FlightEntry.TABLE_NAME, null, values);

        Log.d("SQLTest", mDbHelper.SQL_CREATE_ENTRIES);
//        System.out.println(mDbHelper.SQL_CREATE_ENTRIES);

        db = mDbHelper.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                FlightContract.FlightEntry.COLUMN_NAME_STARTDATE,
        };


        String selection = FlightContract.FlightEntry._ID;
        String[] selectionArgs = { "Date" };

        String sortOrder =
                FlightContract.FlightEntry.COLUMN_NAME_STARTDATE + " DESC";

        Cursor cursor = db.query(
                        FlightContract.FlightEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(FlightContract.FlightEntry._ID));
            itemIds.add(itemId);
        }
        cursor.close();

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
