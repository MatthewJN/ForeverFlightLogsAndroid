package com.foreverflightlogs.foreverflightlogs;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Json Flight Model Class
 * Create a model to represent the data with correct data types
 * and structure to store flights in the API
 */
public class JsonFlightModel {
    Long id;
    @SerializedName("n-Number") String aircraft;
    String flightDate;
    String multiEngine = "0";
    String solo;
    String crossCountry;
    String fromAirport;
    String toAirport;
    String numberOfTakeOffs = "1";
    String numberOfLandings = "1";
    Long createdByID = id;
    Boolean active = true;
    List<JsonSegmentModel> segments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public String getMultiEngine() {
        return multiEngine;
    }

    public void setMultiEngine(String multiEngine) {
        this.multiEngine = multiEngine;
    }

    public String getSolo() {
        return solo;
    }

    public void setSolo(String solo) {
        this.solo = solo;
    }

    public String getCrossCountry() {
        return crossCountry;
    }

    public void setCrossCountry(String crossCountry) {
        this.crossCountry = crossCountry;
    }

    public String getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(String fromAirport) {
        this.fromAirport = fromAirport;
    }

    public String getToAirport() {
        return toAirport;
    }

    public void setToAirport(String toAirport) {
        this.toAirport = toAirport;
    }

    public String getNumberOfTakeOffs() {
        return numberOfTakeOffs;
    }

    public void setNumberOfTakeOffs(String numberOfTakeOffs) {
        this.numberOfTakeOffs = numberOfTakeOffs;
    }

    public String getNumberOfLandings() {
        return numberOfLandings;
    }

    public void setNumberOfLandings(String numberOfLandings) {
        this.numberOfLandings = numberOfLandings;
    }

    public Long getCreatedByID() {
        return createdByID;
    }

    public void setCreatedByID(Long createdByID) {
        this.createdByID = createdByID;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<JsonSegmentModel> getSegments() {
        return segments;
    }

    public void setSegments(List<JsonSegmentModel> segments) {
        this.segments = segments;
    }
}
