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
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

public class AirportDbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_AIRPORT_ENTRIES =
            "CREATE TABLE " + AirportContract.AirportEntry.TABLE_NAME + " (" +
                    AirportContract.AirportEntry._ID + " INTEGER PRIMARY KEY, " +
                    AirportContract.AirportEntry.COLUMN_NAME_AIRPORT_CODE + " TEXT, " +
                    AirportContract.AirportEntry.COLUMN_NAME_AIRPORT_NAME + " TEXT)";

    private static final String SQL_DELETE_AIRPORT_ENTRIES =
            "DROP TABLE IF EXISTS " + FlightContract.FlightEntry.TABLE_NAME;

    /**
     * The database version. Increment DB version when DB schema has changed.
     */
    public static final int DATABASE_VERSION = 1;

    /**
     * The name of the database stored on the disk.
     */
    public static final String DATABASE_NAME = "Airport.db";

    /**
     * Non-Default constructor that accepts a context.
     * @param context
     */
    public AirportDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * onCreate: Creates a new database by executing the SQL presented.
     * @param db The name of the DB to update.
     */
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_AIRPORT_ENTRIES);
    }

    /**
     * onUpgrade:
     * Used to upgrade the database.
     * @param db The DB to be upgraded.
     * @param oldVersion The version number of the old DB.
     * @param newVersion The version number of the new DB.
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_AIRPORT_ENTRIES);
        onCreate(db);
    }

    /**
     * onDowngrade:
     * Used to downgrade the DB.
     * @param db The DB to be downgraded.
     * @param oldVersion The old version of the DB number.
     * @param newVersion The new version of the DB number.
     */
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void insertAirports(Map<String, String> airports, Context context) {
        // Get the database and create content values
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        db.beginTransaction();

        for (String key : airports.keySet()) {
            String sql = "INSERT INTO " + AirportContract.AirportEntry.TABLE_NAME + " (" +
                    AirportContract.AirportEntry.COLUMN_NAME_AIRPORT_CODE + ", " +
                    AirportContract.AirportEntry.COLUMN_NAME_AIRPORT_NAME + ") " +
                    " VALUES (\"" + key + "\", \"" + airports.get(key) + "\")";
            db.execSQL(sql);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public Map<String, String> getAllAirports(Context context) {
        return null;
    }


}
