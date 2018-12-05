package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;

import java.util.Date;

public class Flight {
    private long flightID;
    private String origin;
    private String destination;
    private Date startDate;
    private Date endDate;
    private String aircraft;
    private boolean inProgress;
    private boolean hasSynced;
    private boolean crosscountry;
    private boolean solo;
    private String remarks;
    private Context context;
//    Segment[] segments;
//    public Segment[] getSegments() {
//        return segments;
//    }
//
//    public void setSegments(Segment[] segments) {
//        this.segments = segments;
//    }



    /**
     * Non-Default constructor which creates the flight and stores a context locally.
     * @param context The context which is stored local to the class.
     */
    public Flight(Context context) {
        this.context = context;
    }

    /**
     * Non-Default constructor used for creating a new flight.
     * @param flightID The ID of the flight.
     * @param origin The origin of the flight (a 3 char airport code)
     * @param destination The destination of the flight (a 3 char airport code)
     * @param startDate The time that the flight started.
     * @param endDate The time that the flight ended.
     * @param aircraft The N number of the plane used.
     * @param inProgress Is the flight in progress?
     * @param hasSynced Indicates if the flight has been succesfully sent to the server.
     * @param crosscountry Was the flight cross-country?
     * @param solo Was the pilot alone?
     * @param remarks Comments about the flight.
     * @param context The context of the activity.
     */
    public Flight(long flightID,
                  String origin,
                  String destination,
                  Date startDate,
                  Date endDate,
                  String aircraft,
                  boolean inProgress,
                  boolean hasSynced,
                  boolean crosscountry,
                  boolean solo,
                  String remarks,
                  Context context) {
        this.flightID = flightID;
        this.origin = origin;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.aircraft = aircraft;
        this.hasSynced = hasSynced;
        this.crosscountry = crosscountry;
        this.solo = solo;
        this.remarks = remarks;
        this.context = context;
    }

    // Getters
    public long getFlightID() {
        return flightID;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getAircraft() {
        return aircraft;
    }

    public boolean getInProgress() {
        return inProgress;
    }

    public boolean getHasSynced() {
        return hasSynced;
    }

    public boolean getCrosscountry() {
        return crosscountry;
    }

    public boolean getSolo() {
        return solo;
    }

    public String getRemarks() {
        return remarks;
    }

    // Setters
    public void setFlightID(long flightID) {
        this.flightID = flightID;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
        updateDatabase();
    }

    public void setDestination(String destination) {
        this.destination = destination;
        updateDatabase();
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
        updateDatabase();
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
        updateDatabase();
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
        updateDatabase();
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
        updateDatabase();
    }

    public void setHasSynced(boolean hasSynced) {
        this.hasSynced = hasSynced;
        updateDatabase();
    }

    public void setCrosscountry(boolean crosscountry) {
        this.crosscountry = crosscountry;
        updateDatabase();
    }

    public void setSolo(boolean solo) {
        this.solo = solo;
        updateDatabase();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
        updateDatabase();
    }

    private void updateDatabase() {
        FlightDbHelper dbHelper = new FlightDbHelper(context);
        dbHelper.updateFlight(this, context);
    }

}
