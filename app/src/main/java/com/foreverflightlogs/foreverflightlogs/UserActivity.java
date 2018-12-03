package com.foreverflightlogs.foreverflightlogs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UserActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    }

    public boolean loginUser(String phoneNumber, String password) {

        return false;
    }

    /** Called when the user taps the login button */
    public void login(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        EditText editText2 = (EditText) findViewById(R.id.editText2);

        String username = editText.getText().toString();
        String password = editText2.getText().toString();

        /** This was for testing purposes for the InternetConnection class: */
        // InternetConnection connection = new InternetConnection();
        // connection.isInternetOn(this);

        // only allow logging in and progressing to next screen if connected to internet
        // if (connection.getConnection() == true) {
        //     new UserManager().authWithAPI(username, password, getApplicationContext());
        //     Intent intent = new Intent(this, MainActivity.class);
        //     Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show();
        //     startActivity(intent);
        // } else {
        //     Toast.makeText(this, "Unable to connect", Toast.LENGTH_SHORT).show();
        // } */

        new UserManager().authWithAPI(username, password, getApplicationContext());
        Intent intent = new Intent(this, MainActivity.class);
        Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show();
        startActivity(intent);


    }

}
