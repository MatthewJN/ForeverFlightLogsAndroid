package com.foreverflightlogs.foreverflightlogs;

import android.widget.Toast;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertTrue;
/**
 * Local unit test of Segment Presenter
 */
public class SegmentPresenterUnitTest {




        //@Mock
        private SegmentPresenter sPresenter;
        private Date date;
        private Date startDate;
        Toast toast;


        @Before
        public void setUp() throws Exception {
            //MockitoAnnotations.initMocks(this);
            sPresenter = new SegmentPresenter();
        }

        @Test  //verify start segment returns an integer (id) and that it is greater than 0
        public void startSegment() {
            assertTrue("startSegment id is returned", sPresenter.startSegment(date) > 0 );
        }

        @Test
        public void handleSegmentStart() {
            assertTrue("segmentInProgressID is set", sPresenter.getSegmentInProgressID() > 0);
        }

        @Test
        public void handleSegmentEnd() {
            //assertTrue("segment is ended so segmentID is updated ", sPresenter.get;
        }

        @Test
        public void calculateDuration() {
//        toast = mock(Toast.class);
//        verify(toast, times(1)).makeText(Mockito.application, R.string.no_internet_connection, 1).show();
            assertTrue("duration is calculated ", sPresenter.calculateDuration(startDate, date) > 0);
        }


        @Test
        public void getCurrentTime() {
            assertTrue(sPresenter.getCurrentTime() instanceof Date);
            assertTrue(sPresenter.getCurrentTime().getTime() > 0 );
        }
    }
