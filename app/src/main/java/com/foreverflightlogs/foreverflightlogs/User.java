package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;


public class User {

  // private String auth;
  private Context _context;

  User(Context context) {
    _context = context;
  }

  /**
   * getAuth Gets the authorisation code.
   *
   * @return The auth code in a string.
   */
  public String getAuth() {
    SharedPreferences mPrefs = _context.getSharedPreferences("authPref", MODE_PRIVATE);
    return mPrefs.getString("AuthCode", "");
  }

  /**
   * setAuth: Sets the authorisation code and persists it.
   *
   * @param auth The auth string.
   */
  public void setAuth(String auth) {
    SharedPreferences mPrefs = _context.getSharedPreferences("authPref", MODE_PRIVATE);
    SharedPreferences.Editor prefsEditor = mPrefs.edit();
    prefsEditor.putString("AuthCode", auth);
    prefsEditor.commit();
  }

  /**
   * getUserID Gets the user ID
   *
   * @return The user ID in an int.
   */
  public int getUserID() {
    SharedPreferences mPrefs = _context.getSharedPreferences("userPref", MODE_PRIVATE);
    return mPrefs.getInt("UserID", -1);
  }

  /**
   * setUserID: Sets the user ID and persists it.
   *
   * @param userID The user ID int.
   */
  public void setUserID(int userID) {
    SharedPreferences mPrefs = _context.getSharedPreferences("userPref", MODE_PRIVATE);
    SharedPreferences.Editor prefsEditor = mPrefs.edit();
    prefsEditor.putInt("UserID", userID);
    prefsEditor.commit();
  }

}
