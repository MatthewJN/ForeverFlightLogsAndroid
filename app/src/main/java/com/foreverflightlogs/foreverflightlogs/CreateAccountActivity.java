package com.foreverflightlogs.foreverflightlogs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

/**
 * Create Account Activity
 * User will be able to create an account by providing their
 * phone number, name, and password.
 * After successful creation, they will be taken to login screen
 * where they will login, to create an authorization code to begin
 * using the site
 */
public class CreateAccountActivity extends AppCompatActivity implements Observer {

    CreateAccountPresenter createAccountPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        createAccountPresenter = CreateAccountPresenter.getInstance(getApplicationContext());
        createAccountPresenter.addObserver(this);

    }

    /**
     * Take user to login screen
     * For users who already have an account
     */
    public void onclickLogin(View view){
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
;    }

    public void createNewAccount(View view){
        EditText editPhone = (EditText) findViewById(R.id.phone);
        EditText editName = (EditText) findViewById(R.id.name);
        EditText editPassword = (EditText) findViewById(R.id.password);
        EditText editConfirmPassword = (EditText) findViewById(R.id.confirmPassword);
        Button createAccount = (Button) findViewById(R.id.btn_createAccount);

        String phone = editPhone.getText().toString();
        String name = editName.getText().toString();
        String password = editPassword.getText().toString();
        String confirm = editConfirmPassword.getText().toString();

        //validate input items
          boolean isValid = true;
            if(phone.length() < 10 ) {
                editPhone.setError("Please enter a 10 digit phone number");
                isValid = false;
                Log.i("PHONE", "phone is: " + phone);
                Log.i("PHONELNGTH", "phone.length is: " + phone.length());
            }
            if(name.length() == 0) {
                editName.setError("Please enter your name");
                isValid = false;
                Log.i("NAME", "name is: " + name);
                Log.i("NAMELNGTH", "name.length is: " + name.length());
            }
            if(password.length() == 0){
                editPassword.setError("Please enter a password");
                isValid = false;
                Log.i("PASSWORD", "password is: " + password);
                Log.i("PASSWORDLNGTH", "password.length is: " + password.length());
            }
            if( confirm.length() == 0 || !password.equals(confirm)){
                editConfirmPassword.setError("Passwords do not match. Please enter a password.");
                isValid = false;
                Log.i("PASSWORD", "password "+ password);
                Log.i("CONFIRM", "confirmPassword " + confirm);
            }



            //if passwords don't match display an error
            if(password.equals(confirm) && isValid){

                createAccountPresenter.createAccountOnAPI(phone, password, name, getApplicationContext());
            }
        }
    private void theIntent(){
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(intent);
        finish();
    }

    @Override
    public void update(Observable o, Object arg) {

        if (o != null && o instanceof CreateAccountPresenter) {
            if (arg == "200" ) {
                this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(CreateAccountActivity.this, "Account Successfully Created, Please Login", Toast.LENGTH_SHORT).show();
                    }
                });
                theIntent();
            }
            else {
                this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(CreateAccountActivity.this, "Invalid Registration Details - Create Account Online", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

    }
}
