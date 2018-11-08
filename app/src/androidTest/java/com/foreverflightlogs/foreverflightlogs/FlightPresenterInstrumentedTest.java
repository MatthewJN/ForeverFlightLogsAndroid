package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class FlightPresenterInstrumentedTest {

    @Test
    public void createFlightAndGetFlightID_returnsZero() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        FlightPresenter flightPresenter = new FlightPresenter("Origin", "Aircraft", new Date(), appContext);
        assertEquals(0, flightPresenter.getFlightID());
    }

    @Test
    public void createFlightAndCheckDuration_isCorrect() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        FlightPresenter flightPresenter = new FlightPresenter("Origin", "Aircraft", new Date(), appContext);

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(flightPresenter.getFlightDuration() > 0);
    }
}