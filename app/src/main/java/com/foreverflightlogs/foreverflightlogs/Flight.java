package com.foreverflightlogs.foreverflightlogs;

import java.util.Date;

public class Flight {
    private long flightID;
    private String origin;
    private String destination;
    private Date startDate;
    private Date endDate;
    private String aircraft;
    private boolean hasSynced;
    private boolean crosscountry;
    private boolean solo;
    private String remarks;

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
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public void setHasSynced(boolean hasSynced) {
        this.hasSynced = hasSynced;
    }

    public void setCrosscountry(boolean crosscountry) {
        this.crosscountry = crosscountry;
    }

    public void setSolo(boolean solo) {
        this.solo = solo;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
