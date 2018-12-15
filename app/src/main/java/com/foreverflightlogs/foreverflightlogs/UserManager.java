package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Observable;

public class UserManager extends Observable {

  private static Context context;
  private static Object mLock = new Object();
  private static UserManager USER_MANAGER_INSTANCE = null;

  /**
   * getInstance: Gets an instance of UserManager
   *
   * @param aContext The context
   * @return Returns the UserManager instance.
   */
  public static UserManager getInstance(Context aContext) {
    context = aContext;
    synchronized (mLock) {
      if (USER_MANAGER_INSTANCE == null) {
        USER_MANAGER_INSTANCE = new UserManager();
      }
      return USER_MANAGER_INSTANCE;
    }
  }

  /**
   * authWithAPI: Authorises with the API.
   *
   * @param phoneNumber The phone number (user name).
   * @param password The password for the account.
   * @param context The current context.
   */
  public void authWithAPI(final String phoneNumber, final String password, final Context context) {

    final Context thisContext = context;
    Thread thread = new Thread(new Runnable() {

      @Override
      public void run() {
        try {
          URL url = new URL("https://api.foreverflightlogs.com/v1/authentication");
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setRequestMethod("POST");
          conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
          conn.setRequestProperty("Accept", "application/json");
          conn.setDoOutput(true);
          conn.setDoInput(true);

          JSONObject jsonParam = new JSONObject();
          jsonParam.put("phone", phoneNumber);
          jsonParam.put("password", password);

          Log.i("JSON", jsonParam.toString());
          DataOutputStream os = new DataOutputStream(conn.getOutputStream());
          os.writeBytes(jsonParam.toString());

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

          JSONObject jsonObject = new JSONObject(reply);
          User user = new User(thisContext);
          String auth = jsonObject.getString("auth");
          user.setAuth(auth);

          getUserID(context);

          conn.disconnect();

          setChanged();
          notifyObservers("200");

        } catch (MalformedURLException e) {
          e.printStackTrace();
        } catch (ProtocolException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
          setChanged();
          notifyObservers("401");
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    });

    thread.start();
  }

  /**
   * getUserID: Gets the user ID associated with the login.
   *
   * @param context The context.
   */
  private void getUserID(final Context context) {
    final Context thisContext = context;
    Thread thread = new Thread(new Runnable() {

      @Override
      public void run() {
        try {
          User user = new User(thisContext);
          URL url = new URL("https://api.foreverflightlogs.com/v1/accounts?auth=" + user.getAuth());
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

          JSONObject jsonObject = new JSONObject(reply);

          int userID = jsonObject.getJSONObject("items").getJSONArray("ownerAvailableObjects")
              .getJSONObject(0).getInt("id");

          user.setUserID(userID);

          Log.i("STATUS", String.valueOf(conn.getResponseCode()));
          Log.i("MSG", conn.getResponseMessage());
          Log.i("RETURNED", reply);

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

  /**
   * getAuthCode Fetches the auth code from the model.
   *
   * @return The auth code as a string.
   */
  public String getAuthCode() {
    User user = new User(context);
    return user.getAuth();
  }

  /**
   * getUserID Gets the user ID.
   *
   * @return The user ID as an int.
   */
  public int getUserID() {
    User user = new User(context);
    return user.getUserID();
  }
}
