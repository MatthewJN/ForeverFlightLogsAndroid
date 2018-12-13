package com.foreverflightlogs.foreverflightlogs;

/**
 * Json Segment Model Class Create a model to represent the data with correct data types and
 * structure to store Segments linked to a flight in the API
 */
public class JsonSegmentModel {

  Long id;
  int accountID;
  Long flightID;
  String segmentStartTime;
  String segmentEndTime;
  int pilotInCommandID;
  int dualHourPilotID;
  String night;
  String simulatedInstrument;
  String instrumentFlightRules;
  String visualFlightRules;
  String active = "1";

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getAccountID() {
    return accountID;
  }

  public void setAccountID(int accountID) {
    this.accountID = accountID;
  }

  public Long getFlightID() {
    return flightID;
  }

  public void setFlightID(Long flightID) {
    this.flightID = flightID;
  }

  public String getSegmentStartTime() {
    return segmentStartTime;
  }

  public void setSegmentStartTime(String segmentStartTime) {
    this.segmentStartTime = segmentStartTime;
  }

  public String getSegmentEndTime() {
    return segmentEndTime;
  }

  public void setSegmentEndTime(String segmentEndTime) {
    this.segmentEndTime = segmentEndTime;
  }

  public int getPilotInCommandID() {
    return pilotInCommandID;
  }

  public void setPilotInCommandID(int pilotInCommandID) {
    this.pilotInCommandID = pilotInCommandID;
  }

  public int getDualHourPilotID() {
    return dualHourPilotID;
  }

  public void setDualHourPilotID(int dualHourPilotID) {
    this.dualHourPilotID = dualHourPilotID;
  }

  public String getNight() {
    return night;
  }

  public void setNight(String night) {
    this.night = night;
  }

  public String getSimulatedInstrument() {
    return simulatedInstrument;
  }

  public void setSimulatedInstrument(String simulatedInstrument) {
    this.simulatedInstrument = simulatedInstrument;
  }

  public String getInstrumentFlightRules() {
    return instrumentFlightRules;
  }

  public void setInstrumentFlightRules(String instrumentFlightRules) {
    this.instrumentFlightRules = instrumentFlightRules;
  }

  public String getVisualFlightRules() {
    return visualFlightRules;
  }

  public void setVisualFlightRules(String visualFlightRules) {
    this.visualFlightRules = visualFlightRules;
  }

  public String getActive() {
    return active;
  }

  public void setActive(String active) {
    this.active = active;
  }
}
