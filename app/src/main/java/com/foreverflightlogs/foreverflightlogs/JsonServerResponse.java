package com.foreverflightlogs.foreverflightlogs;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Json Server Response
 * Model of response from API
 * This allows access to the entire json object that is returned
 * When sync is called
 */
public class JsonServerResponse {
    @SerializedName("flights")
    private List<JsonFlights> jsonFlights;

    public List<JsonFlights> getJsonFlights() {
        return jsonFlights;
    }

    public void setJsonFlights(List<JsonFlights> jsonFlights) {
        this.jsonFlights = jsonFlights;
    }



    public class JsonFlights {
        @SerializedName("id")
        private Long flightID;
        private int httpResponse;
        @SerializedName("flightID")
        private long apiFlightID;
        private  Segments segments;


        public Long getFlightID() {
            return flightID;
        }

        public void setFlightID(Long flightID) {
            this.flightID = flightID;
        }

        public int getHttpResponse() {
            return httpResponse;
        }

        public void setHttpResponse(int httpResponse) {
            this.httpResponse = httpResponse;
        }

        public long getApiFlightID() {
            return apiFlightID;
        }

        public void setApiFlightID(long apiFlightID) {
            this.apiFlightID = apiFlightID;
        }

        public Segments getSegments() {
            return segments;
        }

        public void setSegments(Segments segments) {
            this.segments = segments;
        }
    }

    public class Segments{
        private List<FlightSegments> flightSegments;

        public List<FlightSegments> getFlightSegments() {
            return flightSegments;
        }

        public void setFlightSegments(List<FlightSegments> flightSegments) {
            this.flightSegments = flightSegments;
        }
    }

    class FlightSegments{
        private long id;
        private int httpResponse;
        private long segmentID;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getHttpResponse() {
            return httpResponse;
        }

        public void setHttpResponse(int httpResponse) {
            this.httpResponse = httpResponse;
        }

        public long getSegmentID() {
            return segmentID;
        }

        public void setSegmentID(long segmentID) {
            this.segmentID = segmentID;
        }
    }

}
