package com.foreverflightlogs.foreverflightlogs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FlightDbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_FLIGHT_ENTRIES =
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

    private static final String SQL_CREATE_SEGMENT_ENTRIES =
            "CREATE TABLE " + SegmentContract.SegmentEntry.TABLE_NAME + " (" +
                    SegmentContract.SegmentEntry._ID + " INTEGER PRIMARY KEY, " +
                    SegmentContract.SegmentEntry.COLUMN_NAME_STARTDATE + " DATE, " +
                    SegmentContract.SegmentEntry.COLUMN_NAME_ENDDATE + " DATE, " +
                    SegmentContract.SegmentEntry.COLUMN_NAME_PILOTINCOMMAND + " BOOLEAN," +
                    SegmentContract.SegmentEntry.COLUMN_NAME_DUALHOURS + " BOOLEAN," +
                    SegmentContract.SegmentEntry.COLUMN_NAME_SIMULATEDINSTRUMENTS + " BOOLEAN," +
                    SegmentContract.SegmentEntry.COLUMN_NAME_VISUALFLIGHT + " BOOLEAN," +
                    SegmentContract.SegmentEntry.COLUMN_NAME_INSTRUMENTFLIGHT + " BOOLEAN," +
                    SegmentContract.SegmentEntry.COLUMN_NAME_NIGHT + " BOOLEAN," +
                    SegmentContract.SegmentEntry.COLUMN_NAME_FLIGHTID + " INTEGER," +
                    " FOREIGN KEY(" + SegmentContract.SegmentEntry.COLUMN_NAME_FLIGHTID +
                    ") REFERENCES " + FlightContract.FlightEntry.TABLE_NAME + "(" +
                    FlightContract.FlightEntry._ID + "))";

    private static final String SQL_DELETE_FLIGHT_ENTRIES =
            "DROP TABLE IF EXISTS " + FlightContract.FlightEntry.TABLE_NAME;

    private static final String SQL_DELETE_SEGMENT_ENTRIES =
            "DROP TABLE IF EXISTS " + SegmentContract.SegmentEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "Flight.db";

    public FlightDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FLIGHT_ENTRIES);
        db.execSQL(SQL_CREATE_SEGMENT_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_FLIGHT_ENTRIES);
        db.execSQL(SQL_DELETE_SEGMENT_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * Inserts a new flight in to the DB.
     * @param origin The origin of the flight (the 3 digit airport code)
     * @param aircraft The N number of the plane
     * @param startDate The start time of the flight
     * @return Returns the Flight object.
     */
    public Flight insertNewFlight(String origin, String aircraft, Date startDate, Context context) {
        String date = getStringFromDate(startDate);

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

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_ORIGIN))) {
                origin = cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_ORIGIN));
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_DESTINATION))) {
                destination = cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_DESTINATION));
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_STARTDATE))) {
                startDate = getDateFromString(cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_STARTDATE)));
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_ENDDATE))) {
                endDate = getDateFromString(cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_ENDDATE)));
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
                startDate = getDateFromString(cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_STARTDATE)));
            }

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_ENDDATE))) {
                endDate = getDateFromString(cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_ENDDATE)));
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
        values.put(FlightContract.FlightEntry.COLUMN_NAME_STARTDATE, getStringFromDate(flight.getStartDate()));
        values.put(FlightContract.FlightEntry.COLUMN_NAME_ENDDATE, getStringFromDate(flight.getEndDate()));
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

    public Segment insertNewSegment(Date startDate, long flightId, Context context) {
        String date = getStringFromDate(startDate);

        // Get the database and create content values
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        // The content values to insert
        values.put(SegmentContract.SegmentEntry.COLUMN_NAME_STARTDATE, date);
        values.put(SegmentContract.SegmentEntry.COLUMN_NAME_PILOTINCOMMAND, true);
        values.put(SegmentContract.SegmentEntry.COLUMN_NAME_FLIGHTID, flightId);
        long newRowID = db.insert(SegmentContract.SegmentEntry.TABLE_NAME, null, values);

        return getSegment(newRowID, context);
    }

    public Segment getSegment(long segmentId, Context context) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                SegmentContract.SegmentEntry.COLUMN_NAME_STARTDATE,
                SegmentContract.SegmentEntry.COLUMN_NAME_ENDDATE,
                SegmentContract.SegmentEntry.COLUMN_NAME_PILOTINCOMMAND,
                SegmentContract.SegmentEntry.COLUMN_NAME_DUALHOURS,
                SegmentContract.SegmentEntry.COLUMN_NAME_SIMULATEDINSTRUMENTS,
                SegmentContract.SegmentEntry.COLUMN_NAME_VISUALFLIGHT,
                SegmentContract.SegmentEntry.COLUMN_NAME_INSTRUMENTFLIGHT,
                SegmentContract.SegmentEntry.COLUMN_NAME_NIGHT
        };

        String selection = SegmentContract.SegmentEntry._ID + " = ?";
        String[] selectionArgs = { String.format("%d", segmentId) };
        String sortOrder = SegmentContract.SegmentEntry._ID + " DESC";

        Cursor cursor = db.query(
                SegmentContract.SegmentEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        long id = segmentId;
        Date startDate = null;
        Date endDate = null;
        boolean pilotInCommand = false;
        boolean dualHours = false;
        boolean simulatedInstruments = false;
        boolean visualFlight = false;
        boolean instrumentFlight = false;
        boolean night = false;

        while (cursor.moveToNext()) {

            if (!cursor.isNull(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_STARTDATE))) {
                startDate = getDateFromString(cursor.getString(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_STARTDATE)));
            }

            if (!cursor.isNull(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_ENDDATE))) {
                endDate = getDateFromString(cursor.getString(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_ENDDATE)));
            }

            if (!cursor.isNull(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_PILOTINCOMMAND))) {
                pilotInCommand = cursor.getInt(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_PILOTINCOMMAND)) > 0;
            }

            if (!cursor.isNull(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_DUALHOURS))) {
                dualHours = cursor.getInt(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_DUALHOURS)) > 0;
            }

            if (!cursor.isNull(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_SIMULATEDINSTRUMENTS))) {
                simulatedInstruments = cursor.getInt(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_SIMULATEDINSTRUMENTS)) > 0;
            }

            if (!cursor.isNull(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_VISUALFLIGHT))) {
                visualFlight = cursor.getInt(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_VISUALFLIGHT)) > 0;
            }

            if (!cursor.isNull(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_INSTRUMENTFLIGHT))) {
                instrumentFlight = cursor.getInt(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_INSTRUMENTFLIGHT)) > 0;
            }

            if (!cursor.isNull(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_NIGHT))) {
                night = cursor.getInt(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_NIGHT)) > 0;
            }

        }

        Segment segment = new Segment(id,
                startDate,
                endDate,
                pilotInCommand,
                dualHours,
                simulatedInstruments,
                visualFlight,
                instrumentFlight,
                night,
                context);

        cursor.close();
        return segment;
    }

    public List<Segment> getAllSegments(long flightId, Context context) {
        List<Segment> segments = new ArrayList<Segment>();

        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                SegmentContract.SegmentEntry.COLUMN_NAME_STARTDATE,
                SegmentContract.SegmentEntry.COLUMN_NAME_ENDDATE,
                SegmentContract.SegmentEntry.COLUMN_NAME_PILOTINCOMMAND,
                SegmentContract.SegmentEntry.COLUMN_NAME_DUALHOURS,
                SegmentContract.SegmentEntry.COLUMN_NAME_SIMULATEDINSTRUMENTS,
                SegmentContract.SegmentEntry.COLUMN_NAME_VISUALFLIGHT,
                SegmentContract.SegmentEntry.COLUMN_NAME_INSTRUMENTFLIGHT,
                SegmentContract.SegmentEntry.COLUMN_NAME_NIGHT
        };

        String selection = "SELECT * FROM " + SegmentContract.SegmentEntry.TABLE_NAME + " WHERE " +
                SegmentContract.SegmentEntry.COLUMN_NAME_FLIGHTID + "= \"?\"";
        String[] selectionArgs = { String.format("%d", flightId) };
        String sortOrder = SegmentContract.SegmentEntry._ID + " DESC";

        Cursor cursor = db.query(
                SegmentContract.SegmentEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        while (cursor.moveToNext()) {
            long id = -1;
            Date startDate = null;
            Date endDate = null;
            boolean pilotInCommand = false;
            boolean dualHours = false;
            boolean simulatedInstruments = false;
            boolean visualFlight = false;
            boolean instrumentFlight = false;
            boolean night = false;

            if (!cursor.isNull(cursor.getColumnIndex(SegmentContract.SegmentEntry._ID))) {
                id = cursor.getColumnIndex(SegmentContract.SegmentEntry._ID);
            }

            if (!cursor.isNull(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_STARTDATE))) {
                startDate = getDateFromString(cursor.getString(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_STARTDATE)));
            }

            if (!cursor.isNull(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_ENDDATE))) {
                endDate = getDateFromString(cursor.getString(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_ENDDATE)));
            }

            if (!cursor.isNull(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_PILOTINCOMMAND))) {
                pilotInCommand = cursor.getInt(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_PILOTINCOMMAND)) > 0;
            }

            if (!cursor.isNull(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_DUALHOURS))) {
                dualHours = cursor.getInt(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_DUALHOURS)) > 0;
            }

            if (!cursor.isNull(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_SIMULATEDINSTRUMENTS))) {
                simulatedInstruments = cursor.getInt(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_SIMULATEDINSTRUMENTS)) > 0;
            }

            if (!cursor.isNull(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_VISUALFLIGHT))) {
                visualFlight = cursor.getInt(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_VISUALFLIGHT)) > 0;
            }

            if (!cursor.isNull(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_INSTRUMENTFLIGHT))) {
                instrumentFlight = cursor.getInt(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_INSTRUMENTFLIGHT)) > 0;
            }

            if (!cursor.isNull(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_NIGHT))) {
                night = cursor.getInt(cursor.getColumnIndex(SegmentContract.SegmentEntry.COLUMN_NAME_NIGHT)) > 0;
            }

            Segment segment = new Segment(id,
                    startDate,
                    endDate,
                    pilotInCommand,
                    dualHours,
                    simulatedInstruments,
                    visualFlight,
                    instrumentFlight,
                    night,
                    context);

            segments.add(segment);
        }

        return segments;
    }

    public void updateSegment(Segment segment, Context context) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SegmentContract.SegmentEntry.COLUMN_NAME_STARTDATE, getStringFromDate(segment.getStartDate()));
        values.put(SegmentContract.SegmentEntry.COLUMN_NAME_ENDDATE, getStringFromDate(segment.getEndDate()));
        values.put(SegmentContract.SegmentEntry.COLUMN_NAME_PILOTINCOMMAND, segment.getPilotInCommand());
        values.put(SegmentContract.SegmentEntry.COLUMN_NAME_DUALHOURS, segment.getDualHours());
        values.put(SegmentContract.SegmentEntry.COLUMN_NAME_SIMULATEDINSTRUMENTS, segment.getSimulatedInstruments());
        values.put(SegmentContract.SegmentEntry.COLUMN_NAME_VISUALFLIGHT, segment.getVisualFlight());
        values.put(SegmentContract.SegmentEntry.COLUMN_NAME_INSTRUMENTFLIGHT, segment.getinstrumentFlight());
        values.put(SegmentContract.SegmentEntry.COLUMN_NAME_NIGHT, segment.getNight());

        String selection = FlightContract.FlightEntry._ID + " = ?";
        String[] selectionArgs = { String.format("%d", segment.getSegmentID()) };

        db.update(SegmentContract.SegmentEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public void deleteSegment(Segment segment, Context context) {
        SQLiteDatabase db = getWritableDatabase();

        String selection = FlightContract.FlightEntry._ID + " = ?";
        String[] selectionArgs = { String.format("%d", segment.getSegmentID()) };

        db.delete(FlightContract.FlightEntry.TABLE_NAME,
                selection,
                selectionArgs);
    }

    private String getStringFromDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date == null) {
            return null;
        } else {
            return sdf.format(date);
        }
    }

    private static Date getDateFromString(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date returnDate = new Date();
        try {
            returnDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnDate;
    }
}
