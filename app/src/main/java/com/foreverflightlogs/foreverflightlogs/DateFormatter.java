package com.foreverflightlogs.foreverflightlogs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    /**
     * getStringFromDate:
     *
     * Creates the required date-format as a string so that it can be inserted in to an SQLite
     * database.
     *
     * @param date The date to be converted.
     * @return A string represented with the format yyyy-MM-dd HH:mm:ss
     */
    public static String getStringFromDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * getDateFromString:
     *
     * Accepts a string containing a date and converts it to a Date.
     * @param date A string with the following format: yyyy-MM-dd HH:mm:ss
     * @return A Date object.
     */
    public static Date getDateFromString(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date returnDate = new Date();
        try {
            returnDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnDate;
    }

}
