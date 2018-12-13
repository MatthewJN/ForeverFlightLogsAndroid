package com.foreverflightlogs.foreverflightlogs;

import android.provider.BaseColumns;

public final class AirportContract {

  private AirportContract() {
  }

  /**
   * The contract.
   */
  public static class AirportEntry implements BaseColumns {
    public static final String TABLE_NAME = "airport";
    public static final String COLUMN_NAME_AIRPORT_CODE = "airportCode";
    public static final String COLUMN_NAME_AIRPORT_NAME = "airportName";
  }
}
