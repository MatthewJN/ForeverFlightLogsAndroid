package com.foreverflightlogs.foreverflightlogs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Observable;
import java.util.Observer;

public class UserActivity extends AppCompatActivity implements Observer {

  UserManager userManager;
  Button loginButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user);

    AirportPresenter airportPresenter = new AirportPresenter();
    airportPresenter.fetchAirports(getApplicationContext());

    userManager = UserManager.getInstance(getApplicationContext());

//        userManager = new UserManager(getApplicationContext());
    if (userManager.getAuthCode() != "") {
      Intent intent = new Intent(this, MainActivity.class);
      startActivity(intent);
    }

    userManager.addObserver(this);
    loginButton = (Button) findViewById(R.id.button);
  }

  /**
   * Called when the user taps the login button
   */
  public void login(View view) {
    EditText editText = (EditText) findViewById(R.id.editText);
    EditText editText2 = (EditText) findViewById(R.id.password);

    String username = editText.getText().toString();
    String password = editText2.getText().toString();

    boolean isValid = true;

    if (username.length() != 10) {
      isValid = false;
    }

    if (password.length() == 0) {
      isValid = false;
    }
    if (isValid) {
      //userManager = new UserManager(getApplicationContext());
      userManager.authWithAPI(username, password, getApplicationContext());
      loginButton.setEnabled(false);
    }
  }

  public void createAccount(View view) {
    Intent intent = new Intent(this, CreateAccountActivity.class);
    startActivity(intent);
  }

  /**
   * theIntent This is called if the login was succesfull and the activity is to move to main.
   */
  private void theIntent() {
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
  }

  /**
   * update: Used for the observer.
   *
   * @param o The observable object.
   * @param arg The argument passed.
   */
  @Override
  public void update(Observable o, Object arg) {

    if (o != null && o instanceof UserManager) {
      if (arg == "200" && userManager.getAuthCode() != "") {
        theIntent();
      } else {
        this.runOnUiThread(new Runnable() {
          public void run() {
            Toast.makeText(UserActivity.this, "Invalid Login Details - Please Try Again",
                Toast.LENGTH_SHORT).show();
            loginButton.setEnabled(true);
          }
        });
      }
    }
  }
}
