package com.foreverflightlogs.foreverflightlogs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ListSegmentActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
    long flightID;
    public final static String ID_EXTRA = "com.foreverflightlogs.ListSegmentActivity._ID";
    public static final String FLIGHTID = "com.foreverflightlogs.FLIGHTID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_segment);


        // Get the flight ID
        Intent intent = getIntent();
        flightID = intent.getLongExtra(SegmentActivity.FLIGHTID, -1);

        //reference items on screen
        Button btnAddRemarks = (Button)findViewById(R.id.button_add_remarks);
        listView = (ListView)findViewById(R.id.segmentList);
        //to use preloaded layout - must use android.R.layout...
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        listView.setAdapter(adapter);

        LoadSegmentItem load = new LoadSegmentItem(flightID,ListSegmentActivity.this, adapter);
        load.execute();
        //make each list item clickable and open editSegmentActivity
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(ListSegmentActivity.this, EditSegmentActivity.class);
                intent.putExtra("ID_EXTRA", String.valueOf(id));  //@todo how to pass in the values to prepopulate EditSegmentActivity for item clicked?

                startActivity(intent);
            }
        });
    }

    public void addRemarksPressed(View view) {
        Intent intent = new Intent(this, FinalizeFlightActivity.class);
        intent.putExtra(FLIGHTID, flightID);
        startActivity(intent);
    }
}
