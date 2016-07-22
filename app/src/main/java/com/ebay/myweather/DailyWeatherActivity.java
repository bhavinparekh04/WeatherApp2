package com.ebay.myweather;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebay.myweather.model.HourlyData;
import com.ebay.myweather.model.WeatherForecast;
import com.ebay.myweather.util.LocationHelper;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class DailyWeatherActivity extends Activity {


    private TextView cityText;
    private TextView temp;
    private TextView windSpeed;
    private TextView humLab;
    private TextView windLab;
    HourlyWeatherListAdapter adapter;
    LocationManager lm = null;

    private TextView hum;
    private ImageView imgView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(lm == null)
        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_weather);
        adapter = new HourlyWeatherListAdapter(getApplicationContext(), new ArrayList<HourlyData>());

        setUpUIFields();

        Location location = getLocation();

        location = LocationHelper.getLocation(this);
        if(location!= null) {
            Log.i("Got cached location","");
            getWeatherDataAsync(location);
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onResume() {

        super.onResume();

        if(lm == null)
            lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        Log.i("Activity Resumed","");
        Location location = getLocation();

        if(location!= null) {
            Log.i("Got cached location","");
            getWeatherDataAsync(location);
        }
    }

    private void getWeatherDataAsync(Location location) {

        JSONWeatherAsyncTask task = new JSONWeatherAsyncTask();
        task.execute(new Object[]{location.getLatitude()+"",location.getLongitude()+"",this});

    }

    private void setUpUIFields() {
        cityText = (TextView) findViewById(R.id.cityText);
        temp = (TextView) findViewById(R.id.temp);
        hum = (TextView) findViewById(R.id.hum);
        humLab = (TextView) findViewById(R.id.humLab);
        windLab = (TextView) findViewById(R.id.windLab);
        windSpeed = (TextView) findViewById(R.id.windSpeed);
        imgView = (ImageView) findViewById(R.id.condIcon);
    }

    private static final int ALLOWED_TIME = 1000 * 60 * 30;

    /**
     * Get last known location
     * No listener used of location is updated in the last 30 mins
     * If location is older than 30 mins, we will create a listener to update the location data.
     * @return
     */
    private Location getLocation() {

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

        Location location = null;
        String bestProvider = lm.getBestProvider(criteria, false);
        try {
            location = lm.getLastKnownLocation(bestProvider);

        } catch (SecurityException e) {
            // TODO settings redirection
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Please enable location access for the application via Settings -> Location Services", Toast.LENGTH_LONG).show();
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

            boolean gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


            // 1000 meter distance to avoid multiple calls backs, listener will be nullified after 1st callback
            // search for network or gps location

            if(gps_enabled || network_enabled) {
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1000, locationListener);
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1000, locationListener);
            } else {
                Toast.makeText(getApplicationContext(),"Unable to get device location", Toast.LENGTH_LONG).show();

            }

        } catch (SecurityException e) {

        }
        // location is null
        return null;
    }

    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {


            Log.i("Location in call back",location.toString());
            getWeatherDataAsync(location);

            try {
                lm.removeUpdates(locationListener);
            } catch (SecurityException e) {
            }
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
    public void populateWeatherInfo(WeatherForecast weather) {
        HourlyData currentHour = weather.weatherData.get(0);

        cityText.setText(weather.city + "," + weather.country);
        temp.setText("" +Math.round((weather.curr_temp))  + " F");
        hum.setText("" + weather.curr_humidity + "%");
        windSpeed.setText("" + weather.curr_wind + " MHP");
        windLab.setText("Wind");
        humLab.setText("Humidity");

        int resID = getResources().getIdentifier("img"+currentHour.icon , "drawable", getPackageName());
        imgView.setImageResource(resID);

        adapter.addAll(weather.weatherData);
        ListView listView = (ListView) findViewById(R.id.hourList);
        listView.setAdapter(adapter);
    }



}
