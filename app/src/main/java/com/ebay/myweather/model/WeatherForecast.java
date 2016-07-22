/**
 * This is a tutorial source code 
 * provided "as is" and without warranties.
 *
 * For any question please visit the web site
 * http://www.survivingwithandroid.com
 *
 * or write an email to
 * survivingwithandroid@gmail.com
 *
 */
package com.ebay.myweather.model;


import java.util.ArrayList;
import java.util.List;

public class WeatherForecast {
	
	public String city;
	public String country;

	public float curr_temp;
	public String curr_humidity;
	public String curr_icon;
	public String curr_description;
	public String curr_wind;

	public List<HourlyData> weatherData = new ArrayList<>();
}
