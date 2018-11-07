package com.foreverflightlogs.foreverflightlogs;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FlightManagerUnitTest {
    @Test
    public void checkAuth_isWorking() { assertTrue(new UserPresenter().getAuthCode() instanceof String); }
}