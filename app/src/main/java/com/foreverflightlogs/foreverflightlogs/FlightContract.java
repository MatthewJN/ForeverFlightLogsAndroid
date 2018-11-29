package com.foreverflightlogs.foreverflightlogs;

import android.provider.BaseColumns;

public final class FlightContract {
    private FlightContract() {}

    /**
     * The contract.
     */
    public static class FlightEntry implements BaseColumns {
        public static final String TABLE_NAME = "flight";
        public static final String COLUMN_NAME_ORIGIN = "origin";
        public static final String COLUMN_NAME_DESTINATION = "destination";
        public static final String COLUMN_NAME_STARTDATE = "startDate";
        public static final String COLUMN_NAME_ENDDATE = "endDate";
        public static final String COLUMN_NAME_AIRCRAFT = "aircraft";
        public static final String COLUMN_NAME_HASSYNCED = "hasSynced";
        public static final String COLUMN_NAME_CROSSCOUNTRY = "crossCountry";
        public static final String COLUMN_NAME_SOLO = "solo";
        public static final String COLUMN_NAME_REMARKS = "remarks";
    }
}
