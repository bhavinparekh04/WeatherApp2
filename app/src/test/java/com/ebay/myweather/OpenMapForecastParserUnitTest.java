package com.ebay.myweather;

import com.ebay.myweather.model.WeatherForecast;
import com.ebay.myweather.openweather.OpenMapForecastParser;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class OpenMapForecastParserUnitTest {

    @Test
    public void testParsing() throws Exception {

        //todo resource bundle
        String forecast="{\"city\":{\"id\":5809844,\"name\":\"Seattle\",\"coord\":{\"lon\":-122.332069,\"lat\":47.606209},\"country\":\"US\",\"population\":0,\"sys\":{\"population\":0}},\"cod\":\"200\",\"message\":0.0099,\"cnt\":40,\"list\":[{\"dt\":1469156400,\"main\":{\"temp\":77.4,\"temp_min\":77.4,\"temp_max\":77.4,\"pressure\":1019.69,\"sea_level\":1030.24,\"grnd_level\":1019.69,\"humidity\":\"52\",\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":\"3.38\",\"deg\":352.003},\"rain\":{},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2016-07-27 00:00:00\"}]}";
        String weather="{\"coord\":{\"lon\":-122.33,\"lat\":47.61},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"base\":\"cmc stations\",\"main\":{\"temp\":76.95,\"pressure\":1016,\"humidity\":\"36\",\"temp_min\":73.99,\"temp_max\":80.6},\"wind\":{\"speed\":\"5.82\",\"deg\":320},\"rain\":{\"1h\":0.25},\"clouds\":{\"all\":75},\"dt\":1469154956,\"sys\":{\"type\":1,\"id\":2949,\"message\":0.0037,\"country\":\"US\",\"sunrise\":1469190931,\"sunset\":1469246123},\"id\":5809844,\"name\":\"Seattle\",\"cod\":200}";
        WeatherForecast weatherObject = OpenMapForecastParser.getForecast(forecast, weather);

        assertEquals(weatherObject.city,"Seattle");
        assertEquals(weatherObject.weatherData.size(),1);
        assertEquals(weatherObject.weatherData.get(0).description,"clear sky");
    }
}