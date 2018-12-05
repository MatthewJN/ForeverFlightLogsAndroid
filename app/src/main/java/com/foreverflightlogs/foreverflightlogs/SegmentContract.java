package com.foreverflightlogs.foreverflightlogs;

import android.provider.BaseColumns;

public final class SegmentContract {
    private SegmentContract() {}

    public static class SegmentEntry implements BaseColumns {
        public static final String TABLE_NAME = "segment";
        public static final String COLUMN_NAME_STARTDATE = "startDate";
        public static final String COLUMN_NAME_ENDDATE = "endDate";
        public static final String COLUMN_NAME_PILOTINCOMMAND = "pilotInCommand";
        public static final String COLUMN_NAME_DUALHOURS = "dualHours";
        public static final String COLUMN_NAME_SIMULATEDINSTRUMENTS = "simulatedInstruments";
        public static final String COLUMN_NAME_VISUALFLIGHT = "visualFlight";
        public static final String COLUMN_NAME_INSTRUMENTFLIGHT = "instrumentFlight";
        public static final String COLUMN_NAME_NIGHT = "night";
        public static final String COLUMN_NAME_ISCOMPLETED = "isCompleted";
        public static final String COLUMN_NAME_FLIGHTID = "flightId";
    }

}
