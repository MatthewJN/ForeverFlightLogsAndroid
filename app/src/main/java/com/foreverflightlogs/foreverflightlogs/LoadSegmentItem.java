//package com.foreverflightlogs.foreverflightlogs;
//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.util.Log;
//import android.widget.ArrayAdapter;
//
//public class LoadSegmentItem extends AsyncTask<Void, String, Void> {
//    long flightID;
//    Context currentContext;
//    ArrayAdapter<String> adapter;
//    FlightDbHelper flightDbHelper;
//
//
//    LoadSegmentItem(long flightID, Context currentContext, ArrayAdapter<String> adapter){
//        this.flightID = flightID;
//        this.currentContext = currentContext;
//        this.adapter = adapter;
//        flightDbHelper = new FlightDbHelper(this.currentContext);
//    }
////
////    @Override
////    protected Void doInBackground(Void... voids) {
////        Log.i("getAllSegments", "Test pre-sql call");
////        //connect with db & loop over each segment and send to progressUpdate
////        String[] segments = {"segment 1", "segment 2"};
////        //List<Segment> segments = flightDbHelper.getAllSegments(flightID, this.currentContext);
////   // Log.i("segmentsInBackground", "segmentID: " + segments.get(0).getSegmentID());
////
////
//////        for (Segment segment: segments) {
//////            String segmentTitle = Long.toString(segment.getSegmentID());
//////            publishProgress(segmentTitle);
//////        }
////
////        for (String segment: segments) {
////                publishProgress(segment);
////
////        }
////
////        return null;
////    }
////
////    @Override
////    protected void onProgressUpdate(String... values) {
////        this.adapter.add(values[0]);
////        super.onProgressUpdate(values);
////    }
////    @Override
////    protected void onPostExecute(Void aVoid) {
////        super.onPostExecute(aVoid);
////    }
//}
