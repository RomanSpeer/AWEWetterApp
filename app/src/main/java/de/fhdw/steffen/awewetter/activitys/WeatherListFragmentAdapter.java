package de.fhdw.steffen.awewetter.activitys;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;

import java.util.ArrayList;

import de.fhdw.steffen.awewetter.R;
import de.fhdw.steffen.awewetter.classes.Server;
import de.fhdw.steffen.awewetter.classes.Weather;

public class WeatherListFragmentAdapter extends ArrayAdapter<Weather>{

    private ArrayList<Weather> dataSet;
    Context mContext;

    private Server server;

    // View lookup cache
    private static class ViewHolder {
        TextView textViewDay;
        ImageView imageViewIcon;
        TextView textViewMaxTemp;
        TextView textViewMinTemp;
        TextView textViewWindSpeed;
        TextView textViewWindDirection;
    }

    public WeatherListFragmentAdapter(ArrayList<Weather> data, Context context) {
        super(context, R.layout.fragment_weather_list_row_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get the data item for this position
        Weather dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.fragment_weather_list_row_item, parent, false);

            viewHolder.textViewDay = (TextView) convertView.findViewById(R.id.textViewDay);
            viewHolder.imageViewIcon = convertView.findViewById(R.id.imageViewIcon);
            viewHolder.textViewMaxTemp = (TextView) convertView.findViewById(R.id.textViewMaxTemp);
            viewHolder.textViewMinTemp = (TextView) convertView.findViewById(R.id.textViewMinTemp);
            viewHolder.textViewWindSpeed = (TextView) convertView.findViewById(R.id.textViewWindSpeed);
            viewHolder.textViewWindDirection = (TextView) convertView.findViewById(R.id.textViewWindDirection);

            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
            {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        dataModel.setIconWeather(dataModel.getIconWeather().replace('n','d'));

        switch (dataModel.getIconWeather())
        {
            case "01d":
                viewHolder.imageViewIcon.setImageResource(R.drawable.sun01d);
                break;
            case "02d":
                viewHolder.imageViewIcon.setImageResource(R.drawable.fewclouds02d);
                break;
            case "03d":
                viewHolder.imageViewIcon.setImageResource(R.drawable.scatteredclouds03d);
                break;
            case "04d":
                viewHolder.imageViewIcon.setImageResource(R.drawable.brokenclouds04d);
                break;
            case "09d":
                viewHolder.imageViewIcon.setImageResource(R.drawable.showerrain09d);
                break;
            case "10d":
                viewHolder.imageViewIcon.setImageResource(R.drawable.rain10d);
                break;
            case "11d":
                viewHolder.imageViewIcon.setImageResource(R.drawable.thunderstorm11d);
                break;
            case "13d":
                viewHolder.imageViewIcon.setImageResource(R.drawable.snow13d);
                break;
            case "50d":
                viewHolder.imageViewIcon.setImageResource(R.drawable.mist50d);
                break;
            default:
                viewHolder.imageViewIcon.setImageResource(R.drawable.image_not_found);
                ;
        }

        viewHolder.textViewDay.setText(dataModel.getDayWeather());
        viewHolder.textViewMaxTemp.setText(getContext().getResources().getString(R.string.fragment_weather_list_max_temp) + " " + String.valueOf(dataModel.getTempMaxWeather()));
        viewHolder.textViewMinTemp.setText(getContext().getResources().getString(R.string.fragment_weather_list_min_temp) +  " " + String.valueOf(dataModel.getTempMinWeather()));
        viewHolder.textViewWindSpeed.setText(getContext().getResources().getString(R.string.fragment_weather_list_windspeed) +  " " + String.valueOf(dataModel.getWindSpeedWeather()) + getContext().getResources().getString(R.string.fragment_weather_list_windspeed_unit));
        viewHolder.textViewWindDirection .setText(getContext().getResources().getString(R.string.fragment_weather_list_wind_direction) + " " +  dataModel.getWindDirectionWeather());
        // Return the completed view to render on screen
        return convertView;
    }
}
