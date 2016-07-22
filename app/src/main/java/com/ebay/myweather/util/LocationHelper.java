package com.ebay.myweather.util;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ebay.myweather.DailyWeatherActivity;

/**
 * Created by bhparekh on 7/20/2016.
 */
public class LocationHelper {

    private static final int ALLOWED_TIME = 1000 * 60 * 30;


    static LocationManager lm =null;
    /**
     * Get last known location
     * No listener used of location is updated in the last 30 mins
     * If location is older than 30 mins, we will create a listener to update the location data.
     * @return
     */

    public static Location getLocation(DailyWeatherActivity activity) {

        lm = (LocationManager)activity.getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

        Location location = null;
        String bestProvider = lm.getBestProvider(criteria, false);
        try {
            location = lm.getLastKnownLocation(bestProvider);

        } catch (SecurityException e) {
            // TODO settings redirection
            e.printStackTrace();
            Toast.makeText(activity.getApplicationContext(),"Please enable location access for the application via Settings -> Location Services", Toast.LENGTH_LONG).show();
            return null;
        }

        if(location != null) {
            if(System.currentTimeMillis()-location.getTime() < ALLOWED_TIME) {

                return location;

            } else {

                /**
                 * Dont return outdated location
                 */
            }
        }

        try {
            // 1000 meter distance to avoid multiple calls backs, listener will be nullified after 1st callback
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1000, locationListener);
        } catch (SecurityException e) {

        }
        // location is null
        return null;
    }

    private static final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {


            Log.i("Location in call back",location.toString());
          //  getWeatherDataAsync(location);

            try {
                lm.removeUpdates(locationListener);
            } catch (SecurityException e) {
            }
            lm = null;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void getWeatherDataAsync(Location location) {
        String city = "London,UK";
     //   JSONWeatherTask task = new JSONWeatherTask();
     //   task.execute(new String[]{location.getLatitude()+"",location.getLongitude()+""});
    }
}
