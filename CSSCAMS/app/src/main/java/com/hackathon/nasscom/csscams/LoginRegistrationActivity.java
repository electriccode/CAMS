package com.hackathon.nasscom.csscams;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!LOGIN_REG_MODE){

                    btnAction.setText("Sign In");
                    btnSwitch.setText("Register");

                }else{

                    btnAction.setText("Register");
                    btnSwitch.setText("Sign In");

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

                    if(userName.endsWith("")||password.equals("")){
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

                    Intent intent = new Intent(LoginRegistrationActivity.this, SOSActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(LoginRegistrationActivity.this, "Unable to Login" , Toast.LENGTH_SHORT);
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

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(LoginRegistrationActivity.this,"Sign up sucesssfull", Toast.LENGTH_SHORT);

                    ParseUser parseUser = ParseUser.getCurrentUser();
                    parseUser.setUsername(userName);
                    parseUser.setEmail(email);
                    parseUser.put(PHONE,number);

                    Intent intent = new Intent(LoginRegistrationActivity.this, SOSActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(LoginRegistrationActivity.this,"Unable to Sign up", Toast.LENGTH_SHORT);
                }

                progressBar.setVisibility(View.INVISIBLE);

            }
        });

    }

}
