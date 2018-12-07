package de.fhdw.steffen.awewetter.activitys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.fhdw.steffen.awewetter.R;
import de.fhdw.steffen.awewetter.classes.Weather;

public class WeatherListFragmentAdapter extends ArrayAdapter<Weather>{

    private ArrayList<Weather> dataSet;
    Context mContext;

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

        viewHolder.imageViewIcon.setImageResource(R.drawable.ic_cloud);
        viewHolder.textViewDay.setText(dataModel.getDayWeather());
        viewHolder.textViewMaxTemp.setText(String.valueOf(dataModel.getTempMaxWeather()));
        viewHolder.textViewMinTemp.setText(String.valueOf(dataModel.getTempMinWeather()));
        viewHolder.textViewWindSpeed.setText(String.valueOf(dataModel.getWindSpeedWeather()));
        viewHolder.textViewWindDirection .setText(dataModel.getWindDirectionWeather());
        // Return the completed view to render on screen
        return convertView;
    }
}
