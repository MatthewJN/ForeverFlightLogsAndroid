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

    public String getAuth() {
        SharedPreferences mPrefs = _context.getSharedPreferences("authPref", MODE_PRIVATE);
        return mPrefs.getString("AuthCode", "");
    }

    public void setAuth(String auth) {
        SharedPreferences mPrefs = _context.getSharedPreferences("authPref", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("AuthCode", auth);
        prefsEditor.commit();
    }

    public int getUserID() {
        SharedPreferences mPrefs = _context.getSharedPreferences("userPref", MODE_PRIVATE);
        return mPrefs.getInt("UserID", -1);
    }

    public void setUserID(int userID) {
        SharedPreferences mPrefs = _context.getSharedPreferences("userPref", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putInt("UserID", userID);
        prefsEditor.commit();
    }

}
