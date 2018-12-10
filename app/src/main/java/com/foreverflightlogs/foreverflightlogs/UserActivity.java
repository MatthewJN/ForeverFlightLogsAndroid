package com.foreverflightlogs.foreverflightlogs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    }

    // quick test comment to push
    public boolean loginUser(String phoneNumber, String password) {


        return false;
    }

    /** Called when the user taps the login button */
    public void login(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        EditText editText2 = (EditText) findViewById(R.id.editText2);

        String username = editText.getText().toString();
        String password = editText2.getText().toString();

        UserManager userManager = new UserManager(getApplicationContext());
        userManager.authWithAPI(username, password, getApplicationContext());

        Intent intent = new Intent(this, MainActivity.class);

        //Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show();

        startActivity(intent);
    }

}
