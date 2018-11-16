package com.foreverflightlogs.foreverflightlogs;

import java.util.Date;

/**
 * Stores data related to Flight Segment
 */
public class SegmentModel {
    Date startTime;
    Date    stopTime;
    long segmentDuration;
    boolean pilotInCommand;
    boolean dualHours;
    boolean simulatedInstruments;
    boolean visualFlight;
    boolean instrumentFlight;
    boolean night;
    int     segmentID;
    int     flightID;


    //constructor
    SegmentModel() {
        //how to set dates initially?
        //set default values
        pilotInCommand = true;
        dualHours = false;
        simulatedInstruments = false;
        visualFlight = false;
        instrumentFlight = false;
        night = false;

    }

    /////   GETTERS AND SETTERS   ///////////

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date start) {
        this.startTime = start;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stop) {
        this.stopTime = stop;
    }

    public boolean isPilotInCommand() {
        return pilotInCommand;
    }

    public void setPilotInCommand(boolean pilotInCommand) {
        this.pilotInCommand = pilotInCommand;
    }

    public boolean isDualHours() {
        return dualHours;
    }

    public void setDualHours(boolean dualHours) {
        this.dualHours = dualHours;
    }

    public boolean isSimulatedInstruments() {
        return simulatedInstruments;
    }

    public void setSimulatedInstruments(boolean simulatedInstruments) {
        this.simulatedInstruments = simulatedInstruments;
    }

    public boolean isVisualFlight() {
        return visualFlight;
    }

    public void setVisualFlight(boolean visualFlight) {
        this.visualFlight = visualFlight;
    }

    public boolean isInstrumentFlight() {
        return instrumentFlight;
    }

    public void setInstrumentFlight(boolean instrumentFlight) {
        this.instrumentFlight = instrumentFlight;
    }

    public boolean isNight() {
        return night;
    }

    public void setNight(boolean night) {
        this.night = night;
    }

    public int getSegmentID() {
        return segmentID;
    }

    public void setSegmentID(int segmentID) {
        this.segmentID = segmentID;
    }

    public int getFlightID() {
        return flightID;
    }

    public void setFlightID(int flightID) {
        this.flightID = flightID;
    }


    public long getSegmentDuration() {
        return segmentDuration;
    }

    public void setSegmentDuration(long segmentDuration) {
        this.segmentDuration = segmentDuration;
    }
}
