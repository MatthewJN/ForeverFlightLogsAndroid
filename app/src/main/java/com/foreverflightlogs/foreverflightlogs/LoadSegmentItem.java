package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

public class LoadSegmentItem extends AsyncTask<Void, String, Void> {
    int segmentID;
    Context currentContext;
    ArrayAdapter<String> adapter;

    LoadSegmentItem(int segmentID, Context currentContext, ArrayAdapter<String> adapter){
        this.segmentID = segmentID;
        this.currentContext = currentContext;
        this.adapter = adapter;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        //connect with db & loop over each segment and send to progressUpdate
        String[] segments = { "segment 1 details", "segment 2 details", "segment 3 details", "segment 4 details"};
        for (String segment: segments) {
            publishProgress(segment);
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        this.adapter.add(values[0]);
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
