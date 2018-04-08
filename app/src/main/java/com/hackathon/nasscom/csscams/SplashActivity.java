package com.hackathon.nasscom.csscams;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;

public class SplashActivity extends AppCompatActivity {


    private static final int START_PARSE_LOGIN_ACTIVITY = 0;

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log.d(TAG,"Entered the splash activity");

        ParseLoginBuilder builder = new ParseLoginBuilder(SplashActivity.this);
        startActivityForResult(builder.build(), START_PARSE_LOGIN_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == START_PARSE_LOGIN_ACTIVITY) {

            Log.d(TAG,"Back from Parse User");

            if(resultCode == RESULT_OK){
                Log.d(TAG, ParseUser.getCurrentUser().getUsername() + "is logged in");
            }


        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
