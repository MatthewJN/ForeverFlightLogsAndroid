package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;

public class SyncManager {

    public static void sync(Context context) {
        //1. flightpresenter - call non default that accepts just context, not in progress, and not synced
        //2. flightPresenter.flights - contains all non synced and notInProgress flights
        //3. Fetch segments for each flight in flights
        //4. convert segments and flight to JSON - loop
        //5. send to server
        //6. with response update db for flight synced
        //7. loop thru json fetch flight ID set hasSynced to 1 
    }


}
