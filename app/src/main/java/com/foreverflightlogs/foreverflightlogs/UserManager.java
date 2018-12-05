package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class UserManager {

    private Context context;

    UserManager(Context context) {
        this.context = context;
    }

    /**
     * authWithAPI:
     * Authorizes with the API.
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
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("phone", phoneNumber); // 5552000000
                    jsonParam.put("password", password); // test

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
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

                    //int userID = jsonObject.getInt("userID");
                    //user.setUserID(userID);

                    getUserID(context);

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());
                    Log.i("RETURNED", reply);
                    Log.i("AUTHCODE", user.getAuth());

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
     * getUserID:
     * Gets the user ID associated with the login.
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
                    conn.setRequestProperty("Accept","application/json");
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

                    int userID = jsonObject.getJSONObject("items").getJSONArray("ownerAvailableObjects").getJSONObject(0).getInt("id");

                    user.setUserID(userID);
                    
                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());
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

    public String getAuthCode() {
        User user = new User(context);
        return user.getAuth();
    }

    public int getUserID() {
        User user = new User(context);
        return user.getUserID();
    }

}
