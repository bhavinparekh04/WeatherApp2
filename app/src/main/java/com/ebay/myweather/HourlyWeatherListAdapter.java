package com.ebay.myweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ebay.myweather.model.HourlyData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * List to hold weather records (3 hour period)
 * Created by bhparekh on 7/18/2016.
 */

public class HourlyWeatherListAdapter extends ArrayAdapter<HourlyData> {

    static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd hh:mm a");

    public HourlyWeatherListAdapter(Context context, List<HourlyData> data) {
        super(context, 0, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /**
         * Get ith element
         */
        HourlyData weatherData = getItem(position);

        /**
         * Get view holder
         */
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hour_weather_row, parent, false);
        }

        Date weatherDt = new Date(weatherData.date*1000);

        TextView time = (TextView) convertView.findViewById(R.id.forecasttime);
        TextView temp = (TextView) convertView.findViewById(R.id.temp);
        TextView windSpeed = (TextView) convertView.findViewById(R.id.windSpeed);
        ImageView icon = (ImageView) convertView.findViewById(R.id.condIcon);

        time.setText(sdf.format(weatherDt));
        temp.setText(Math.round((weatherData.temp))  + " F");
        windSpeed.setText(weatherData.wind + " MPH");

        int resID = getContext().getResources().getIdentifier("img"+weatherData.icon , "drawable", getContext().getPackageName());
        icon.setImageResource(resID);

        return convertView;
    }
}
