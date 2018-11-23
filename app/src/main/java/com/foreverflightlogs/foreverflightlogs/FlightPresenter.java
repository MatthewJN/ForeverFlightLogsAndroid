package com.foreverflightlogs.foreverflightlogs;


import android.content.Context;
import java.util.Date;


public class FlightPresenter implements Syncable {


    /**
     * The current flight.
     */
    public Flight flight;

    /**
     * Default Constructor:
     * Not available to be called.
     */
    private FlightPresenter() { }

    /**
     * Non-Default Constructor:
     *
     * Pass in a flightID to instantiate the FlightPresenter with the flight desired.
     * @param flightID The flightID assigned by the model.
     */
    public FlightPresenter(long flightID, Context context) {
        FlightDbHelper flightDbHelper = new FlightDbHelper(context);
        flight = flightDbHelper.getFlight(flightID, context);
    }

    /**
     * Non-Default Constructor:
     * 
     * Called when a new flight is starting. Pass in the origin, aircraft, and date.
     *
     * @param origin The 3 character airport code.
     * @param aircraft The aircraft tail number.
     * @param startDate The start date (usually the exact time the start button was pressed).
     */
    public FlightPresenter(String origin, String aircraft, Date startDate, Context context) {
        FlightDbHelper flightDbHelper = new FlightDbHelper(context);
        flight = flightDbHelper.insertNewFlight(origin, aircraft, startDate, context);
    }

    /**
     * endFlight
     * End the current flight.
     */
    public void endFlight() {
        syncData();
    }

    /**
     * syncData
     * Synchronises the data with the ForeverFlightLog server.
     * Typically called when a flight ends, but left public in case that fails and
     * the app needs to try again at a later time.
     * @return A boolean to indicate if the sync was successful.
     */
    @Override
    public boolean syncData() {
        return false;
    }

    public long getFlightID() {
        return flight.getFlightID();
    }

    /**
     * getFlightDuration
     * Provides the duration of the flight. If flight was not ended or has not finished
     * it returns the current duration at the moment the request was made.
     * @return The flight duration in seconds.
     */
    public long getFlightDuration() {
        if (flight.getEndDate() == null) {
            return flight.getEndDate().getTime() - flight.getStartDate().getTime();
        } else {
            return new Date().getTime() - flight.getStartDate().getTime();
        }
    }

//    public String getOrigin() {
//        return flight.getOrigin();
//    }
//
//    public String getDestination() {
//        return flight.getDestination();
//    }
//
//    public Date getStartDate() {
//        return flight.getStartDate();
//    }
//
//    public Date getEndDate() {
//        return flight.getEndDate();
//    }
//
//    public String getAircraft() {
//        return flight.getAircraft();
//    }
//
//    public boolean getHasSynced() {
//        return flight.getHasSynced();
//    }
//
//    public boolean getCrosscountry() {
//        return flight.getCrosscountry();
//    }
//
//    public boolean getSolo() {
//        return flight.getSolo();
//    }
//
//    public String getRemarks() {
//        return flight.getRemarks();
//    }
//
//    public void setOrigin(String origin) {
//        flight.setOrigin(origin);
//    }
//
//    public void setDestination(String destination) {
//        flight.setDestination(destination);
//    }
//
//    public void setStartDate(Date startDate) {
//        flight.setStartDate(startDate);
//    }
//
//    public void setEndDate(Date endDate) {
//        flight.setEndDate(endDate);
//    }
//
//    public void setAircraft(String aircraft) {
//        flight.setAircraft(aircraft);
//    }
//
//    public void setHasSynced(boolean hasSynced) {
//        flight.setHasSynced(hasSynced);
//    }
//
//    public void setCrosscountry(boolean crosscountry) {
//        flight.setCrosscountry(crosscountry);
//    }
//
//    public void setSolo(boolean solo) {
//        flight.setSolo(solo);
//    }
//
//    public void setRemarks(String remarks) {
//        flight.setRemarks(remarks);
//    }

}
