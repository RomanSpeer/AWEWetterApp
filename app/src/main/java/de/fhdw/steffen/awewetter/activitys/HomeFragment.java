/**
 * HomeFragment
 * Darstellung der Startseite der App.
 * Hier wird eine Uhr sowie bei hinterlegten Daten
 * auch die aktuellen Wetterinformationen angezeigt.
 *
 * @author Steffen Höltje
 * @version 1.0
 */

package de.fhdw.steffen.awewetter.activitys;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Calendar;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;

import de.fhdw.steffen.awewetter.R;
import de.fhdw.steffen.awewetter.classes.Server;
import de.fhdw.steffen.awewetter.classes.Weather;
import de.fhdw.steffen.awewetter.classes.WeatherList;

public class HomeFragment extends Fragment {

    // Variablen für die Darstellung
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

    //Variablen für die Wetterinformationen
    private boolean citySet = true;
    private String sunrise = "";
    private String sunset = "";

    /**
     *
     * Erstellen der HomeFragment-View
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return HomeFragment mit allen Darstellungen und Informationen
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Holen der Ressourcen aus den XML File
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

        //Holen der SharedPreference Informationen
        SharedPreferences preferences = getContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        //Prüfen ob die Informationen leer sind
        if (!preferences.getAll().isEmpty()) {
            //Holen der Wetterliste und des aktuellen Wetters
            WeatherList weatherList = WeatherList.getWeatherList();
            Weather currentWeather = weatherList.getWeatherData().get(0);


            //Umwandeln des Timestamps zu leserlichen Uhrzeit
            Format dayFormatter = new SimpleDateFormat("HH:mm:ss");

            Date sunriseDate = new java.util.Date((long) Integer.parseInt(currentWeather.getSunrise()) * 1000);
            Date sunsetDate = new java.util.Date((long) Integer.parseInt(currentWeather.getSunset()) * 1000);

            sunrise = dayFormatter.format(sunriseDate);
            sunset = dayFormatter.format(sunsetDate);


            //Setzen der Informationen
            linearLayoutWeatherHome.setVisibility(View.VISIBLE);
            textViewCityHome.setText(getResources().getString(R.string.fragment_home_city) + " " + preferences.getString("cityName", ""));
            textViewTemperatureHome.setText(getResources().getString(R.string.fragment_home_temperature) + " " + currentWeather.getCurrentTmp());
            textViewPrecipitationHome.setText(getResources().getString(R.string.fragment_home_precipitation) + " " + currentWeather.getHumidity());
            textViewSunriseHome.setText(getResources().getString(R.string.fragment_home_sunrise) + " " + sunrise);
            textViewSunsetHome.setText(getResources().getString(R.string.fragment_home_sunset) + " " + sunset);
            textViewUpdateHome.setText(getResources().getString(R.string.fragment_home_update) + " " + currentWeather.getDayWeather());

            currentWeather.setIconWeather(currentWeather.getIconWeather().replace('n', 'd'));

            //Setzen des Icons für das aktuelle Wetter
            switch (currentWeather.getIconWeather()) {
                case "01d":
                    imageViewWeatherHome.setImageResource(R.drawable.sun01d);
                    break;
                case "02d":
                    imageViewWeatherHome.setImageResource(R.drawable.fewclouds02d);
                    break;
                case "03d":
                    imageViewWeatherHome.setImageResource(R.drawable.scatteredclouds03d);
                    break;
                case "04d":
                    imageViewWeatherHome.setImageResource(R.drawable.brokenclouds04d);
                    break;
                case "09d":
                    imageViewWeatherHome.setImageResource(R.drawable.showerrain09d);
                    break;
                case "10d":
                    imageViewWeatherHome.setImageResource(R.drawable.rain10d);
                    break;
                case "11d":
                    imageViewWeatherHome.setImageResource(R.drawable.thunderstorm11d);
                    break;
                case "13d":
                    imageViewWeatherHome.setImageResource(R.drawable.snow13d);
                    break;
                case "50d":
                    imageViewWeatherHome.setImageResource(R.drawable.mist50d);
                    break;
                default:
                    imageViewWeatherHome.setImageResource(R.drawable.image_not_found);
                    ;
            }

            //Logik ob das Wetter für den ausgewählten Sport geeignet ist
            String sportType = preferences.getString("sportType", "");

            switch (sportType) {
                case "Kein Sport":
                    textViewInformationHome.setText(getResources().getString(R.string.fragment_home_information_not_given));
                    break;
                case "Fahrrad fahren":
                    if ((currentWeather.getTempMaxWeather() < 30 || currentWeather.getTempMinWeather() > 0) && !currentWeather.getIconWeather().equals("11d")) {
                        if (currentWeather.getWindSpeedWeather() > 5.0 && currentWeather.getWindSpeedWeather() <= 10) {
                            textViewInformationHome.setText(getResources().getString(R.string.fragment_home_information_bad_good) + " " + sportType);
                        } else if (currentWeather.getWindSpeedWeather() >= 10) {
                            textViewInformationHome.setText(getResources().getString(R.string.fragment_home_information_bad) + " " + sportType);
                        } else {
                            textViewInformationHome.setText(getResources().getString(R.string.fragment_home_information_perfect) + " " + sportType);
                        }
                    } else {
                        textViewInformationHome.setText(getResources().getString(R.string.fragment_home_information_bad) + " " + sportType);
                    }
                    break;
                case "Laufen":
                    if ((currentWeather.getTempMaxWeather() < 25 || currentWeather.getTempMinWeather() > 5) && !currentWeather.getIconWeather().equals("11d")) {
                        if (currentWeather.getWindSpeedWeather() > 8) {
                            textViewInformationHome.setText(getResources().getString(R.string.fragment_home_information_bad_good) + " " + sportType);
                        } else {
                            textViewInformationHome.setText(getResources().getString(R.string.fragment_home_information_perfect) + " " + sportType);
                        }
                    } else {
                        textViewInformationHome.setText(getResources().getString(R.string.fragment_home_information_bad) + " " + sportType);
                    }
                    break;
                case "Fallschirmspringen":
                    if (currentWeather.getWindSpeedWeather() >= 15 || currentWeather.getCurrentTmp() > 40 || currentWeather.getIconWeather().equals("10d") || currentWeather.getIconWeather().equals("11d") || currentWeather.getIconWeather().equals("13d") || currentWeather.getIconWeather().equals("50d") || currentWeather.getIconWeather().equals("03d")) {
                        textViewInformationHome.setText(getResources().getString(R.string.fragment_home_information_bad) + " " + sportType);
                    } else {
                        textViewInformationHome.setText(getResources().getString(R.string.fragment_home_information_perfect) + " " + sportType);
                    }
                    break;
                case "Segeln":
                    if (currentWeather.getCurrentTmp() > 40 || currentWeather.getIconWeather().equals("10d") || currentWeather.getIconWeather().equals("11d") || currentWeather.getIconWeather().equals("13d") || currentWeather.getIconWeather().equals("50d") || currentWeather.getIconWeather().equals("03d")) {
                        textViewInformationHome.setText(getResources().getString(R.string.fragment_home_information_bad) + " " + sportType);
                    } else {
                        if (currentWeather.getWindSpeedWeather() <= 5.0) {
                            textViewInformationHome.setText(getResources().getString(R.string.fragment_home_information_bad_good) + " " + sportType);
                        } else {
                            textViewInformationHome.setText(getResources().getString(R.string.fragment_home_information_perfect) + " " + sportType);
                        }

                    }
                    break;
                default:
                    textViewInformationHome.setVisibility(View.INVISIBLE);
            }
        } else {
            linearLayoutWeatherHome.setVisibility(View.INVISIBLE);
        }

        return viewHome;
    }
}
