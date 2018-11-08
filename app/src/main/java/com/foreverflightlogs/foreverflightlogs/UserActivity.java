package com.foreverflightlogs.foreverflightlogs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    }

    public boolean loginUser(String phoneNumber, String password) {
        return false;
    }
}
