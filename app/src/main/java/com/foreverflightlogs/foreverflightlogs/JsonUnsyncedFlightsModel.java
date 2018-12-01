package com.foreverflightlogs.foreverflightlogs;

import java.util.List;

/**
 * Json Unsynced Flight Model
 * Stores the model to create the jsonObject that will
 * store all the flights needing to be synced and the
 * associated segments in the correct format for API
 */
public class JsonUnsyncedFlightsModel {

    int userID;
    List<JsonFlightModel> flights;

}
