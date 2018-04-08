package com.hackathon.nasscom.csscams;

import android.app.Application;
import android.os.StrictMode;
import android.util.Log;

import com.parse.Parse;

/**
 * Created by tarun on 07/04/18.
 */

public class SCCAMSApp extends Application {

    private static final String TAG = "SCCAMSApp";

    @Override
    public void onCreate() {
        super.onCreate();
        //Parse.initialize(this,Config.PARSE_APP_ID,Config.PARSE_CLIENT_KEY);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(Config.PARSE_APP_ID)
                .server(Config.PARSE_SERVER_URL)
                .build()
        );

        LocationProvider locationProvider = new LocationProvider(this);
        locationProvider.getCurrentLocation();

    }
}
