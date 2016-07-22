package com.ebay.myweather.openweather;

import android.util.Log;

import com.ebay.myweather.util.HttpClient;
import com.ebay.myweather.model.WeatherForecast;

import org.json.JSONException;

public class OpenMapHttpAdapter extends HttpClient {

	//TODO hardcoded APIKEY
	private static String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?mode=json&units=imperial&appid=a65c4c5f57dbf8fe1ff8bc7b930fc9e3&";
	private static String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?mode=json&units=imperial&appid=a65c4c5f57dbf8fe1ff8bc7b930fc9e3&";

	private static String IMG_URL = "http://openweathermap.org/img/w/";


	public String getWeatherData(String location) {
		return getData(WEATHER_URL + location);
	}

	public WeatherForecast getWeatherBO(String... location) throws JSONException {
		StringBuilder query = new StringBuilder("lat=").append(location[0]).append("&lon=").append(location[1]);
		Log.i("Query string : " , query.toString());
		String forecastJson =  getData(FORECAST_URL + query.toString());
		String weatherJson =  getData(WEATHER_URL + query.toString());

		Log.i("Forecast : ",forecastJson);
		Log.i("Weather : ",weatherJson);
		if(forecastJson !=null && weatherJson !=null)
			return OpenMapForecastParser.getForecast(forecastJson, weatherJson);

		return null;
	}


}
