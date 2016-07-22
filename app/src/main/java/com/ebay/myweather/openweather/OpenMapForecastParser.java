/**
 * This is a tutorial source code
 * provided "as is" and without warranties.
 * <p/>
 * For any question please visit the web site
 * http://www.survivingwithandroid.com
 * <p/>
 * or write an email to
 * survivingwithandroid@gmail.com
 */
package com.ebay.myweather.openweather;

import com.ebay.myweather.model.HourlyData;
import com.ebay.myweather.model.Location;
import com.ebay.myweather.model.Weather;
import com.ebay.myweather.model.WeatherForecast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class OpenMapForecastParser {

    public static WeatherForecast getForecast(String forecastJson, String weatherJson) throws JSONException {
        WeatherForecast weather = null;
        if (weatherJson != null && weatherJson.length() > 0) {
            weather = new WeatherForecast();
            JSONObject currentObj = new JSONObject(weatherJson);

            JSONObject weatherjson = currentObj.getJSONArray("weather").getJSONObject(0);
            JSONObject main = currentObj.getJSONObject("main");

            weather.curr_wind = currentObj.getJSONObject("wind").getString("speed");
            weather.curr_description = weatherjson.getString("description");
            weather.curr_icon = weatherjson.getString("icon");
            weather.curr_humidity = main.getString("humidity");
            weather.curr_temp= (float)main.getDouble("temp");


            if (forecastJson != null && forecastJson.length() > 0) {
                JSONObject jObj = new JSONObject(forecastJson);
                JSONObject city = getObject("city", jObj);

                if (city != null) {
                    weather.city = getString("name", city);
                    weather.country = getString("country", city);
                }

                JSONArray list = jObj.getJSONArray("list");

                for (int i = 0; i < list.length(); i++) {

                    JSONObject listItem = list.getJSONObject(i);
                    HourlyData hourlyData = new HourlyData();
                    weatherjson = listItem.getJSONArray("weather").getJSONObject(0);
                    main = listItem.getJSONObject("main");

                    hourlyData.date = listItem.getLong("dt");
                    hourlyData.wind = listItem.getJSONObject("wind").getString("speed");
                    hourlyData.description = weatherjson.getString("description");
                    hourlyData.icon = weatherjson.getString("icon");
                    hourlyData.humidity = main.getString("humidity");
                    hourlyData.temp= (float)main.getDouble("temp");

                    weather.weatherData.add(hourlyData);
                }
            }
        }

        return weather;
    }


    private static JSONObject getObject(String tagName, JSONObject jObj) throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        jObj.getString("cod");
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }

}
