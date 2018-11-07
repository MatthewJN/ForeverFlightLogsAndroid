package com.foreverflightlogs.foreverflightlogs;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Timer {

    /* Start the timer & return current time
    *  suggested to use calender here: https://stackoverflow.com/questions/5369682/get-current-time-and-date-on-android
    *  */
    public Date start() {
        //timer should start
        //get current time and return it
        Date currentTime = Calendar.getInstance().getTime();
        /* Not sure format of currentTime*/
       SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        String test = sdf.format(currentTime);

        Log.d("startTime", test);
        return currentTime;
    }

    /* Stop timer and return current time */
    public Date stop() {
        //timer should stop
        //get current time and return it
        Date currentTime = Calendar.getInstance().getTime();
        /* Not sure format of currentTime
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        String test = sdf.format(currentTime);
        Log.e("TEST", test);*/
        return currentTime;
    }
}