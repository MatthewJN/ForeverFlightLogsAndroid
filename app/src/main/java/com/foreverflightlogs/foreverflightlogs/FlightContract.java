package com.foreverflightlogs.foreverflightlogs;

import android.provider.BaseColumns;

public final class FlightContract {
    private FlightContract() {}

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FlightEntry.TABLE_NAME + " (" +
                    FlightEntry._ID + " INTEGER PRIMARY KEY," +
                    FlightEntry.COLUMN_NAME_ORIGIN + " TEXT," +
                    FlightEntry.COLUMN_NAME_DESTINATION + " TEXT)" +
                    FlightEntry.COLUMN_NAME_STARTDATE + " TEXT)" +
                    FlightEntry.COLUMN_NAME_ENDDATE + " TEXT)" +
                    FlightEntry.COLUMN_NAME_AIRCRAFT + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FlightEntry.TABLE_NAME;

    public static class FlightEntry implements BaseColumns {
        public static final String TABLE_NAME = "flight";
        public static final String COLUMN_NAME_ORIGIN = "origin";
        public static final String COLUMN_NAME_DESTINATION = "destination";
        public static final String COLUMN_NAME_STARTDATE = "startDate";
        public static final String COLUMN_NAME_ENDDATE = "endDate";
        public static final String COLUMN_NAME_AIRCRAFT = "aircraft";
    }
}
