package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
}
