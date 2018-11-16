package com.foreverflightlogs.foreverflightlogs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
     * @return Returns the ID of the entry in the DB.
     */
    public long insertNewFlight(String origin, String aircraft, Date startDate) {
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

        return newRowID;
    }

    public FlightContract.FlightEntry getFlight(long flightId) {
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
        FlightContract.FlightEntry flightEntry;

        while (cursor.moveToNext()) {
            
            Log.i("ZTHEFLIGHTID", cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry._ID)));
            Log.i("ZTHEORIGIN", cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_ORIGIN)));

            if (!cursor.isNull(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_DESTINATION))) {
                Log.i("ZTHEDESTINATION", cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_DESTINATION)));
            }

            //Log.i("ZTHEDESTINATION", cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_DESTINATION)));



            //Log.i("ZTHESTARTDATE", cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_STARTDATE)));
            Log.i("ZTHEAIRCRAFT", cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_AIRCRAFT)));
            //Log.i("ZTHEENGDATE", cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_ENDDATE)));

            //Log.i("ZTHEHASSYNCED", cursor.getInt(FlightContract.FlightEntry.COLUMN_NAME_HASSYNCED);


            //Log.i("ZTHEHASSYNCED", cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_HASSYNCED)));
            //Log.i("ZTHESOLO", cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_SOLO)));
            //Log.i("ZTHECROSSCOUNTRY", cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_CROSSCOUNTRY)));
            //Log.i("ZTHEREMARKS", cursor.getString(cursor.getColumnIndex(FlightContract.FlightEntry.COLUMN_NAME_REMARKS)));
        }

        cursor.close();

        return null;
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