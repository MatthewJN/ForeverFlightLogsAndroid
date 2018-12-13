package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;

import java.util.Date;

public class Segment {

  private long segmentID;
  private Date startDate;
  private Date endDate;
  private boolean pilotInCommand;
  private boolean dualHours;
  private boolean simulatedInstruments;
  private boolean visualFlight;
  private boolean instrumentFlight;
  private boolean night;
  private boolean isCompleted;
  private Context context;

  public Segment(Context context) {
    this.context = context;
  }

  public Segment(long segmentID,
      Date startDate,
      Date endDate,
      boolean pilotInCommand,
      boolean dualHours,
      boolean simulatedInstruments,
      boolean visualFlight,
      boolean instrumentFlight,
      boolean night,
      boolean isCompleted,
      Context context) {
    this.segmentID = segmentID;
    this.startDate = startDate;
    this.endDate = endDate;
    this.pilotInCommand = pilotInCommand;
    this.dualHours = dualHours;
    this.simulatedInstruments = simulatedInstruments;
    this.visualFlight = visualFlight;
    this.instrumentFlight = instrumentFlight;
    this.night = night;
    this.isCompleted = isCompleted;
    this.context = context;
  }

  // Getters
  public long getSegmentID() {
    return segmentID;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public boolean getPilotInCommand() {
    return pilotInCommand;
  }

  public boolean getDualHours() {
    return dualHours;
  }

  public boolean getSimulatedInstruments() {
    return simulatedInstruments;
  }

  public boolean getVisualFlight() {
    return visualFlight;
  }

  public boolean getinstrumentFlight() {
    return instrumentFlight;
  }

  public boolean getNight() {
    return night;
  }

  public boolean getIsCompleted() {
    return isCompleted;
  }

  // Setters
  public void setSegmentID(long segmentID) {
    this.segmentID = segmentID;
  }

  public void setStartDate(Date date) {
    this.startDate = date;
    updateDatabase();
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
    updateDatabase();
  }

  public void setPilotInCommand(boolean pilotInCommand) {
    this.pilotInCommand = pilotInCommand;
    updateDatabase();
  }

  public void setDualHours(boolean dualHours) {
    this.dualHours = dualHours;
    updateDatabase();
  }

  public void setSimulatedInstruments(boolean simulatedInstruments) {
    this.simulatedInstruments = simulatedInstruments;
    updateDatabase();
  }

  public void setVisualFlight(boolean visualFlight) {
    this.visualFlight = visualFlight;
    updateDatabase();
  }

  public void setInstrumentFlight(boolean instrumentFlight) {
    this.instrumentFlight = instrumentFlight;
    updateDatabase();
  }

  public void setNight(boolean night) {
    this.night = night;
    updateDatabase();
  }

  public void setIsCompleted(boolean isCompleted) {
    this.isCompleted = isCompleted;
    updateDatabase();
  }

  private void updateDatabase() {
    FlightDbHelper dbHelper = new FlightDbHelper(context);
    dbHelper.updateSegment(this, context);
  }
}
