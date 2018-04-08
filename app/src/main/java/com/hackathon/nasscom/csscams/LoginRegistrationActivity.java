package com.hackathon.nasscom.csscams;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginRegistrationActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputUserName, inputPhoneNumber;
    private Button btnSwitch, btnAction;
    private ProgressBar progressBar;

    private static final String PHONE = "phone";

    private boolean LOGIN_REG_MODE = false;

    private static final String TAG = "LogRegActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_registration);

        btnSwitch = (Button) findViewById(R.id.sign_in_button);
        btnAction = (Button) findViewById(R.id.sign_up_button);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPhoneNumber = (EditText) findViewById(R.id.phone);
        inputUserName = (EditText) findViewById(R.id.userName);
        inputPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if(ParseUser.getCurrentUser() != null && !ParseUser.getCurrentUser().getUsername().trim().equals("")){

            Log.d(TAG,"Already Logged in " + ParseUser.getCurrentUser().getUsername());
            Intent intent = new Intent(LoginRegistrationActivity.this, SOSActivity.class);
            startActivity(intent);
            finish();

        }

        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!LOGIN_REG_MODE){

                    btnAction.setText("Sign In");
                    btnSwitch.setText("Register");

                    inputEmail.setVisibility(View.GONE);
                    inputPhoneNumber.setVisibility(View.GONE);

                }else{

                    btnAction.setText("Register");
                    btnSwitch.setText("Sign In");

                    inputEmail.setVisibility(View.VISIBLE);
                    inputPhoneNumber.setVisibility(View.VISIBLE);

                }

                LOGIN_REG_MODE = !LOGIN_REG_MODE;


            }
        });

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String phone = inputPhoneNumber.getText().toString().trim();
                String userName = inputUserName.getText().toString().trim();

                if(LOGIN_REG_MODE){

                    if(userName.equals("")||password.equals("")){
                        CommonHelpers.makeShortToast(LoginRegistrationActivity.this,"Username or password missing");
                        return;
                    }

                    signInUser(userName,password);

                } else {

                    if (email.equals("") || phone.equals("") || userName.equals("") || password.equals("")) {
                        CommonHelpers.makeShortToast(LoginRegistrationActivity.this, "Username or password missing");
                        return;
                    }

                    signUpUser(userName, email, password, phone);


                }

            }
        });

    }


    private void signInUser(String userName, String password) {

        progressBar.setVisibility(View.VISIBLE);

        ParseUser.logInInBackground(userName, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {

                    ParseUser parseUser = ParseUser.getCurrentUser();
                    parseUser.setUsername(user.getUsername());
                    parseUser.setEmail(user.getEmail());
                    parseUser.put(PHONE,user.get(PHONE));


                    Log.d(TAG,"Login Successfull");
                    Intent intent = new Intent(LoginRegistrationActivity.this, SOSActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Log.d(TAG,"Unable to Login");
                    Toast.makeText(LoginRegistrationActivity.this, "Unable to Login due to " +e.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.INVISIBLE);

            }

        });


    }


    private void signUpUser(final String userName, final String email, final String password, final String number){

        progressBar.setVisibility(View.VISIBLE);

        ParseUser user = new ParseUser();
        user.setUsername(userName);
        user.setPassword(password);
        user.setEmail(email);
        user.put(PHONE, number);
        user.put("role","user");

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(LoginRegistrationActivity.this,"Sign up sucessfull", Toast.LENGTH_SHORT);

                    ParseUser parseUser = ParseUser.getCurrentUser();
                    parseUser.setUsername(userName);
                    parseUser.setEmail(email);
                    parseUser.put(PHONE,number);

                    Log.d(TAG,"Sign up successfull");

                    Intent intent = new Intent(LoginRegistrationActivity.this, SOSActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    Log.d(TAG,"Sign up failed");

                    Toast.makeText(LoginRegistrationActivity.this,"Unable to Sign up due to " + e.getMessage()
                            , Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.INVISIBLE);

            }
        });

    }

}
