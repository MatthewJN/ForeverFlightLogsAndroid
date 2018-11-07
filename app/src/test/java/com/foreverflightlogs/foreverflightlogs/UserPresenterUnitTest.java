package com.foreverflightlogs.foreverflightlogs;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UserPresenterUnitTest {

    @Test
    public void createFlightandGetFlightID_returnsZero() {
        FlightPresenter flightPresenter = new FlightPresenter("Origin", "Aircraft", new Date());
        assertEquals(0, flightPresenter.getFlightID());
    }

    @Test
    public void createFlightAndCheckDuration_isCorrect() {
        FlightPresenter flightPresenter = new FlightPresenter("Origin", "Aircraft", new Date());
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(flightPresenter.getFlightDuration() > 0);
    }
}