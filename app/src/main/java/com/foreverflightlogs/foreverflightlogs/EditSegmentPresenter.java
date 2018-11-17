package com.foreverflightlogs.foreverflightlogs;

class EditSegmentPresenter {

    EditSegmentActivity sView;
    /**
     * Default Constructor:
     * Nothing to be initialized
     */
    EditSegmentPresenter() { }

    /**
     * Non-Default Constructor:
     * Pass in a view to instantiate the SegmentPresenter with the view desired.
     * @param view The segment activity view
     */
    EditSegmentPresenter(EditSegmentActivity view) {
        sView = view;
    }

    public void populateSegmentFields(String segmentID) {
        //in db pull segment data and set in segment
    }

    public void saveSegmentInDB() {
        //save all the segment data  - but how do I get the current status of all the items? to save?
        sView.dualHour.isChecked(); //do this for each of the buttons on save and update if needed
        //close activity and return to listSegmentActivity
    }
}
