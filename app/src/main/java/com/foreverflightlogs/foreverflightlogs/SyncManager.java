package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

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

                  try{
                      String auth = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImp0aSI6IjU3NDg4ZWExMDkzYzYifQ.eyJpc3MiOiJodHRwOlwvXC9mb3JldmVyRmxpZ2h0TG9nLmNvbSIsImF1ZCI6Imh0dHA6XC9cL2ZvcmV2ZXJGbGlnaHRMb2cuY29tIiwianRpIjoiNTc0ODhlYTEwOTNjNiIsImlhdCI6MTU0MzQ0NzYwMiwibmJmIjoxNTQzNDQ3NjAyLCJleHAiOjE1NDQ2NTcyMDIsInBob25lIjoyMDgzNzY0OTk5LCJhY2NvdW50SUQiOiIxMDE5NSIsImFjY291bnRUeXBlSUQiOiI0MDAwIiwicGVybSI6IltdIn0.SsUDf-bhQemae_OGriu4a00c-YXd05EDjGUyJ18lzyU";
                      //URL url = new URL("https://api.foreverflightlogs.com/v1/applications?auth=".concat(auth));
                      String urlString = "https://api.foreverflightlogs.com/v1/applications?auth=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImp0aSI6IjU3NDg4ZWExMDkzYzYifQ.eyJpc3MiOiJodHRwOlwvXC9mb3JldmVyRmxpZ2h0TG9nLmNvbSIsImF1ZCI6Imh0dHA6XC9cL2ZvcmV2ZXJGbGlnaHRMb2cuY29tIiwianRpIjoiNTc0ODhlYTEwOTNjNiIsImlhdCI6MTU0MzQ0NzYwMiwibmJmIjoxNTQzNDQ3NjAyLCJleHAiOjE1NDQ2NTcyMDIsInBob25lIjoyMDgzNzY0OTk5LCJhY2NvdW50SUQiOiIxMDE5NSIsImFjY291bnRUeXBlSUQiOiI0MDAwIiwicGVybSI6IltdIn0.SsUDf-bhQemae_OGriu4a00c-YXd05EDjGUyJ18lzyU";

                      URL url = new URL(urlString);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                      conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type","application/json;charset=UTF-8");
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setDoOutput(true);
                        conn.setDoInput(true);
                      Log.i("URL", "url is: " + url);
                        //create the json object
                        String jsonString = createJson(context);

                      DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                      //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                      os.writeBytes(jsonString);

                      os.flush();
                      os.close();

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

                      // Gson gson = new Gson();
                      // User user = gson.fromJson(reply, User.class);

                      JSONObject jsonObject = new JSONObject(reply);
//                      User user = new User(thisContext);
//                      String auth = jsonObject.getString("auth");
//                      user.setAuth(auth);

                      Log.i("SYNCSTATUS", String.valueOf(conn.getResponseCode()));
                      Log.i("SYNCMSG" , conn.getResponseMessage());
                      Log.i("SYNCRETURNED", reply);



                      conn.disconnect();

                  } catch (MalformedURLException e) {
                      e.printStackTrace();
                  } catch (ProtocolException e) {
                      e.printStackTrace();
                  } catch (IOException e) {
                      e.printStackTrace();
                  } catch (JSONException e) {
                      e.printStackTrace();
                  }
          }
    });

        thread.start();
}


    public static String createJson(Context context) {
        //Testing with just one flight and all segments associated with it
        FlightPresenter flightPresenter = new FlightPresenter(56, context);
        Flight flight = flightPresenter.flight;


        //get all flights in flightArray

        //get all segments for flights
      //  Segment[] segments = flightPresenter.flight.getSegments();
      //  Log.i("segmentArray", "0: " + segments[0]);


        //create flight data as needed for JSON

        Gson gson = new Gson();

        //create json object
        JsonObject userFlights = new JsonObject();
        JsonArray flightData = new JsonArray();
        JsonObject flightDetails = new JsonObject();
        JsonArray segmentsData = new JsonArray();
        JsonObject segment = new JsonObject();
        int accountID = 10195;


        userFlights.addProperty("userID", accountID);
        flightDetails.addProperty("id", flight.getFlightID());
        flightDetails.addProperty("duration", "15.00");
        flightDetails.addProperty("n-Number", "5631B");
        flightDetails.addProperty("flightDate", "2016-01-01");
        flightDetails.addProperty("multiEngine", "0");
        flightDetails.addProperty("solo", "0");
        flightDetails.addProperty("crossCountry", "0");
        flightDetails.addProperty("fromAirport", "BOI");
        flightDetails.addProperty("toAirport", "BOI");
        flightDetails.addProperty("numberOfTakeOffs", "1");
        flightDetails.addProperty("numberOfLandings", "1");
        flightDetails.addProperty("createdByID", accountID);
        flightDetails.addProperty("active", true);
        flightData.add(flightDetails);
        userFlights.add("flights", flightData);
        flightDetails.add("segments", segmentsData);


        for (int i = 0; i < 4; i++) {
            //for (int i = 0; i < segments.length; i++) {
            //   if(segments[i] != null) {
            segment.addProperty("id", 600 + i);
            segment.addProperty("accountID", accountID);
            segment.addProperty("flightID", flight.getFlightID());
            segment.addProperty("segmentStartTime", "2017-04-12 18:21:03");
            segment.addProperty("segmentEndTime", "2017-04-12 18:27:03");

            segment.addProperty("pilotInCommandID", accountID);
            segment.addProperty("dualHourPilotID", 0);
            segment.addProperty("night", "1");
            segment.addProperty("simulatedInstrument", "1");
            segment.addProperty("instrumentFlightRules", "1");
            segment.addProperty("visualFlightRules", "1");
            segment.addProperty("active", "1");
            //convert date to  string format
//                     segment.addProperty("segmentStartTime", segments[i].getStartDate());

            segmentsData.add(segment);

            //}
        }


        String json = gson.toJson(userFlights);

        Log.i("jsonFlight", json);
        return json;
    }
}
