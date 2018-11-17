package com.foreverflightlogs.foreverflightlogs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ListSegmentActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_segment);

        //reference items on screen
        Button btnAddRemarks = (Button)findViewById(R.id.button_add_remarks);
        listView = (ListView)findViewById(R.id.segmentList);
        //to use preloaded layout - must use android.R.layout...
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        listView.setAdapter(adapter);
            int segmentID = 1; //@todo remove this and pass it current segmentID
            LoadSegmentItem load = new LoadSegmentItem(segmentID,ListSegmentActivity.this, adapter);
            load.execute();

    }

    public void addRemarksPressed(View view) {
        Intent intent = new Intent(this, FinalizeFlightActivity.class);
        startActivity(intent);
    }
}
