package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SegmentsAdapter extends ArrayAdapter<Segment> {
    public SegmentsAdapter(Context context, List<Segment> segments) {
        super(context, 0, segments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Segment segment = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        TextView segmentNumber = (TextView) convertView.findViewById(R.id.segmentNumber);
        TextView startDate = (TextView) convertView.findViewById(R.id.startDate);
        // Populate the data into the template view using the data object
        segmentNumber.setText("1");
        startDate.setText(getStringFromDate(segment.getStartDate()));
        // Return the completed view to render on screen
        return convertView;
    }

    private String getStringFromDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        if (date == null) {
            return null;
        } else {
            return sdf.format(date);
        }
    }
}