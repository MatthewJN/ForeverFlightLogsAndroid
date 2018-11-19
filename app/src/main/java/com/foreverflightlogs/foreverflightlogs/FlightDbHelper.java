package com.foreverflightlogs.foreverflightlogs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FlightDbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FlightContract.FlightEntry.TABLE_NAME + " (" +
                    FlightContract.FlightEntry._ID + " INTEGER PRIMARY KEY, " +
                    FlightContract.FlightEntry.COLUMN_NAME_ORIGIN + " TEXT, " +
                    FlightContract.FlightEntry.COLUMN_NAME_DESTINATION + " TEXT, " +
                    FlightContract.FlightEntry.COLUMN_NAME_STARTDATE + " DATE, " +
                    FlightContract.FlightEntry.COLUMN_NAME_ENDDATE + " DATE, " +
                    FlightContract.FlightEntry.COLUMN_NAME_AIRCRAFT + " TEXT," +
                    FlightContract.FlightEntry.COLUMN_NAME_CROSSCOUNTRY + " BOOLEAN," +
                    FlightContract.FlightEntry.COLUMN_NAME_SOLO + " BOOLEAN," +
                    FlightContract.FlightEntry.COLUMN_NAME_REMARKS + " TEXT," +
                    FlightContract.FlightEntry.COLUMN_NAME_HASSYNCED + " BOOLEAN)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FlightContract.FlightEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "Flight.db";

    public FlightDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * Inserts a new flight in to the DB.
     * @param origin The origin of the flight (the 3 digit airport code)
     * @param aircraft The N number of the plane
     * @param date The start time of the flight
     * @return Returns the Flight object.
     */
    public Flight insertNewFlight(String origin, String aircraft, Date startDate, Context context) {
        // Makes a compatible date for SQL.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(startDate);

        // Get the database and create content values
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        // The content values to insert
        values.put(FlightContract.FlightEntry.COLUMN_NAME_ORIGIN, origin);
        values.put(FlightContract.FlightEntry.COLUMN_NAME_AIRCRAFT, aircraft);
        values.put(FlightContract.FlightEntry.COLUMN_NAME_STARTDATE, date);
        values.put(FlightContract.FlightEntry.COLUMN_NAME_HASSYNCED, false);
        long newRowID = db.insert(FlightContract.FlightEntry.TABLE_NAME, null, values);

        return getFlight(newRowID, context);
    }

    public Flight getFlight(long flightId, Context context) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                FlightContract.FlightEntry.COLUMN_NAME_ORIGIN,
                FlightContract.FlightEntry.COLUMN_NAME_DESTINATION,
                FlightContract.FlightEntry.COLUMN_NAME_STARTDATE,
                FlightContract.FlightEntry.COLUMN_NAME_ENDDATE,
                FlightContract.FlightEntry.COLUMN_NAME_AIRCRAFT,
                FlightContract.FlightEntry.COLUMN_NAME_HASSYNCED,
                FlightContract.FlightEntry.COLUMN_NAME_CROSSCOUNTRY,
                FlightContract.FlightEntry.COLUMN_NAME_SOLO,
                FlightContract.FlightEntry.COLUMN_NAME_REMARKS
        };

        String selection = FlightContract.FlightEntry._ID + " = ?";
        String[] selectionArgs = { String.format("%d", flightId) };
        String sortOrder = FlightContract.FlightEntry._ID + " DESC";

        Cursor cursor = db.query(
                FlightContract.FlightEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        List itemIds = new ArrayList<>();
        Flight flight = new Flight(context);

        while (cursor.moveToNext()) {

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry._ID))) {
                flight.setFlightID(cursor.getColumnIndex(FlightContract.FlightEntry._ID));
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_ORIGIN))) {
                flight.setOrigin(cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_ORIGIN)));
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_DESTINATION))) {
                flight.setDestination(cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_DESTINATION)));
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_STARTDATE))) {
                // TODO: 20/11/2018 Get the date
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_ENDDATE))) {
                // TODO: 20/11/2018 Get the date
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_AIRCRAFT))) {
                flight.setAircraft(cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_AIRCRAFT)));
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_HASSYNCED))) {
                flight.setHasSynced(cursor.getInt(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_HASSYNCED)) > 0);
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_CROSSCOUNTRY))) {
                flight.setCrosscountry(cursor.getInt(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_CROSSCOUNTRY)) > 0);
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_SOLO))) {
                flight.setSolo(cursor.getInt(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_SOLO)) > 0);
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_REMARKS))) {
                flight.setAircraft(cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_REMARKS)));
            }
            //Log.i("ZTHESTARTDATE", cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_STARTDATE)));
            //Log.i("ZTHEENGDATE", cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_ENDDATE)));
        }

        cursor.close();
        return flight;
    }

    public void updateFlight(Flight flight, Context context) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FlightContract.FlightEntry.COLUMN_NAME_ORIGIN, flight.getOrigin());
        values.put(FlightContract.FlightEntry.COLUMN_NAME_DESTINATION, flight.getDestination());
        //values.put(FlightContract.FlightEntry.COLUMN_NAME_STARTDATE, flight.getStartDate());
        //values.put(FlightContract.FlightEntry.COLUMN_NAME_ENDDATE, flight.getEndDate());
        values.put(FlightContract.FlightEntry.COLUMN_NAME_AIRCRAFT, flight.getAircraft());
        values.put(FlightContract.FlightEntry.COLUMN_NAME_HASSYNCED, flight.getHasSynced());
        values.put(FlightContract.FlightEntry.COLUMN_NAME_CROSSCOUNTRY, flight.getCrosscountry());
        values.put(FlightContract.FlightEntry.COLUMN_NAME_SOLO, flight.getSolo());
        values.put(FlightContract.FlightEntry.COLUMN_NAME_REMARKS, flight.getRemarks());

        String selection = FlightContract.FlightEntry._ID + " = ?";
        String[] selectionArgs = { String.format("%d", flight.getFlightID()) };

        db.update(FlightContract.FlightEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public void deleteFlight(Flight flight, Context context) {
        SQLiteDatabase db = getWritableDatabase();

        String selection = FlightContract.FlightEntry._ID + " = ?";
        String[] selectionArgs = { String.format("%d", flight.getFlightID()) };

        db.delete(FlightContract.FlightEntry.TABLE_NAME,
                selection,
                selectionArgs);
    }
}

/**



        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
        Log.d("SQLOutput", cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_AIRCRAFT)));
        long itemId = cursor.getLong(
        cursor.getColumnIndexOrThrow(FlightContract.FlightEntry._ID));
        itemIds.add(itemId);
        Log.d("NewItem", String.valueOf(itemId));
        }
        cursor.close();
 **/