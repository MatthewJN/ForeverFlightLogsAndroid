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
import java.util.Observable;

/**
 * Responsible for  connecting with the API and updating the UI
 *
 */
public class CreateAccountPresenter extends Observable {

    private static Context context;
    private static Object mLock = new Object();
    private static CreateAccountPresenter CREATE_ACCOUNT_PRESENTER_INSTANCE = null;
    private String response;
    private boolean isAccountCreated = false;


   // CreateAccountPresenter(Context context) { this.context = context;}
    //turn this into a singleton
    public static CreateAccountPresenter getInstance(Context aContext) {
        context = aContext;
        synchronized (mLock) {
            if (CREATE_ACCOUNT_PRESENTER_INSTANCE == null) {
                CREATE_ACCOUNT_PRESENTER_INSTANCE = new CreateAccountPresenter();
            }
            return CREATE_ACCOUNT_PRESENTER_INSTANCE;
        }
    }

    public void createAccountOnAPI(final String phone, final String password, final String name, final Context context) {

        final Context thisContext = context;
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL("https://api.foreverflightlogs.com/v1/accounts");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("phone", phone); // 5552000000
                    jsonParam.put("password", password); // test
                    jsonParam.put("name", name);

                    Log.i("JSONACCT", jsonParam.toString());
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
                    setResponse(reply); //store the reply in case we need to pull in activity
                    JSONObject jsonObject = new JSONObject(reply);
                    if(jsonObject.has("items")){
                        Log.i("ITEM", "items found in reply");
                    }
                    if(jsonObject.has("error")){
                        Log.i("ACCTREPLY", "reply is : " + reply);
                        setChanged();
                        notifyObservers("401");
                    }
                    else {
                        setChanged();
                        notifyObservers("200");
                    }

                    Log.i("ACCTSTATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("ACCTMSG" , conn.getResponseMessage());
                    Log.i("ACCTRETURNED", reply);


                    conn.disconnect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    setChanged();
                    notifyObservers("401");
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //return "200";
            }
        });

        thread.start();

    }

    public boolean isAccountCreated() {
        return isAccountCreated;
    }

    public void setAccountCreated(boolean accountCreated) {
        isAccountCreated = accountCreated;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
