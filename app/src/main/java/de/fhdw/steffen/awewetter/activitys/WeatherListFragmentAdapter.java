/**
 * WeatherListFragmentAdapter
 * <p>
 * Adapter für die Darstellungg der Informationen in dem WeatherListFragment
 *
 * @author Steffen Höltje
 * @version 1.0
 */

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

public class WeatherListFragmentAdapter extends ArrayAdapter<Weather> {

    private ArrayList<Weather> dataSet;
    Context mContext;

    /**
     * LoockUp-Chache anzeigen (Welche Viewelemente werden vorhanden sein)
     */
    private static class ViewHolder {
        TextView textViewDay;
        ImageView imageViewIcon;
        TextView textViewMaxTemp;
        TextView textViewMinTemp;
        TextView textViewWindSpeed;
        TextView textViewWindDirection;
    }

    /**
     * Konstruktor für den Adapter
     *
     * @param data    Liste der Wetterobjekte
     * @param context Context in dem die Daten angezeigt werden sollen
     */
    public WeatherListFragmentAdapter(ArrayList<Weather> data, Context context) {
        super(context, R.layout.fragment_weather_list_row_item, data);
        this.dataSet = data;
        this.mContext = context;

    }

    /**
     * Darstellen der Wetterinformationen
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Daten aus dem Array an einer bestimmten Position holen
        Weather dataModel = getItem(position);
        // Prüfen, ob eine vorhandene Ansicht wiederverwendet wird
        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.fragment_weather_list_row_item, parent, false);

            ////Holen der Ressourcen aus den XML File
            viewHolder.textViewDay = (TextView) convertView.findViewById(R.id.textViewDay);
            viewHolder.imageViewIcon = convertView.findViewById(R.id.imageViewIcon);
            viewHolder.textViewMaxTemp = (TextView) convertView.findViewById(R.id.textViewMaxTemp);
            viewHolder.textViewMinTemp = (TextView) convertView.findViewById(R.id.textViewMinTemp);
            viewHolder.textViewWindSpeed = (TextView) convertView.findViewById(R.id.textViewWindSpeed);
            viewHolder.textViewWindDirection = (TextView) convertView.findViewById(R.id.textViewWindDirection);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        //Setzen des Icon für die jeweilige Wetterbeschreibung
        dataModel.setIconWeather(dataModel.getIconWeather().replace('n', 'd'));

        switch (dataModel.getIconWeather()) {
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

        //Setzen der weiteren Informationen
        viewHolder.textViewDay.setText(dataModel.getDayWeather());
        viewHolder.textViewMaxTemp.setText(getContext().getResources().getString(R.string.fragment_weather_list_max_temp) + " " + String.valueOf(dataModel.getTempMaxWeather()));
        viewHolder.textViewMinTemp.setText(getContext().getResources().getString(R.string.fragment_weather_list_min_temp) + " " + String.valueOf(dataModel.getTempMinWeather()));
        viewHolder.textViewWindSpeed.setText(getContext().getResources().getString(R.string.fragment_weather_list_windspeed) + " " + String.valueOf(dataModel.getWindSpeedWeather()) + getContext().getResources().getString(R.string.fragment_weather_list_windspeed_unit));
        viewHolder.textViewWindDirection.setText(getContext().getResources().getString(R.string.fragment_weather_list_wind_direction) + " " + dataModel.getWindDirectionWeather());

        return convertView;
    }
}
