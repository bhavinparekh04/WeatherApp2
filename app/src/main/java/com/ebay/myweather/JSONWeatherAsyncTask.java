package com.ebay.myweather;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ebay.myweather.model.Weather;
import com.ebay.myweather.model.WeatherForecast;
import com.ebay.myweather.openweather.OpenMapHttpAdapter;

import org.json.JSONException;

/**
 * Created by bhparekh on 7/21/16.
 */

public class JSONWeatherAsyncTask extends AsyncTask<Object, Void, WeatherForecast> {

    DailyWeatherActivity weatherActivity;
    @Override
    protected WeatherForecast doInBackground(Object... params) {
        Weather weather = new Weather();

        Log.i("Params *** : ",(String)params[0] + (String)params[1]);

        String [] strparam= new String[2];
        strparam[0]=(String)params[0];
        strparam[1]=(String)params[1];
        weatherActivity=(DailyWeatherActivity)params[2];
        WeatherForecast weatherBO = null;
        try {
            weatherBO = (new OpenMapHttpAdapter()).getWeatherBO(strparam);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return weatherBO;

    }


    @Override
    protected void onPostExecute(WeatherForecast weather) {
        super.onPostExecute(weather);
        Log.i("","Call back for weather data");
        if(weather != null) {
            weatherActivity.populateWeatherInfo(weather);
        } else {
            //TODO service error handling
            Toast.makeText(weatherActivity.getApplicationContext(),"Error getting weather data, no internet connection", Toast.LENGTH_LONG).show();

        }

    }

}
