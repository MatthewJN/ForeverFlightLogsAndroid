package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SyncManager {

    public static void sync(final Context context) {
        //1. flightpresenter - call non default that accepts just context, not in progress, and not synced
        // FlightPresenter flightPresenter = new FlightPresenter(context, false,false);
        //2. flightPresenter.flights - contains all non synced and notInProgress flights
        //3. Fetch segments for each flight in flights
        //4. convert segments and flight to JSON - loop
        //5. send to server
        //6. with response update db for flight synced
        //7. loop thru json fetch flight ID set hasSynced to 1
        final Context thisContext = context;

          Thread thread = new Thread(new Runnable() {
              @Override
              public void run() {
                  UserManager userManager = new UserManager(thisContext);
                  String auth = userManager.getAuthCode();
                  try{
                      URL url = new URL("https://api.foreverflightlogs.com/v1/applications?auth=".concat(auth));

                      // setup http post connection
                      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                      conn.setRequestMethod("POST");
                      conn.setRequestProperty("Content-Type","application/json;charset=UTF-8");
                      conn.setRequestProperty("Accept", "application/json");
                      conn.setDoOutput(true);
                      conn.setDoInput(true);
                      Log.i("URL", "url is: " + url);

                      String jsonString = createJson(context); //create the json object

                      if(jsonString == "0") {
                          //no flights are returned
                          return;
                      }

                      //send the POST
                      DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                      os.writeBytes(jsonString);

                      os.flush();
                      os.close();

                      //read the server response
                      String reply;
                      InputStream in = conn.getInputStream();
                      StringBuffer sb = new StringBuffer();
                      try {
                          int chr;
                          while ((chr = in.read()) != -1) {
                              sb.append((char) chr);
                          }
                          reply = sb.toString();
                      } finally {
                          in.close();
                      }

                      Log.i("SYNCSTATUS", String.valueOf(conn.getResponseCode()));
                      Log.i("SYNCMSG" , conn.getResponseMessage());
                      Log.i("SYNCRETURNED", reply);

                      //parse reply and update flight to show synced
                      updateFlightsSynced(reply, context);



                      conn.disconnect();

                  } catch (MalformedURLException e) {
                      e.printStackTrace();
                  } catch (ProtocolException e) {
                      e.printStackTrace();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }
    });

        thread.start();
}

    private static void updateFlightsSynced(String reply, Context context) {
        FlightPresenter flightPresenter;
        Gson gson = new GsonBuilder().create();
        JsonServerResponse objectReply = gson.fromJson(reply, JsonServerResponse.class);

        //loop thru and update flight hasSynced in db
        for (JsonServerResponse.JsonFlights flight : objectReply.getJsonFlights()){
            if(flight.getHttpResponse() == 200){
                //create flight presenter to access flight info and update db
                flightPresenter = new FlightPresenter(flight.getFlightID(),context);
                flightPresenter.flight.setHasSynced(true);

            }
        }
        return;

        }

    /**
     * Create a json object by taking items stored in the db and
     * set those values into an object to pass to the API
     * UserFlights {
     *      "userID": int,
     *      "flights" [
     *          {
     *              flight info
     *              "segments" [
     *                  {
     *                      segmentInfo
     *                  }
     *              ]
     *          },
     *          {
     *              flight #2 info
     *              segments for flight#2 [
     *                  {
     *                      segment info
     *                  }
     *              ]
     *          }
     *      ]
     *  }
     * @param context
     * @return
     */
    public static String createJson(Context context) {
        //Get all unsynced flights that are not in progress
        FlightPresenter flightPresenter = new FlightPresenter(false, false, context);
        List<Flight> dbflights = flightPresenter.flights;
        if(dbflights.size() <= 0){
            return "0";
        }
        Gson gson = new Gson();

        UserManager userManager = new UserManager(context);
        //int accountID = 2;
        int accountID = userManager.getUserID();
        JsonUnsyncedFlightsModel userFlights = new JsonUnsyncedFlightsModel();
        userFlights.setUserID(accountID);
        userFlights.flights = new ArrayList<>();
        //Access the data for each flight from the db
        for(Flight dbflight: dbflights){
            //set all individual flight data in this loop
            SegmentPresenter segmentPresenter = new SegmentPresenter(dbflight.getFlightID(),context);
            JsonFlightModel jsonFlight = new JsonFlightModel();
            jsonFlight.id = dbflight.getFlightID();
            jsonFlight.setAircraft(dbflight.getAircraft());
            jsonFlight.setFlightDate(getStringFromDate(dbflight.getStartDate()));
            jsonFlight.setSolo(convertBooleanToString(dbflight.getSolo()));
            jsonFlight.setCrossCountry(convertBooleanToString(dbflight.getCrosscountry()));
            jsonFlight.setFromAirport(dbflight.getOrigin());
            jsonFlight.setToAirport(dbflight.getDestination());

           jsonFlight.segments= new ArrayList<>(); //create array of segments
            for(Segment dbsegment: segmentPresenter.segments){
                //set all individual segment data in this loop
                JsonSegmentModel jsonSegment = new JsonSegmentModel();
                jsonSegment.id = dbsegment.getSegmentID();
                jsonSegment.setAccountID(accountID);
                jsonSegment.setFlightID(dbflight.getFlightID());
                jsonSegment.setSegmentStartTime(getStringFromDate(dbsegment.getStartDate()));
                jsonSegment.setSegmentEndTime(getStringFromDate(dbsegment.getEndDate()));
                jsonSegment.setPilotInCommandID(accountID);
                jsonSegment.setDualHourPilotID(0);
                jsonSegment.setNight(convertBooleanToString(dbsegment.getNight()));
                jsonSegment.setSimulatedInstrument(convertBooleanToString(dbsegment.getSimulatedInstruments()));
                jsonSegment.setInstrumentFlightRules(convertBooleanToString(dbsegment.getinstrumentFlight()));
                jsonSegment.setVisualFlightRules(convertBooleanToString(dbsegment.getVisualFlight()));

                jsonFlight.segments.add(jsonSegment); //add the jsonSegment to the array of segments
            }
            userFlights.flights.add(jsonFlight);//add the individual flights to flights array
        }
        String json = gson.toJson(userFlights); //create the jsonString

        Log.i("jsonFlight", json);
        return json;

    }
    private static String getStringFromDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        if (date == null) {
            return null;
        } else {
            return sdf.format(date);
        }
    }

    private static String convertBooleanToString(Boolean value){
        if(value){
            return "1";
        }
        else {
            return "0";
        }
    }
}
