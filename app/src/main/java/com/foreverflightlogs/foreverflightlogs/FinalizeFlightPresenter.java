package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;

public class FinalizeFlightPresenter {

    Flight flight;

    public FinalizeFlightPresenter(long flightID, Context context) {
        FlightDbHelper flightDbHelper = new FlightDbHelper(context);
        flight = flightDbHelper.getFlight(flightID, context);

    }
}
