package com.foreverflightlogs.foreverflightlogs;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertTrue;

public class TimerUnitTest {

    @Test
    public void startTimer_returnsCurrentTime() { assertTrue(new Timer().start() instanceof Date); }
}