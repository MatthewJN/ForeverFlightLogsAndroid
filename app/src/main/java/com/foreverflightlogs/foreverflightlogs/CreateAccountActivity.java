package com.foreverflightlogs.foreverflightlogs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Create Account Activity
 * User will be able to create an account by providing their
 * phone number, name, and password.
 * After successful creation, they will be taken to login screen
 * where they will login, to create an authorization code to begin
 * using the site
 */
public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

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
        final EditText editPhone = (EditText) findViewById(R.id.phone);
        final EditText editName = (EditText) findViewById(R.id.name);
        final EditText editPassword = (EditText) findViewById(R.id.password);
        final EditText editConfirmPassword = (EditText) findViewById(R.id.confirmPassword);
        final Button createAccount = (Button) findViewById(R.id.btn_createAccount);

        final String phone = editPhone.getText().toString();
        final String name = editName.getText().toString();
        final String password = editPassword.getText().toString();
        final String confirm = editConfirmPassword.getText().toString();

        //validate input items
        createAccount.setOnClickListener(new View.OnClickListener() {
          boolean isValid;
            @Override
            public void onClick(View v) {
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

            isValid = true;

                //if passwords don't match display an error
                if(password.equals(confirm)){
                    CreateAccountPresenter createAccountPresenter = new CreateAccountPresenter(getApplicationContext());
                    boolean isAccountCreated = createAccountPresenter.createAccountOnAPI(phone, password, name, getApplicationContext());
                    if( isAccountCreated){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        Toast.makeText(getApplicationContext(), "Your account has been created, please login.", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Error creating account.", Toast.LENGTH_SHORT).show();
                        Log.i("ACCTERROR", "Error creating acct: " + createAccountPresenter.getResponse());
                    }
                }
            }

        });




    }

}
