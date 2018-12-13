package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirportPresenter {

  /**
   * Default Constructor
   */
  AirportPresenter() {

  }

  /**
   * fetchAirports: Gets all of the airports from a Gist JSON file
   *
   * @param context The context
   */
  public void fetchAirports(Context context) {
    final Context thisContext = context;
    if (getAirports(context).size() == 0) {
      Thread thread = new Thread(new Runnable() {

        @Override
        public void run() {
          try {
            URL url = new URL(
                "https://gist.githubusercontent.com/MatthewJN/cd0f67c71727eb30c7b304f303ff75d5/raw/27ff7a0b0bddc3151b960719b242107bb5440f4a/airports.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(false);
            conn.setDoInput(true);

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

            JSONArray jsonArray = new JSONArray(reply); //JSONObject(reply).getJSONArray("items");

            AirportDbHelper airportDbHelper = new AirportDbHelper(thisContext);

            Map<String, String> airports = new HashMap<String, String>();

            for (int i = 0; i < jsonArray.length(); i++) {
              airports.put(jsonArray.getJSONObject(i).getString("code"),
                  jsonArray.getJSONObject(i).getString("name"));
            }

            airportDbHelper.insertAirports(airports, thisContext);

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
  }

  /**
   * getAirports:
   *
   * @param context The context
   * @return A list of all airports.
   */
  public List<String> getAirports(Context context) {
    AirportDbHelper airportDbHelper = new AirportDbHelper(context);
    return airportDbHelper.getAllAirports();
  }
}
