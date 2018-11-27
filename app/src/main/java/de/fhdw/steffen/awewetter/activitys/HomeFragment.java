package de.fhdw.steffen.awewetter.activitys;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.fhdw.steffen.awewetter.R;

public class HomeFragment extends Fragment {

    //Views
    private View viewHome;
    public LinearLayout linearLayoutWeatherHome;
    public TextView textViewCityHome;
    public ImageView imageViewWeatherHome;
    public TextView textViewTemperatureHome;
    public TextView textViewPrecipitationHome;
    public TextView textViewSunriseHome;
    public TextView textViewSunsetHome;
    public TextView textViewInformationHome;
    public TextView textViewUpdateHome;

    private boolean citySet = true;

    //Strings weather
    private String city = "";
    private String temerature = "";
    private String precipitation = "";
    private String sunrise = "";
    private String sunset = "";
    private String information = "";
    private String update = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewHome = inflater.inflate(R.layout.fragment_home, container, false);
        linearLayoutWeatherHome = viewHome.findViewById(R.id.linearLayoutWeatherHome);
        textViewCityHome = viewHome.findViewById(R.id.textViewCityHome);
        imageViewWeatherHome = viewHome.findViewById(R.id.imageViewWeatherHome);
        textViewTemperatureHome = viewHome.findViewById(R.id.textViewTemperatureHome);
        textViewPrecipitationHome = viewHome.findViewById(R.id.textViewPrecipitationHome);
        textViewSunriseHome = viewHome.findViewById(R.id.textViewSunriseHome);
        textViewSunsetHome = viewHome.findViewById(R.id.textViewSunsetHome);
        textViewInformationHome = viewHome.findViewById(R.id.textViewInformationHome);
        textViewUpdateHome = viewHome.findViewById(R.id.textViewUpdateHome);

        if(citySet==true)
        {
            linearLayoutWeatherHome.setVisibility(View.VISIBLE);

            textViewCityHome.setText(getResources().getString(R.string.fragment_home_city) + city);
            textViewTemperatureHome.setText(getResources().getString(R.string.fragment_home_temperature) + temerature);
            textViewPrecipitationHome.setText(getResources().getString(R.string.fragment_home_precipitation) + precipitation);
            textViewSunriseHome.setText(getResources().getString(R.string.fragment_home_sunrise) + sunrise);
            textViewSunsetHome.setText(getResources().getString(R.string.fragment_home_sunset) +sunset);
            textViewUpdateHome.setText(getResources().getString(R.string.fragment_home_update) + update);

            //Logik wetter geeignet sport
            textViewInformationHome.setText(getResources().getString(R.string.fragment_home_information_perfect) + information);
        }
        else
        {
            linearLayoutWeatherHome.setVisibility(View.INVISIBLE);
        }

        return viewHome;
    }
}
