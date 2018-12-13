package com.foreverflightlogs.foreverflightlogs;

import java.util.List;

/**
 * Json Unsynced Flight Model Stores the model to create the jsonObject that will store all the
 * flights needing to be synced and the associated segments in the correct format for API
 */
public class JsonUnsyncedFlightsModel {

  int userID;
  List<JsonFlightModel> flights;

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public List<JsonFlightModel> getFlights() {
    return flights;
  }

  public void setFlights(List<JsonFlightModel> flights) {
    this.flights = flights;
  }
}
