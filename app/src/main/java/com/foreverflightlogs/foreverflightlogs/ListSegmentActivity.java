package com.foreverflightlogs.foreverflightlogs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ListSegmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_segment);
    }

    public void addRemarksPressed(View view) {
        Intent intent = new Intent(this, FinalizeFlightActivity.class);
        startActivity(intent);
    }
}
