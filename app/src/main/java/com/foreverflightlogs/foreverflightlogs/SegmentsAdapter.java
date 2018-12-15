package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    TextView endDate = (TextView) convertView.findViewById(R.id.endDate);
    TextView duration = (TextView) convertView.findViewById(R.id.duration);

    ImageView pic = (ImageView) convertView.findViewById(R.id.picImage);
    ImageView dualHours = (ImageView) convertView.findViewById(R.id.dualImage);
    ImageView simulatedInstruments = (ImageView) convertView.findViewById(R.id.simInstImage);
    ImageView visualFlight = (ImageView) convertView.findViewById(R.id.visFlightImage);
    ImageView instrumentFlight = (ImageView) convertView.findViewById(R.id.instFlightImage);
    ImageView nightFlight = (ImageView) convertView.findViewById(R.id.nightFlightImage);

    // Populate the data into the template view using the data object
    String segmentNumberString = String.format("%d", position + 1);
    segmentNumber.setText(segmentNumberString);

    startDate.setText(getStringFromDate(segment.getStartDate()));
    endDate.setText(getStringFromDate(segment.getEndDate()));

    String segmentDurationText = String.format("%02d:%02d:%02d",
        TimeUnit.MILLISECONDS.toHours(getSegmentDuration(segment)),
        TimeUnit.MILLISECONDS.toMinutes(getSegmentDuration(segment)) - TimeUnit.MINUTES
            .toMinutes(TimeUnit.MILLISECONDS.toHours(getSegmentDuration(segment))),
        TimeUnit.MILLISECONDS.toSeconds(getSegmentDuration(segment)) - TimeUnit.MINUTES
            .toSeconds(TimeUnit.MILLISECONDS.toMinutes(getSegmentDuration(segment))));
    duration.setText(segmentDurationText);

    if (!segment.getPilotInCommand()) {
      pic.setBackgroundColor(Color.rgb(180, 0, 0));
    } else {
      pic.setBackgroundColor(Color.rgb(0, 180, 0));
    }

    if (!segment.getDualHours()) {
      dualHours.setBackgroundColor(Color.rgb(180, 0, 0));
    } else {
      dualHours.setBackgroundColor(Color.rgb(0, 180, 0));
    }

    if (!segment.getSimulatedInstruments()) {
      simulatedInstruments.setBackgroundColor(Color.rgb(180, 0, 0));
    } else {
      simulatedInstruments.setBackgroundColor(Color.rgb(0, 180, 0));
    }

    if (!segment.getVisualFlight()) {
      visualFlight.setBackgroundColor(Color.rgb(180, 0, 0));
    } else {
      visualFlight.setBackgroundColor(Color.rgb(0, 180, 0));
    }

    if (!segment.getinstrumentFlight()) {
      instrumentFlight.setBackgroundColor(Color.rgb(180, 0, 0));
    } else {
      instrumentFlight.setBackgroundColor(Color.rgb(0, 180, 0));
    }

    if (!segment.getNight()) {
      nightFlight.setBackgroundColor(Color.rgb(180, 0, 0));
    } else {
      nightFlight.setBackgroundColor(Color.rgb(0, 180, 0));
    }

    // Return the completed view to render on screen
    return convertView;
  }

  /**
   * getStringFromDate: Gets a string representing the date.
   *
   * @param date A date
   * @return A string representing the date.
   */
  private String getStringFromDate(Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    if (date == null) {
      return null;
    } else {
      return sdf.format(date);
    }
  }

  /**
   * getSegmentDuration: Used to get the duration of the segment.
   *
   * @param segment The segment to be inspected
   * @return Number of seconds that the segment lasted.
   */
  public long getSegmentDuration(Segment segment) {
    return segment.getEndDate().getTime() - segment.getStartDate().getTime();
  }
}