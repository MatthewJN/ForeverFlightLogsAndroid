package com.foreverflightlogs.foreverflightlogs;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FlightsAdapter extends ArrayAdapter<Flight> {

  public FlightsAdapter(Context context, List<Flight> flights) {
    super(context, 0, flights);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    // Get the data item for this position
    Flight flight = getItem(position);

    // Check if an existing view is being reused, otherwise inflate the view
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext())
          .inflate(R.layout.list_flight_item, parent, false);
    }

    // Lookup view for data population
    TextView flightOrigin = (TextView) convertView.findViewById(R.id.flightsDeparture);
    TextView flightDestination = (TextView) convertView.findViewById(R.id.flightsArrival);
    TextView startDate = (TextView) convertView.findViewById(R.id.flightsStartDate);
    TextView startTime = (TextView) convertView.findViewById(R.id.flightsStartTime);
    TextView endDate = (TextView) convertView.findViewById(R.id.flightsEndDate);
    TextView endTime = (TextView) convertView.findViewById(R.id.flightsEndTime);
    TextView nNumber = (TextView) convertView.findViewById(R.id.flightsNNumber);
    TextView segments = (TextView) convertView.findViewById(R.id.flightsNumberOfSegments);

    ImageView crossCountry = (ImageView) convertView.findViewById(R.id.flightsCrossCountry);
    ImageView solo = (ImageView) convertView.findViewById(R.id.flightsSolo);
    ImageView synced = (ImageView) convertView.findViewById(R.id.flightsSynced);
    ImageView inProgress = (ImageView) convertView.findViewById(R.id.flightsInProgress);

    // Populate the data into the template view using the data object
    //Log.i("GETORIGIN", flight.getOrigin());
    flightOrigin.setText(flight.getOrigin());
    flightDestination.setText(flight.getDestination());

    SegmentPresenter segmentPresenter = new SegmentPresenter(flight.getFlightID(), getContext());
    String numberOfSegments = String.format("%d", segmentPresenter.segments.size());
    segments.setText(numberOfSegments);

    startDate.setText(getStringFromDate(flight.getStartDate()));
    startTime.setText(getTimeStringFromDate(flight.getStartDate()));

    endDate.setText(getStringFromDate(flight.getEndDate()));
    endTime.setText(getTimeStringFromDate(flight.getEndDate()));

    nNumber.setText(flight.getAircraft());

    if (!flight.getCrosscountry()) {
      crossCountry.setBackgroundColor(Color.rgb(180, 0, 0));
    } else {
      crossCountry.setBackgroundColor(Color.rgb(0, 180, 0));
    }

    if (!flight.getSolo()) {
      solo.setBackgroundColor(Color.rgb(180, 0, 0));
    } else {
      solo.setBackgroundColor(Color.rgb(0, 180, 0));
    }

    if (!flight.getHasSynced()) {
      synced.setBackgroundColor(Color.rgb(180, 0, 0));
    } else {
      synced.setBackgroundColor(Color.rgb(0, 180, 0));
    }

    if (!flight.getInProgress()) {
      inProgress.setBackgroundColor(Color.rgb(180, 0, 0));
    } else {
      inProgress.setBackgroundColor(Color.rgb(0, 180, 0));
      convertView.setBackgroundColor(Color.argb(25, 180, 0, 0));
    }

    // Return the completed view to render on screen
    return convertView;
  }

  private String getStringFromDate(Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");
    if (date == null) {
      return null;
    } else {
      return sdf.format(date);
    }
  }

  private String getTimeStringFromDate(Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    if (date == null) {
      return null;
    } else {
      return sdf.format(date);
    }
  }

}