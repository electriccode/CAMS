package com.hackathon.nasscom.csscams;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by tarun on 07/04/18.
 */

public class LocationProvider implements LocationListener {

    private LocationManager locationManager;

    private static Location userLocation;

    public LocationProvider(Context context){

        locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);

    }

    public Location getCurrentLocation(){

        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                1000,
                0, this);

        Log.d("LocationProvider", locationManager.toString());

        return userLocation;
    }


    @Override
    public void onLocationChanged(Location location) {
        userLocation = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
