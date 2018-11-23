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
        String date = DateFormatter.getStringFromDate(startDate);

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

        //List itemIds = new ArrayList<>();
        //Flight flight = new Flight(context);

        long id = flightId;
        String origin = "";
        String destination = "";
        Date startDate = null;
        Date endDate = null;
        String aircraft = "";
        boolean hasSynced = false;
        boolean crosscountry = false;
        boolean solo = false;
        String remarks = "";

        while (cursor.moveToNext()) {

//            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry._ID))) {
//                id = cursor.getColumnIndex(FlightContract.FlightEntry._ID);
//            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_ORIGIN))) {
                origin = cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_ORIGIN));
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_DESTINATION))) {
                destination = cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_DESTINATION));
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_STARTDATE))) {
                startDate = DateFormatter.getDateFromString(cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_STARTDATE)));
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_ENDDATE))) {
                endDate = DateFormatter.getDateFromString(cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_ENDDATE)));
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_AIRCRAFT))) {
                aircraft = cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_AIRCRAFT));
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_HASSYNCED))) {
                hasSynced = cursor.getInt(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_HASSYNCED)) > 0;
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_CROSSCOUNTRY))) {
                crosscountry = cursor.getInt(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_CROSSCOUNTRY)) > 0;
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_SOLO))) {
                solo = cursor.getInt(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_SOLO)) > 0;
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_REMARKS))) {
                remarks = cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_REMARKS));
            }
        }

        Flight flight = new Flight(id,
                origin,
                destination,
                startDate,
                endDate,
                aircraft,
                hasSynced,
                crosscountry,
                solo,
                remarks,
                context);

        cursor.close();
        return flight;
    }

    public List<Flight> getAllFlights(boolean synced, Context context) {
        List<Flight> flights = new ArrayList<Flight>();

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

        String selection = "SELECT * FROM " + FlightContract.FlightEntry.TABLE_NAME + " WHERE " + FlightContract.FlightEntry.COLUMN_NAME_HASSYNCED + "= \"?\"";
        String[] selectionArgs = { String.format("%d", synced) };
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

        while (cursor.moveToNext()) {
            long id = -1;
            String origin = "";
            String destination = "";
            Date startDate = null;
            Date endDate = null;
            String aircraft = "";
            boolean hasSynced = false;
            boolean crosscountry = false;
            boolean solo = false;
            String remarks = "";

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry._ID))) {
                id = cursor.getColumnIndex(FlightContract.FlightEntry._ID);
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_ORIGIN))) {
                origin = cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_ORIGIN));
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_DESTINATION))) {
                destination = cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_DESTINATION));
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_STARTDATE))) {
                startDate = DateFormatter.getDateFromString(cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_STARTDATE)));
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_ENDDATE))) {
                endDate = DateFormatter.getDateFromString(cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_ENDDATE)));
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_AIRCRAFT))) {
                aircraft = cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_AIRCRAFT));
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_HASSYNCED))) {
                hasSynced = cursor.getInt(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_HASSYNCED)) > 0;
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_CROSSCOUNTRY))) {
                crosscountry = cursor.getInt(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_CROSSCOUNTRY)) > 0;
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_SOLO))) {
                solo = cursor.getInt(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_SOLO)) > 0;
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_REMARKS))) {
                remarks = cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_REMARKS));
            }

            Flight flight = new Flight(id,
                    origin,
                    destination,
                    startDate,
                    endDate,
                    aircraft,
                    hasSynced,
                    crosscountry,
                    solo,
                    remarks,
                    context);

            flights.add(flight);
        }

        return flights;
    }

    public void updateFlight(Flight flight, Context context) {
        Log.i("UPDATEDDEST", flight.getDestination());

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FlightContract.FlightEntry.COLUMN_NAME_ORIGIN, flight.getOrigin());
        values.put(FlightContract.FlightEntry.COLUMN_NAME_DESTINATION, flight.getDestination());
        values.put(FlightContract.FlightEntry.COLUMN_NAME_STARTDATE, DateFormatter.getStringFromDate(flight.getStartDate()));
        values.put(FlightContract.FlightEntry.COLUMN_NAME_ENDDATE, DateFormatter.getStringFromDate(flight.getEndDate()));
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