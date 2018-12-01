package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

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
                  User user = new User(thisContext);
                  String auth = user.getAuth();
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
                      //updateFlightsSynced(reply, context);



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

    private void updateFlightsSynced(String reply, Context context) {
        //parse to object
//        Gson gson = new Gson();
//        ArrayList<Flight> flights = new ArrayList<Flight>();
//        jsonFlight = gson.fromJson(reply, Flight.class);
//        if(reply != null){
//            for()
//        }
    }


    public static String createJson(Context context) {
        //Get all unsynced flights that are not in progress
        FlightPresenter flightPresenter = new FlightPresenter(false, false, context);
        List<Flight> dbflights = flightPresenter.flights;
        //List<Segment> dbsegments = flightPresenter.segments;
       // Log.i("NUMSEGMENTS", "length: "+ dbsegments.size());

        Gson gson = new Gson();
        /**
         * Create a json object by taking items stored in db and
         * set the values into a object to pass to API
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
         *
         */
        JsonUnsyncedFlightsModel userFlights = new JsonUnsyncedFlightsModel();
        userFlights.userID = 2;
        userFlights.flights = new ArrayList<>();
        //Access the data for each flight from the db
       // FlightPresenter flight;
        for(Flight dbflight: dbflights){
            SegmentPresenter segmentPresenter = new SegmentPresenter(dbflight.getFlightID(),context);
            int size = segmentPresenter.segments.size();
            Log.i("NUMFLIGHTSEGMENTS", "length: "+ segmentPresenter.segments.size());
            JsonFlightModel jsonFlight = new JsonFlightModel();
            jsonFlight.id = dbflight.getFlightID();
           jsonFlight.segments= new ArrayList<>();
            for(Segment dbsegment: segmentPresenter.segments){
                JsonSegmentModel jsonSegment = new JsonSegmentModel();
                jsonSegment.id = dbsegment.getSegmentID();
                jsonFlight.segments.add(jsonSegment);
            }
            userFlights.flights.add(jsonFlight);
        }
//
//        //create json object
//        JsonObject userFlights = new JsonObject();
//        JsonArray flightData = new JsonArray();
//
//        JsonArray segmentsData = new JsonArray();
//        JsonObject segment = new JsonObject();
//        int accountID = 2;
//
//        //create flight data according to API requirements
//        userFlights.addProperty("userID", accountID);
//
//        for(Flight flight: flights) {
//            JsonObject flightDetails = new JsonObject();
//            Log.i("id", "flightID: " + flight.getFlightID());
//            flightDetails.addProperty("id", flight.getFlightID());
//            //flightDetails.addProperty("duration", "15.00");
//            flightDetails.addProperty("n-Number", flight.getAircraft());
//            flightDetails.addProperty("flightDate", getStringFromDate(flight.getStartDate()));
//            flightDetails.addProperty("multiEngine", "0");
//            flightDetails.addProperty("solo", convertBooleanToString(flight.getSolo()));
//            flightDetails.addProperty("crossCountry", convertBooleanToString(flight.getCrosscountry()));
//            flightDetails.addProperty("fromAirport", flight.getOrigin());
//            flightDetails.addProperty("toAirport", flight.getDestination());
//            flightDetails.addProperty("numberOfTakeOffs", "1");
//            flightDetails.addProperty("numberOfLandings", "1");
//            flightDetails.addProperty("createdByID", accountID);
//            flightDetails.addProperty("active", true);
//
//            flightData.add(flightDetails);
//            flightDetails.add("segments", segmentsData);
//
//
//            //for (int i = 0; i < 1; i++) {
//                //for (int i = 0; i < segments.length; i++) {
//                //   if(segments[i] != null) {
////                segment.addProperty("id", 600);
////                segment.addProperty("accountID", accountID);
////                segment.addProperty("flightID", flight.getFlightID());
////                segment.addProperty("segmentStartTime", "2017-04-12 18:21:03");
////                segment.addProperty("segmentEndTime", "2017-04-12 18:27:03");
////
////                segment.addProperty("pilotInCommandID", accountID);
////                segment.addProperty("dualHourPilotID", 0);
////                segment.addProperty("night", "1");
////                segment.addProperty("simulatedInstrument", "1");
////                segment.addProperty("instrumentFlightRules", "1");
////                segment.addProperty("visualFlightRules", "1");
////                segment.addProperty("active", "1");
//                //convert date to  string format
////                     segment.addProperty("segmentStartTime", segments[i].getStartDate());
//
//                segmentsData.add(segment);
//
//                //}
//           // }
//        }
//
//        userFlights.add("flights", flightData);
        String json = gson.toJson(userFlights);

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
