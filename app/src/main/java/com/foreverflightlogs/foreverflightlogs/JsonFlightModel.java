package com.foreverflightlogs.foreverflightlogs;

import java.util.List;

/**
 * Json Flight Model Class
 * Create a model to represent the data with correct data types
 * and structure to store flights in the API
 */
public class JsonFlightModel {
    Long id;
//    @SerializedName ("n-Number") String aircraft;
//    String flightDate;
//    String multiEngine = "0";
//    String solo;
//    String crossCountry;
//    String fromAirport;
//    String toAirport;
//    String numberOfTakeOffs = "1";
//    String numberOfLandings = "1";
//    Long createdByID = id;
//    Boolean active = true;
    List<JsonSegmentModel> segments;
}
